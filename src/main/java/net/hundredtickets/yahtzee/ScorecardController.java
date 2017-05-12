package net.hundredtickets.yahtzee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hundredtickets.yahtzee.model.Match;
import net.hundredtickets.yahtzee.service.ScorecardService;

@Controller
public class ScorecardController {

	@Autowired
	private ScorecardService scorecardService;

	private Match match = new Match("David", "Melanie");

	public ScorecardController() {

	}

	@ModelAttribute("match")
	public Match getMatch() {

		return match;
	}

	@RequestMapping("/match")
	public String getScorecard(@Valid @ModelAttribute("match") Match match, BindingResult bindingResult) {

		return "match";
	}

	@PostMapping("/match")
	public String putScorecardPoints(@Valid @ModelAttribute("match") Match match, BindingResult bindingResult) {
		for (ObjectError error : bindingResult.getAllErrors()) {
			System.out.println(error);
		}
		return "match";
	}
}
