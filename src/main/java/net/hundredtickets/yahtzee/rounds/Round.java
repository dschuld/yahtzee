package net.hundredtickets.yahtzee.rounds;

import java.util.Arrays;

/**
 * A Round in the game consists of rolling five dice up to three times. After
 * each roll, any of the dice can be saved and the other dice re-rolled.
 * <p>
 * The class used {@link DiceSet} to represent the results of each throw. An
 * exemplary usage where all the dice after the first roll are saved looks like
 * this:
 * <p>
 * 
 * <code>
 * 
 *
 * Round round = new Round();<br>
 * DiceSet dice = round.roll();<br>
 * round.save(dice);
 * 
 * </code>
 * 
 * 
 * @author David
 *
 */
public class Round {

	private int remainingRolls = 3;
	private boolean[] savedDice = new boolean[5];
	private DiceSet currentRolledDice = DiceSet.createEmpty();

	/**
	 * Returns how many rolls are left in this round. The dice can be rolled up
	 * to three times.
	 * 
	 */
	public int getRemainingRolls() {
		return remainingRolls;
	}

	/**
	 * Rolls the dice. This means that the saved dice from previous rolls are
	 * kept, and (5-#saved dice) dice are thrown and yield new values.
	 * 
	 */
	public DiceSet roll() {
		if (remainingRolls <= 0) {
			throw new NoRollsLeftException();
		}

		remainingRolls--;

		currentRolledDice.setRandomValues(savedDice);
		System.out.println("New dice: " + currentRolledDice);
		return currentRolledDice;
	}

	/**
	 * This saved the specified dice for this rounds, meaning that they will not
	 * be thrown in the next roll.
	 * 
	 * @param diceSet
	 */
	public void save(boolean[] savedDice) {

		System.out.println("Current dice:" + currentRolledDice + ",savedDice: " + Arrays.toString(savedDice));

		this.savedDice = savedDice;

	}

	public DiceSet getCurrentRolledDice() {
		return this.currentRolledDice;
	}

	public boolean[] getSavedDice() {
		return this.savedDice;
	}

}
