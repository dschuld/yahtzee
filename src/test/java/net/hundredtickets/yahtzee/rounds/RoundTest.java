package net.hundredtickets.yahtzee.rounds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		DiceSet before = round.roll();
		boolean[] saved = { true, true, true, false, false };
		round.save(saved);

		DiceSet after = round.roll();

		assertEquals("Incorrect number of rolls left", 1, round.getRemainingRolls());
		assertEquals("Saved dice before and after second roll are not equal", before.getDice(0), after.getDice(0));
		assertEquals("Saved dice before and after second roll are not equal", before.getDice(1), after.getDice(1));
		assertEquals("Saved dice before and after second roll are not equal", before.getDice(2), after.getDice(2));

	}

	@Test(expected = IllegalArgumentException.class)
	public void createDiceSetTooLarge() {
		DiceSet s1 = new DiceSet(1, 2, 3, 4);
		DiceSet s2 = new DiceSet(6, 5, 4);
		DiceSet.createFromDiceSet(s1, s2);
	}

}
