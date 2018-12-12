package org.tomrowicki.aoc2018;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileReader {

	public static List<String> getFileContents(String filename) throws IOException, URISyntaxException {
		return Files.readAllLines(getPath(filename));
	}

	public static Stream<String> getFileContentsAsStream(String filename) throws IOException, URISyntaxException {
		return Files.lines(getPath(filename));
	}

	private static Path getPath(String filename) throws URISyntaxException {
		return Paths.get(FileReader.class.getClassLoader().getResource(filename).toURI());
	}
}
