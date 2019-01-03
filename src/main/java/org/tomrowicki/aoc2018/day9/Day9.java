package org.tomrowicki.aoc2018.day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

import org.tomrowicki.aoc2018.FileReader;

public class Day9 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		List<String> contents = FileReader.getFileContents("input9.txt");
		Day9Input parsedInput = parseInput(contents.get(0));
		// part 1
		// GameState gs = new GameState(parsedInput.getNoOfPlayers(),
		// parsedInput.getLastMarbleWorth());
		// part 2
		GameState gs = new GameState(parsedInput.getNoOfPlayers(), parsedInput.getLastMarbleWorth() * 100);
		gs.play();
		Player playerWithHighestScore = gs.getPlayers().stream()
				.max(Comparator.comparing(Player::getCurrentScore))
				.get();
		System.out.println("Part I answer: " + playerWithHighestScore.getCurrentScore());
	}

	private static Day9Input parseInput(String line) {
		Day9Input parsedInput = new Day9Input();
		String[] parts = line.split(" ");
		parsedInput.setNoOfPlayers(Integer.parseInt(parts[0]));
		parsedInput.setLastMarbleWorth(Integer.parseInt(parts[6]));
		return parsedInput;
	}
}
