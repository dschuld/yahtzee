package net.hundredtickets.yahtzee.model;

import javax.validation.Valid;

/**
 * Match encapsulates the scorecards for the players. Currently only two player
 * game possible.
 * 
 * @author david
 *
 */
public class Match {

	@Valid
	private Scorecard firstPlayer;
	@Valid
	private Scorecard secondPlayer;

	public Match(String firstPlayerName, String secondPlayerName) {
		firstPlayer = new Scorecard(firstPlayerName);
		secondPlayer = new Scorecard(secondPlayerName);
	}

	public Scorecard getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Scorecard firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Scorecard getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(Scorecard secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

}
