package org.tomrowicki.aoc2018.day9;

import lombok.Getter;
import lombok.Setter;

public class Player {

	@Getter
	private int playerNo;

	@Setter
	@Getter
	private long currentScore;

	public Player(int playerNo) {
		this.playerNo = playerNo;
		currentScore = 0l;
	}
}
