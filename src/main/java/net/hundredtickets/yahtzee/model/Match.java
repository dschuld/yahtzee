package net.hundredtickets.yahtzee.model;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Match encapsulates the scorecards for the players. Currently only two player
 * game possible.
 * 
 * @author david
 *
 */
public class Match {

	private Map<String, String> playerIdToName;

	@Valid
	private Scorecard activePlayer;
	@Valid
	private Scorecard passivePlayer;

	public static final Fields[] fieldnames = Fields.values();

	public Match(String activePlayerName, String passivePlayerName) {
		this.playerIdToName = new HashMap<>();

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
		activePlayer = new Scorecard(activePlayer.getPlayerId());
		passivePlayer = new Scorecard(passivePlayer.getPlayerId());
	}

	public Scorecard getScorecard(String player) {
	    if (activePlayer.getPlayerId().equals(player)) {
	        return activePlayer;
        } else {
	        return passivePlayer;
        }
    }
}
