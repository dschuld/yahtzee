package net.hundredtickets.yahtzee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hundredtickets.yahtzee.model.Match;
import net.hundredtickets.yahtzee.model.Round;
import net.hundredtickets.yahtzee.service.RoundService;
import net.hundredtickets.yahtzee.service.ScorecardService;

@Controller
public class ScorecardController {

	@Autowired
	private RoundService roundService;

	@Autowired
	private ScorecardService scorecardService;

	private Match match = new Match("David", "Melanie");

	public ScorecardController() {

	}

	@ModelAttribute("round")
	public Round getRound() {
		return new Round();
	}

	@ModelAttribute("match")
	public Match getMatch() {

		return match;
	}

	@RequestMapping("/match")
	public String getScorecard(@Valid @ModelAttribute("match") Match match, @ModelAttribute("round") Round round,
			BindingResult bindingResult) {

		setDiceValues(round);

		return "match";
	}

	@PostMapping("/match")
	public String putScorecardPoints(@Valid @ModelAttribute("match") Match match, @ModelAttribute("round") Round round,
			BindingResult bindingResult) {
		for (ObjectError error : bindingResult.getAllErrors()) {
			System.out.println(error);
		}

		setDiceValues(round);
		return "match";
	}

	@GetMapping("/round")
	public String getRoll(@ModelAttribute("round") Round round, BindingResult bindingResult) {

		setDiceValues(round);

		return "match";
	}

	@PostMapping("/round")
	public String postRoll(@ModelAttribute("round") Round round, BindingResult bindingResult) {

		setDiceValues(round);

		return "match";
	}

	private void setDiceValues(Round round) {
		Integer[] dice = roundService.getDice();
		round.setDice1(dice[0]);
		round.setDice2(dice[1]);
		round.setDice3(dice[2]);
		round.setDice4(dice[3]);
		round.setDice5(dice[4]);
	}

}
