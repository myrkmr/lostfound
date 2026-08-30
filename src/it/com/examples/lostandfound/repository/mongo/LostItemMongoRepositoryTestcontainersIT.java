package com.examples.lostandfound.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.examples.lostandfound.model.LostItem;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;

public class LostItemMongoRepositoryTestcontainersIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:5");

	private static final String DATABASE_NAME = "lostfound-repository-it";
	private static final String COLLECTION_NAME = "lostitems";

	private MongoClient mongoClient;
	private LostItemMongoRepository lostItemRepository;
	private MongoCollection<Document> lostItemCollection;

	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		mongoClient.getDatabase(DATABASE_NAME).drop();
		lostItemRepository = new LostItemMongoRepository(mongoClient, DATABASE_NAME, COLLECTION_NAME);
		lostItemCollection = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Test
	public void testFindAll() {
		lostItemCollection.insertOne(new Document().append("id", "1").append("description", "Wallet"));
		lostItemCollection.insertOne(new Document().append("id", "2").append("description", "Keys"));
		assertThat(lostItemRepository.findAll()).containsExactly(
				new LostItem("1", "Wallet"),
				new LostItem("2", "Keys"));
	}
}
