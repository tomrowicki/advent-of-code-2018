package org.tomrowicki.aoc2018.day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.testng.annotations.Test;
import org.tomrowicki.aoc2018.FileReader;

@Test
public class Day9Test {
	
	// first try - ~800ms
	// warmed-up jvm - ~700ms

	public void shouldRunAsQuicklyAsPossible() throws IOException, URISyntaxException {
		List<String> contents = FileReader.getFileContents("input9test.txt");
		contents.forEach(s -> playGame(s));
	}

	private void playGame(String game) {
		Day9Input parsedInput = Day9.parseInput(game);
		GameState gs = new GameState(parsedInput.getNoOfPlayers(), parsedInput.getLastMarbleWorth());
		gs.play();
	}
}
