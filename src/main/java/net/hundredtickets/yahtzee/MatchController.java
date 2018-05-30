package net.hundredtickets.yahtzee;

import net.hundredtickets.yahtzee.model.Match;
import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.model.Scorecard;
import net.hundredtickets.yahtzee.rounds.NoRollsLeftException;
import net.hundredtickets.yahtzee.service.RoundService;
import net.hundredtickets.yahtzee.service.ScorecardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MatchController {

    private RoundService roundService;

    private ScorecardService scorecardService;

    private Match match;

    private Map<String, Scorecard> scorecards = new HashMap<>();

    public MatchController(RoundService roundService, ScorecardService scorecardService) {
        this.scorecardService = scorecardService;
        this.roundService = roundService;
        match = new Match("Player1", "Player2");
        scorecards.put(match.getActivePlayer().getPlayerId(), match.getActivePlayer());
        scorecards.put(match.getPassivePlayer().getPlayerId(), match.getPassivePlayer());
    }

    @ModelAttribute("roll")
    public Roll getRoll() {
        return new Roll();
    }

    @ModelAttribute("match")
    public Match getMatch() {
        return match;
    }


    @GetMapping("/start")
    public String startMatch(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round) {

        String playerId = match.addPlayer(player);
        this.scorecards.get(playerId).setPlayerName(player);
        cloneScorecards(playerId);

        roundService.fetchCurrentValues(round);
        return "match";
    }

    @GetMapping("/match")
    public String getScorecard(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round) {

        cloneScorecards(player);
        roundService.fetchCurrentValues(round);
        return "match";
    }

    @PostMapping("/match")
    public String putScorecardPoints(String player, @Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll roll) {
        setActivePlayerScorecard(player, match);
        if (player.toLowerCase().equals("player1")) {
            this.scorecards.put("Player1", new Scorecard(match.getActivePlayer()));
        } else {
            this.scorecards.put("Player2", new Scorecard(match.getActivePlayer()));
        }
        cloneScorecards(player);

        roll.setRemainingRolls(3);
        roundService.newRound();
        return "match";
    }

    private void cloneScorecards(String player) {
        if (player.toLowerCase().equals("player1")) {
            match.setActivePlayer(new Scorecard(this.scorecards.get("Player1")));
            match.setPassivePlayer(new Scorecard(this.scorecards.get("Player2")));
        } else {
            match.setActivePlayer(new Scorecard(this.scorecards.get("Player2")));
            match.setPassivePlayer(new Scorecard(this.scorecards.get("Player1")));
        }
    }

    private void setActivePlayerScorecard(String playerId, Match match) {
        if (!match.getActivePlayer().getPlayerId().toLowerCase().equals(playerId.toLowerCase())) {
            Scorecard activePlayer = match.getActivePlayer();
            match.setActivePlayer(match.getPassivePlayer());
            match.setPassivePlayer(activePlayer);
        }
    }


    @PostMapping("/roll")
    public String postRoll(String player, @ModelAttribute("roll") Roll roll) {

        cloneScorecards(player);
        setDiceValues(roll);

        return "match";
    }

    @PostMapping("/reset")
    public String resetMatch(@Valid @ModelAttribute("match") Match match, @ModelAttribute("roll") Roll round) {

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

    @GetMapping("/value/{player}/{field}")
    @ResponseBody
    public Integer getPlayerValue(@PathVariable String player, @PathVariable String field) {
        return match.getScorecard(player).get(field);
    }

}
