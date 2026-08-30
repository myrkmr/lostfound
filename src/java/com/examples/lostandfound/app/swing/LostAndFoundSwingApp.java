package com.examples.lostandfound.app.swing;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class LostAndFoundSwingApp {

	@Option(names = "--mongo-host")
	private String mongoHost = "localhost";

	@Option(names = "--mongo-port")
	private int mongoPort = 27017;

	@Option(names = "--db-name")
	private String databaseName = "lostfound";

	@Option(names = "--db-collection")
	private String collectionName = "lostitems";
}
