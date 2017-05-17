package net.hundredtickets.yahtzee.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.rounds.DiceSet;
import net.hundredtickets.yahtzee.rounds.Round;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoundService {

	private Round round = new Round();

	public Roll rollDice(Roll roll) {

		System.out.println("\n\nCurrent dice: " + roll);
		round.save(roll.getSavedDice());

		// Roll dice
		System.out.println("Rolling...");
		DiceSet newRoll = round.roll();

		System.out.println("New dice: " + newRoll);

		return roll;
	}

	/**
	 * Initializes a new round
	 */
	public void newRound() {
		this.round = new Round();
	}

	/**
	 * Updates the value of the UI model element
	 * 
	 * @param roll
	 *            UI model
	 */
	public void fetchCurrentValues(Roll roll) {

		roll.setDice(round.getCurrentRolledDice().getDice(0), round.getCurrentRolledDice().getDice(1),
				round.getCurrentRolledDice().getDice(2), round.getCurrentRolledDice().getDice(3),
				round.getCurrentRolledDice().getDice(4));

		roll.setRemainingRolls(round.getRemainingRolls());

	}

}
