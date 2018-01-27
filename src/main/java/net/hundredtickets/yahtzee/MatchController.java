package net.hundredtickets.yahtzee;

import javax.validation.Valid;

import net.hundredtickets.yahtzee.model.Scorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import net.hundredtickets.yahtzee.model.Match;
import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.rounds.NoRollsLeftException;
import net.hundredtickets.yahtzee.service.RoundService;
import net.hundredtickets.yahtzee.service.ScorecardService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MatchController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private ScorecardService scorecardService;

    private Match match;

    private Map<String, Scorecard> scorecards = new HashMap<>();

    public MatchController() {
        match = new Match("David", "Melanie");
        scorecards.put(match.getActivePlayer().getPlayerName(), match.getActivePlayer());
        scorecards.put(match.getPassivePlayer().getPlayerName(), match.getPassivePlayer());
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
    public String getScorecard(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round,
                               BindingResult bindingResult) {

        cloneScorecards(player);
        roundService.fetchCurrentValues(round);
        return "match";
    }

    @PostMapping("/match")
    public String putScorecardPoints(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll roll,
                                     BindingResult bindingResult) {
        cloneScorecards(player, match);
        if (player.toLowerCase().equals("david")) {
            this.scorecards.put("David", new Scorecard(match.getActivePlayer()));
        } else {
            this.scorecards.put("Melanie", new Scorecard(match.getActivePlayer()));
        }
        cloneScorecards(player);

        roll.setRemainingRolls(3);
        roundService.newRound();
        return "match";
    }

    private void cloneScorecards(String player) {
        if (player.toLowerCase().equals("david")) {
            match.setActivePlayer(new Scorecard(this.scorecards.get("David")));
            match.setPassivePlayer(new Scorecard(this.scorecards.get("Melanie")));
        } else {
            match.setActivePlayer(new Scorecard(this.scorecards.get("Melanie")));
            match.setPassivePlayer(new Scorecard(this.scorecards.get("David")));
        }
    }

    private void cloneScorecards(String playerName, Match match) {
        if (!match.getActivePlayer().getPlayerName().toLowerCase().equals(playerName.toLowerCase())) {
            Scorecard activePlayer = match.getActivePlayer();
            match.setActivePlayer(match.getPassivePlayer());
            match.setPassivePlayer(activePlayer);
        }
    }

    @GetMapping("/roll")
    @ResponseBody
    public Roll getRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

        roundService.fetchCurrentValues(roll);

        return roll;
    }

    @GetMapping("/value/{player}/{field}")
    @ResponseBody
    public Integer getPlayerValue(@PathVariable String player, @PathVariable String field) {
        return match.getScorecard(player).get(field);
    }


    @PostMapping("/roll")
    public String postRoll(String player, @ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

        cloneScorecards(player);
        setDiceValues(roll);

        return "match";
    }

    @PostMapping("/reset")
    public String resetMatch(@Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round,
                             BindingResult bindingResult) {

        match.reset();
        roundService.newRound();
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
