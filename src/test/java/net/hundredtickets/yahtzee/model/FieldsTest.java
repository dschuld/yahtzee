package net.hundredtickets.yahtzee.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldsTest {

    @Test
    public void testEvaluateCount() {
        assertEquals(2, Fields.ONES.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(2, Fields.TWOS.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(0, Fields.FIVES.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(30, Fields.SIXES.evaluate(new Integer[]{6, 6, 6, 6, 6}));
    }

    @Test
    public void testEvaluateThreeOfAKind() {
        assertEquals(0, Fields.THREE_OF_A_KIND.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(10, Fields.THREE_OF_A_KIND.evaluate(new Integer[]{2, 1, 2, 3, 2}));
        assertEquals(12, Fields.THREE_OF_A_KIND.evaluate(new Integer[]{2, 3, 2, 3, 2}));
        assertEquals(17, Fields.THREE_OF_A_KIND.evaluate(new Integer[]{3, 3, 3, 3, 5}));
    }

    @Test
    public void testEvaluateFourOfAKind() {
        assertEquals(0, Fields.FOUR_OF_A_KIND.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(11, Fields.FOUR_OF_A_KIND.evaluate(new Integer[]{2, 2, 2, 3, 2}));
    }

    @Test
    public void testEvaluateFullHouse() {
        assertEquals(0, Fields.FULL_HOUSE.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(25, Fields.FULL_HOUSE.evaluate(new Integer[]{2, 1, 2, 1, 2}));
    }


    @Test
    public void testEvaluateChance() {
        assertEquals(11, Fields.CHANCE.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(8, Fields.CHANCE.evaluate(new Integer[]{2, 1, 2, 1, 2}));
        assertEquals(30, Fields.CHANCE.evaluate(new Integer[]{6, 6, 6, 6, 6}));
        assertEquals(15, Fields.CHANCE.evaluate(new Integer[]{1, 2, 3, 4, 5}));
        assertEquals(17, Fields.CHANCE.evaluate(new Integer[]{3, 3, 3, 5, 3}));
        assertEquals(22, Fields.CHANCE.evaluate(new Integer[]{6, 5, 5, 4, 2}));
    }

    @Test
    public void testEvaluateSmallStraight() {
        assertEquals(30, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(30, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{2, 1, 3, 4, 6}));
        assertEquals(0, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{2, 1, 1, 1, 2}));
        assertEquals(30, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{1, 6, 2, 3, 4}));
        assertEquals(30, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{2, 3, 4, 6, 5}));
        assertEquals(30, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{1, 2, 3, 4, 5}));
        assertEquals(0, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{2, 1, 5, 5, 5}));
        assertEquals(0, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{6, 5, 4, 2, 2}));
        assertEquals(0, Fields.SMALL_STRAIGHT.evaluate(new Integer[]{1, 2, 3, 5, 6}));
    }

    @Test
    public void testEvaluateLargeStraight() {
        assertEquals(0, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(0, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{2, 1, 3, 4, 6}));
        assertEquals(0, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{2, 1, 1, 1, 2}));
        assertEquals(0, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{1, 6, 2, 3, 4}));
        assertEquals(40, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{2, 3, 4, 6, 5}));
        assertEquals(40, Fields.LARGE_STRAIGHT.evaluate(new Integer[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testEvaluateYahtzee() {
        assertEquals(0, Fields.YAHTZEE.evaluate(new Integer[]{1, 1, 2, 3, 4}));
        assertEquals(0, Fields.YAHTZEE.evaluate(new Integer[]{2, 1, 3, 4, 6}));
        assertEquals(0, Fields.YAHTZEE.evaluate(new Integer[]{2, 1, 1, 1, 2}));
        assertEquals(50, Fields.YAHTZEE.evaluate(new Integer[]{4, 4, 4, 4, 4}));
        assertEquals(0, Fields.YAHTZEE.evaluate(new Integer[]{4, 1, 2, 1, 2}));
        assertEquals(50, Fields.YAHTZEE.evaluate(new Integer[]{1, 1, 1, 1, 1}));
    }
}
