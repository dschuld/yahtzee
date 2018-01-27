package net.hundredtickets.yahtzee;

import javax.validation.Valid;

import net.hundredtickets.yahtzee.model.Scorecard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    private Scorecard davidCard;
    private Scorecard melCard;

    public MatchController() {
        match = new Match("David", "Melanie");
        scorecards.put("David", match.getActivePlayer());
        scorecards.put("Melanie", match.getPassivePlayer());
        davidCard = new Scorecard(match.getActivePlayer());
        melCard = new Scorecard(match.getPassivePlayer());
    }

    private String dirty = null;

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
        if (player.toLowerCase().equals("david")) {
            match.setActivePlayer(new Scorecard(this.davidCard));
            match.setPassivePlayer(new Scorecard(this.melCard));
        } else {
            match.setActivePlayer(new Scorecard(this.melCard));
            match.setPassivePlayer(new Scorecard(this.davidCard));
        }
        roundService.fetchCurrentValues(round);
        return "match";
    }

    @PostMapping("/match")
    public String putScorecardPoints(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll roll,
                                     BindingResult bindingResult) {
        setMatchPlayers(player, match);
        if (player.toLowerCase().equals("david")) {
            this.davidCard = new Scorecard(match.getActivePlayer());
        } else {
            this.melCard = new Scorecard(match.getActivePlayer());
        }
        if (player.toLowerCase().equals("david")) {
            match.setActivePlayer(new Scorecard(this.davidCard));
            match.setPassivePlayer(new Scorecard(this.melCard));
        } else {
            match.setActivePlayer(new Scorecard(this.melCard));
            match.setPassivePlayer(new Scorecard(this.davidCard));
        }
//        this.scorecards.put(player, match.getActivePlayer());
//
//        for(Scorecard card: scorecards.values()) {
//            String playerName = card.getPlayerName();
//            if (!card.getPlayerName().toLowerCase().equals(player.toLowerCase())) {
//                match.setPassivePlayer(card);
//            }
//        }

        roll.setRemainingRolls(3);
        roundService.newRound();
        dirty = player;
        return "match";
    }

    private void setMatchPlayers(String playerName, @Valid @ModelAttribute("match") Match match) {
        if (!match.getActivePlayer().getPlayerName().toLowerCase().equals(playerName.toLowerCase())) {
            Scorecard activePlayer = match.getActivePlayer();
            match.setActivePlayer(match.getPassivePlayer());
            match.setPassivePlayer(activePlayer);
        }
//        if (playerName.toLowerCase().equals("david")) {
//            match.setActivePlayer(davidCard);
//            match.setPassivePlayer(melCard);
//        } else {
//            match.setActivePlayer(melCard);
//            match.setPassivePlayer(davidCard);
//        }
    }

    @GetMapping("/roll")
    public String getRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

        roundService.fetchCurrentValues(roll);

        return "match";
    }

    @GetMapping("/value/{player}/{field}")
    @ResponseBody
    public Integer getPlayerValue(@PathVariable String player, @PathVariable String field) {
        return match.getScorecard(player).get(field);
    }


    @PostMapping("/roll")
    public String postRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

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
