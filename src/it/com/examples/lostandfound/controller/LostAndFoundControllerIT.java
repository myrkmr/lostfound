package com.examples.lostandfound.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import com.examples.lostandfound.model.LostItem;
import com.examples.lostandfound.repository.LostItemRepository;
import com.examples.lostandfound.repository.mongo.LostItemMongoRepository;
import com.examples.lostandfound.view.LostItemView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class LostAndFoundControllerIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:5");

	private static final String DATABASE_NAME = "lostfound-controller-it";
	private static final String COLLECTION_NAME = "lostitems";

	@Mock
	private LostItemView lostItemView;

	private MongoClient mongoClient;
	private LostItemRepository lostItemRepository;
	private LostAndFoundController lostAndFoundController;
	private AutoCloseable closeable;

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		mongoClient.getDatabase(DATABASE_NAME).drop();
		lostItemRepository = new LostItemMongoRepository(mongoClient, DATABASE_NAME, COLLECTION_NAME);
		lostAndFoundController = new LostAndFoundController(lostItemRepository, lostItemView);
	}

	@After
	public void tearDown() throws Exception {
		closeable.close();
		mongoClient.close();
	}

	@Test
	public void testAllLostItems() {
		LostItem lostItem = new LostItem("1", "Wallet");
		lostItemRepository.save(lostItem);
		lostAndFoundController.allLostItems();
		verify(lostItemView).showAllLostItems(asList(lostItem));
	}
}
