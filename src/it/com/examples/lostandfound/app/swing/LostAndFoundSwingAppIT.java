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
}
