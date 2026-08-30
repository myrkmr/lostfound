package com.examples.lostandfound.app.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import picocli.CommandLine;

public class LostAndFoundSwingAppIT {

	@Test
	public void testHelpOptionReturnsSuccess() {
		int exitCode = new CommandLine(new LostAndFoundSwingApp()).execute("--help");
		assertThat(exitCode).isZero();
	}

	@Test
	public void testMongoConfigurationOptionsAreAccepted() {
		CommandLine.ParseResult parseResult = new CommandLine(new LostAndFoundSwingApp()).parseArgs(
				"--mongo-host=mongo.example",
				"--mongo-port=12345",
				"--db-name=lost-items",
				"--db-collection=items");
		assertThat(parseResult.matchedOptionValue("--mongo-host", "")).isEqualTo("mongo.example");
		assertThat(parseResult.matchedOptionValue("--mongo-port", 0)).isEqualTo(12345);
		assertThat(parseResult.matchedOptionValue("--db-name", "")).isEqualTo("lost-items");
		assertThat(parseResult.matchedOptionValue("--db-collection", "")).isEqualTo("items");
	}
}
