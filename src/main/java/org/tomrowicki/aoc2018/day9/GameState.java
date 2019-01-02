package org.tomrowicki.aoc2018.day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class GameState {

	private int lastMarble;

	private int borderIndex;

	private int currentMarbleIndex;
	
	private int marbleBeingPlaced;

	@Getter
	private List<Player> players = new ArrayList<>();

	private int[] circleState;

	public GameState(int noOfPlayers, int lastMarble) {
		this.lastMarble = lastMarble;
		for (int i = 0; i < noOfPlayers; i++) {
			Player p = new Player(i + 1);
			players.add(p);
		}
		// accounts for the zeroth turn
		circleState = new int[lastMarble + 1];
		makeFirstMoves();
	}

	private void makeFirstMoves() {
		this.borderIndex = 2;
		this.currentMarbleIndex = 1;
		circleState[0] = 0;
		circleState[1] = 2;
		circleState[2] = 1;
		marbleBeingPlaced = 3;
	}

	public void play() {
		// beginning at the 3rd step
		for (int i = 2; i < lastMarble; i++) {
			Player currentPlayer = players.get(i % players.size());
			makeAMove(currentPlayer);
			marbleBeingPlaced++;
		}
	}

	// marble 23 and its multiples are not placed and added to the score instead
	// also in the above case, a marble located 7 marbles counter-clockwise is
	// taken and added to the score, the one next to it cw becomes current
	private void makeAMove(Player currentPlayer) {
		if (marbleBeingPlaced % 23 == 0) {
			currentPlayer.setCurrentScore(calculateCurrentScore(currentPlayer, marbleBeingPlaced));
			cutOutMarble(currentMarbleIndex - 7);
		} else {
			if (currentMarbleIndex < borderIndex) {
				squeezeMarbleBetweenOthers(currentMarbleIndex + 2);
				currentMarbleIndex += 2;
			} else {
				squeezeMarbleBetweenOthers(1);
				currentMarbleIndex = 1;
			}
			borderIndex++;
		}
	}

	private void squeezeMarbleBetweenOthers(int insertionIndex) {
		List<Integer> basisForNewCircleState = new ArrayList<>();
		for (int i=0; i<circleState.length; i++) {
			if (i == insertionIndex) {
				basisForNewCircleState.add(marbleBeingPlaced);
			}
			basisForNewCircleState.add(circleState[i]);
		}
		circleState = basisForNewCircleState.stream().mapToInt(i->i).toArray();
	}

	// FIXME marble 92 causes exception
	private int calculateCurrentScore(Player currentPlayer, int marbleBeingPlaced) {
		int prevScore = currentPlayer.getCurrentScore();
		return prevScore + marbleBeingPlaced + circleState[currentMarbleIndex - 7];
	}

	private void cutOutMarble(int indexOfMarbleBeingCutOut) {
		List<Integer> basisForNewCircleState = new ArrayList<>();
		for (int i=0; i<circleState.length; i++) {
			if (i == indexOfMarbleBeingCutOut) {
				continue;
			}
			basisForNewCircleState.add(circleState[i]);
		}
		// cool way to convert List<Integer> to int[]
		circleState = basisForNewCircleState.stream().mapToInt(i->i).toArray();
		currentMarbleIndex = indexOfMarbleBeingCutOut;
		borderIndex--;
	}

	public void printCircleState() {
		System.out.println(Arrays.toString(circleState));
	}
}
