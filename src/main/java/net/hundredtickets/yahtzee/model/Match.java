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
	private Scorecard activePlayer;
	@Valid
	private Scorecard passivePlayer;

	public Match(String activePlayerName, String passivePlayerName) {
		activePlayer = new Scorecard(activePlayerName);
		passivePlayer = new Scorecard(passivePlayerName);
	}

	public Scorecard getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Scorecard activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Scorecard getPassivePlayer() {
		return passivePlayer;
	}

	public void setPassivePlayer(Scorecard passivePlayer) {
		this.passivePlayer = passivePlayer;
	}

	public void reset() {
		activePlayer = new Scorecard(activePlayer.getPlayerName());
		passivePlayer = new Scorecard(passivePlayer.getPlayerName());
	}
}
