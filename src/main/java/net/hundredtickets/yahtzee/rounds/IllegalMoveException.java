package net.hundredtickets.yahtzee.rounds;

public class IllegalMoveException extends RuntimeException {

	private static final long serialVersionUID = 8294554922216287920L;

	public IllegalMoveException(String message) {
		super(message);
	}

}
