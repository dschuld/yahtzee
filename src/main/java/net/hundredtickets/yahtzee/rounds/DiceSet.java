package net.hundredtickets.yahtzee.rounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * A DiceSet is a set of up to 5 dice. It can be created by specifying the
 * values directly, from one or more existing DiceSets, or from random values to
 * simulate rolling the dice.
 * 
 * @author David
 *
 */
public class DiceSet implements Iterable<Integer> {

	private static Random random = new Random();

	private List<Integer> dice = new ArrayList<Integer>();

	DiceSet(Integer... dice) {

		if (dice.length > 5) {
			throw new IllegalArgumentException("DiceSet must not contain more than 5 values");
		}
		this.dice = Arrays.asList(dice);
	}

	public void setRandomValues(boolean[] savedDice) {
		for (int i = 0; i < savedDice.length; i++) {
			if (!savedDice[i]) {
				this.dice.set(i, random.nextInt(6) + 1);
			}
		}
	}

	/**
	 * Creates an empty dice set
	 */
	public static DiceSet createEmpty() {
		return createFromDice(0, 0, 0, 0, 0);
	}

	/**
	 * Creates a DiceSet from the specified values.
	 * 
	 * @param dice
	 *            List of int specifying the dice values
	 */
	public static DiceSet createFromDice(Integer... dice) {
		return new DiceSet(dice);
	}

	/**
	 * Creates a DiceSet by merging the specified diceSets.
	 * 
	 * @param diceSets
	 *            DiceSets to merge
	 */
	public static DiceSet createFromDiceSet(DiceSet... diceSets) {
		Stream<DiceSet> stream = Stream.of(diceSets);

		return new DiceSet(stream.map(set -> set.dice).reduce(new ArrayList<Integer>(), (l1, l2) -> {
			l1.addAll(l2);
			return l1;
		}).toArray(new Integer[0]));
	}

	/**
	 * Checks if this DiceSet contains all values of the parameter DiceSet
	 * 
	 * @param diceSet
	 *            DiceSet to be checked
	 * @return True if all values of the parameter set are contained in this
	 *         set.
	 */
	public boolean contains(DiceSet diceSet) {
		List<Integer> tmpList = new ArrayList<>(dice);
		for (Integer num : diceSet) {
			if (tmpList.contains(num)) {
				tmpList.remove(num);
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object other) {

		return other instanceof DiceSet && this.contains((DiceSet) other) && ((DiceSet) other).contains(this);
	}

	/**
	 * Returns the size of this DiceSet
	 * 
	 * @return
	 */
	public int size() {
		return dice.size();
	}

	@Override
	public Iterator<Integer> iterator() {
		return dice.iterator();
	}

	public Integer getDice(int pos) {
		return this.dice.get(pos);
	}

	@Override
	public String toString() {
		return this.dice.toString();
	}

}
