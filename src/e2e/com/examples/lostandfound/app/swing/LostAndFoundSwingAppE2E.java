package com.examples.lostandfound.app.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;

@RunWith(GUITestRunner.class)
public class LostAndFoundSwingAppE2E extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:5");

	private static final String DATABASE_NAME = "lostfound-e2e";
	private static final String COLLECTION_NAME = "lostitems";

	private MongoClient mongoClient;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(mongo.getHost(), mongo.getFirstMappedPort());
		mongoClient.getDatabase(DATABASE_NAME).drop();
		mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.insertOne(new Document().append("id", "1").append("description", "Wallet"));
		mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.insertOne(new Document().append("id", "2").append("description", "Keys"));
		application("com.examples.lostandfound.app.swing.LostAndFoundSwingApp")
				.withArgs(
						"--mongo-host=" + mongo.getHost(),
						"--mongo-port=" + mongo.getFirstMappedPort(),
						"--db-name=" + DATABASE_NAME,
						"--db-collection=" + COLLECTION_NAME)
				.start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Lost and Found Management System".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

	@Test
	@GUITest
	public void testOnStartAllDatabaseItemsAreShown() {
		assertThat(window.list("lostItemList").contents()).containsExactly("1 - Wallet", "2 - Keys");
	}

	@Test
	@GUITest
	public void testAddButtonSuccess() {
		window.textBox("idTextBox").enterText("3");
		window.textBox("itemNameTextBox").enterText("Phone");
		window.textBox("descriptionTextBox").enterText("Mobile phone");
		window.textBox("lostDateTextBox").enterText("2026-08-30");
		window.button(JButtonMatcher.withText("Add")).requireEnabled();
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(window.list("lostItemList").contents()).contains("3 - Mobile phone");
		Document savedItem = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.find(new Document("id", "3")).first();
		assertThat(savedItem.getString("description")).isEqualTo("Mobile phone");
	}

	@Test
	@GUITest
	public void testAddButtonRejectsDuplicateId() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("itemNameTextBox").enterText("Another wallet");
		window.textBox("descriptionTextBox").enterText("Duplicate item");
		window.textBox("lostDateTextBox").enterText("2026-08-30");
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(window.label("errorMessageLabel").text())
				.isEqualTo("Already existing lost item with id 1: 1 - Wallet");
		long matchingItems = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.countDocuments(new Document("id", "1"));
		assertThat(matchingItems).isEqualTo(1);
	}

	@Test
	@GUITest
	public void testDeleteButtonSuccess() {
		window.list("lostItemList").selectItem("1 - Wallet");
		window.button(JButtonMatcher.withText("Delete Selected")).requireEnabled();
		window.button(JButtonMatcher.withText("Delete Selected")).click();
		assertThat(window.list("lostItemList").contents()).doesNotContain("1 - Wallet");
		long matchingItems = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.countDocuments(new Document("id", "1"));
		assertThat(matchingItems).isZero();
	}

	@Test
	@GUITest
	public void testDeleteButtonRejectsMissingItem() {
		window.list("lostItemList").selectItem("1 - Wallet");
		mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME)
				.deleteOne(new Document("id", "1"));
		window.button(JButtonMatcher.withText("Delete Selected")).requireEnabled();
		window.button(JButtonMatcher.withText("Delete Selected")).click();
		assertThat(window.label("errorMessageLabel").text())
				.isEqualTo("No existing lost item with id 1: 1 - Wallet");
		assertThat(window.list("lostItemList").contents()).doesNotContain("1 - Wallet");
	}
}
