package net.hundredtickets.yahtzee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.hundredtickets.yahtzee.model.Round;
import net.hundredtickets.yahtzee.service.RoundService;

@Controller
public class RoundController {

	@Autowired
	private RoundService roundService;

	@ModelAttribute("round")
	public Round getRound() {
		return new Round();
	}
	//
	// @GetMapping("/round")
	// public String getRoll(@ModelAttribute("round") Round round, BindingResult
	// bindingResult) {
	//
	// setDiceValues(round);
	//
	// return "match :: round";
	// }
	//
	// @PostMapping("/round")
	// public String postRoll(@ModelAttribute("round") Round round,
	// BindingResult bindingResult) {
	//
	// setDiceValues(round);
	//
	// return "match :: round";
	// }

	private void setDiceValues(Round round) {
		Integer[] dice = roundService.getDice();
		round.setDice1(dice[0]);
		round.setDice2(dice[1]);
		round.setDice3(dice[2]);
		round.setDice4(dice[3]);
		round.setDice5(dice[4]);
	}
}
