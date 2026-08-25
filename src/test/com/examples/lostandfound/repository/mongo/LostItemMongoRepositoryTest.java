package com.examples.lostandfound.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class LostItemMongoRepositoryTest {

	private static final String DATABASE_NAME = "lostfound";
	private static final String COLLECTION_NAME = "lostitems";

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient client;
	private LostItemMongoRepository lostItemRepository;

	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}

	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}

	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(serverAddress));
		lostItemRepository = new LostItemMongoRepository(client, DATABASE_NAME, COLLECTION_NAME);
		client.getDatabase(DATABASE_NAME).drop();
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(lostItemRepository.findAll()).isEmpty();
	}
}
