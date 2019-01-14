package org.tomrowicki.aoc2018.day9;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class GameState {

	private int lastMarble;

	private int borderIndex;

	private int currentMarbleIndex;

	private int marbleBeingPlaced;

	private int percentComplete = 0;

	@Getter
	private List<Player> players = new ArrayList<>();

	// TODO spróbować zastąpić ArrayDeque?
	private ArrayList<Integer> circleState;

	public GameState(int noOfPlayers, int lastMarble) {
		this.lastMarble = lastMarble;
		for (int i = 0; i < noOfPlayers; i++) {
			Player p = new Player(i + 1);
			players.add(p);
		}
		// accounts for the zeroth turn
		circleState = new ArrayList<>();
		makeFirstMoves();
	}

	private void makeFirstMoves() {
		this.borderIndex = 2;
		this.currentMarbleIndex = 1;
		circleState.add(0);
		circleState.add(2);
		circleState.add(1);
		marbleBeingPlaced = 3;
	}

	public void play() {
		// beginning at the 3rd step
		for (int i = 2; i < lastMarble; i++) {
			Player currentPlayer = players.get(i % players.size());
			makeAMove(currentPlayer);
			// printCircleState(currentPlayer.getPlayerNo());
			printPercent(i);
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

	private long calculateCurrentScore(Player currentPlayer, int marbleBeingPlaced) {
		long prevScore = currentPlayer.getCurrentScore();
		long newScore;
		if (currentMarbleIndex < 7) {
			// dealing with wrapping around via left side
			newScore = prevScore + marbleBeingPlaced + circleState.get(circleState.size() + (currentMarbleIndex - 7));
		} else {
			newScore = prevScore + marbleBeingPlaced + circleState.get(currentMarbleIndex - 7);
		}
		return newScore;
	}

	private void cutOutMarble(int indexOfMarbleBeingCutOut) {
		if (indexOfMarbleBeingCutOut < 0) {
			// dealing with wrapping around via left side
			indexOfMarbleBeingCutOut = circleState.size() + indexOfMarbleBeingCutOut;
		}
		circleState.remove(indexOfMarbleBeingCutOut);
		currentMarbleIndex = indexOfMarbleBeingCutOut;
		borderIndex--;
	}

	private void squeezeMarbleBetweenOthers(int insertionIndex) {
		if (insertionIndex >= circleState.size()) {
			circleState.add(marbleBeingPlaced);
		} else {
			circleState.add(insertionIndex, marbleBeingPlaced);
		}
	}

	public void printCircleState(int playerId) {
		System.out.println("[" + playerId + "] " + circleState.toString());
	}

	private void printPercent(int marbleWorth) {
		int percent = marbleWorth * 100 / lastMarble;
		if (percent > percentComplete) {
			System.out.println(percent + "%");
			percentComplete = percent;
		}
	}
}
