package net.hundredtickets.yahtzee.model;
/**
 * Interface that evaluates the dice values.
 *
 * @author david
 */
@FunctionalInterface
public interface Evaluator {
    int evaluate(Integer[] dice);

}