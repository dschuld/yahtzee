package net.hundredtickets.yahtzee;

import javax.validation.Valid;

import net.hundredtickets.yahtzee.model.Scorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import net.hundredtickets.yahtzee.model.Match;
import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.rounds.NoRollsLeftException;
import net.hundredtickets.yahtzee.service.RoundService;
import net.hundredtickets.yahtzee.service.ScorecardService;

@Controller
public class MatchController {

	@Autowired
	private RoundService roundService;

	@Autowired
	private ScorecardService scorecardService;

	private Match match = new Match("David", "Melanie");

	public MatchController() {
		//
	}

	@ModelAttribute("roll")
	public Roll getRoll() {
		return new Roll();
	}

	@ModelAttribute("match")
	public Match getMatch() {
		return match;
	}

	@GetMapping("/match")
	public String getScorecard(@Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round,
			BindingResult bindingResult) {

		roundService.fetchCurrentValues(round);
		return "match";
	}

	@PostMapping("/match")
	public String putScorecardPoints(@Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll roll,
			BindingResult bindingResult) {

		Scorecard activePlayer = match.getActivePlayer();
		match.setActivePlayer(match.getPassivePlayer());
		match.setPassivePlayer(activePlayer);

		roll.setRemainingRolls(3);
		roundService.newRound();
		return "match";
	}

	@GetMapping("/roll")
	public String getRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

		roundService.fetchCurrentValues(roll);

		return "match";
	}

	@PostMapping("/roll")
	public String postRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

		setDiceValues(roll);

		return "match";
	}



    @GetMapping("/reset")
    public String resetMatch(@Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round,
                             BindingResult bindingResult) {

        match.reset();
        roundService.newRound();
//        putScorecardPoints(match, new Roll(), null);
//        getScorecard(match, new Roll(), null);
        return "match";
    }

	private void setDiceValues(Roll roll) {

		try {
			roundService.rollDice(roll);
			roundService.fetchCurrentValues(roll);
		} catch (NoRollsLeftException e) {
			roll.setRemainingRolls(0);
			return;
		}
	}

}
