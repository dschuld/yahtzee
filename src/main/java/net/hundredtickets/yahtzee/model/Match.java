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

	public Match(String activePlayerId, String passivePlayerId) {
		this.playerIdToName = new HashMap<>();

		activePlayer = new Scorecard(activePlayerId);
		passivePlayer = new Scorecard(passivePlayerId);
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

    //TODO extend for more than 2 players
    public String addPlayer(String player) {
	    if (activePlayer.getPlayerName() == null || activePlayer.getPlayerName().isEmpty()) {
	        activePlayer.setPlayerName(player);
	        return activePlayer.getPlayerId();
        } else if (passivePlayer.getPlayerName() == null || passivePlayer.getPlayerName().isEmpty()) {
            passivePlayer.setPlayerName(player);
            return passivePlayer.getPlayerId();
        } else {
	        throw new IllegalArgumentException("Too many players registered!");
        }

    }
}
