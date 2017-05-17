package net.hundredtickets.yahtzee.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.rounds.NoRollsLeftException;

public class RoundServiceTest {

	private RoundService roundService;

	@Before
	public void setUp() {
		roundService = new RoundService();
		roundService.newRound();
	}

	@Test
	public void rollAndSave() {
		Roll roll = new Roll();
		Roll returnedRoll = roundService.rollDice(roll);

		Integer dice1 = returnedRoll.getDice1();
		Integer dice3 = returnedRoll.getDice3();
		Integer dice5 = returnedRoll.getDice5();

		returnedRoll.setSaveDice1(true);
		returnedRoll.setSaveDice3(true);
		returnedRoll.setSaveDice5(true);

		returnedRoll = roundService.rollDice(returnedRoll);

		assertEquals("Dice not equal", dice1, returnedRoll.getDice1());
		assertEquals("Dice not equal", dice3, returnedRoll.getDice3());
		assertEquals("Dice not equal", dice5, returnedRoll.getDice5());

	}

	@Test(expected = NoRollsLeftException.class)
	public void rollTooOften() {
		Roll roll = new Roll();
		Roll returnedRoll = roundService.rollDice(roll);

		returnedRoll = roundService.rollDice(returnedRoll);

		returnedRoll = roundService.rollDice(returnedRoll);
		returnedRoll = roundService.rollDice(returnedRoll);

	}

}
