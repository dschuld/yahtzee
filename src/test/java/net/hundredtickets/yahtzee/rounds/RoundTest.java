package net.hundredtickets.yahtzee.rounds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class RoundTest {

	@Test
	public void testEmptyRoll() {
		Round round = new Round();
		assertEquals("Incorrect number of rolls left", 3, round.getRemainingRolls());
	}

	@Test
	public void testRollOnce() {
		Round round = new Round();
		DiceSet diceSet = round.roll();
		assertEquals("Incorrect number of rolls left", 2, round.getRemainingRolls());
		assertEquals("Incorrect number of dice in roll", 5, diceSet.size());
		for (Integer num : diceSet) {
			assertTrue("Dice value not between 1 and 6", 1 <= num && num <= 6);
		}

	}

	@Test
	public void rollTwiceWithSave() {
		Round round = new Round();
		DiceSet dice = round.roll();
		Iterator<Integer> iterator = dice.iterator();
		DiceSet saveInput = new DiceSet(iterator.next(), iterator.next(), iterator.next());
		round.save(saveInput);
		DiceSet savedDice = round.getSavedDice();
		assertEquals("Saved dice and input not equal", saveInput, savedDice);

		dice = round.roll();

		// TODO assert dice contains saved dice
		assertEquals("Incorrect number of rolls left", 1, round.getRemainingRolls());
		assertEquals("Incorrect number of dice in roll", 5, dice.size());

	}

	@Test(expected = IllegalArgumentException.class)
	public void createDiceSetTooLarge() {
		DiceSet s1 = new DiceSet(1, 2, 3, 4);
		DiceSet s2 = new DiceSet(6, 5, 4);
		DiceSet.createFromDiceSet(s1, s2);
	}

	// TODO make proof for 6-set
	@Test(expected = IllegalMoveException.class)
	public void rollOnceAndFixIllegalDice() {
		Round round = new Round();
		round.roll();
		round.save(new DiceSet(6, 6, 6, 6, 6));

	}

}
