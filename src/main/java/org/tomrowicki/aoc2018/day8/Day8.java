package org.tomrowicki.aoc2018.day8;

import java.io.IOException;
import java.net.URISyntaxException;

import org.tomrowicki.aoc2018.FileReader;

public class Day8 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		for (String s : FileReader.getFileContents("test.txt")) {
			System.out.println(s);
		}
		FileReader.getFileContentsAsStream("test.txt").forEach(System.out::println);
	}
}
