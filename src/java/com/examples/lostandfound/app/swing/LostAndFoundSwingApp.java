package com.examples.lostandfound.app.swing;

import java.awt.EventQueue;
import java.util.concurrent.Callable;

import com.examples.lostandfound.controller.LostAndFoundController;
import com.examples.lostandfound.repository.mongo.LostItemMongoRepository;
import com.examples.lostandfound.view.swing.LostItemSwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class LostAndFoundSwingApp implements Callable<Void> {

	@Option(names = "--mongo-host")
	private String mongoHost = "localhost";

	@Option(names = "--mongo-port")
	private int mongoPort = 27017;

	@Option(names = "--db-name")
	private String databaseName = "lostfound";

	@Option(names = "--db-collection")
	private String collectionName = "lostitems";

	public static void main(String[] args) {
		new CommandLine(new LostAndFoundSwingApp()).execute(args);
	}

	@Override
	public Void call() {
		EventQueue.invokeLater(() -> {
			LostItemMongoRepository lostItemRepository = new LostItemMongoRepository(
					new MongoClient(new ServerAddress(mongoHost, mongoPort)),
					databaseName,
					collectionName);
			LostItemSwingView lostItemView = new LostItemSwingView();
			LostAndFoundController lostAndFoundController = new LostAndFoundController(
					lostItemRepository,
					lostItemView);
			lostItemView.setLostAndFoundController(lostAndFoundController);
			lostItemView.setVisible(true);
			lostAndFoundController.allLostItems();
		});
		return null;
	}
}
