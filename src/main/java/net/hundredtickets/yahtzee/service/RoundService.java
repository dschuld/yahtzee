package net.hundredtickets.yahtzee.service;

import org.springframework.stereotype.Service;

@Service
public class RoundService {

	public Integer[] getDice() {
		Integer[] dice = { 1, 2, 3, 4, 50 };
		return dice;
	}

}
