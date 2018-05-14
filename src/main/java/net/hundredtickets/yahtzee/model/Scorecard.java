package net.hundredtickets.yahtzee.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * A Yahtzee Scorecard, containing a field for each scorecard entry and
 * respective validations.
 *
 * @author david
 */
@Entity
public class Scorecard {

    @Id
    private Long id;

    private String playerName;

    private String playerId;

    private long matchId;

    @Min(0)
    @Max(5)
    private Integer ones;
    @Min(0)
    @Max(10)
    private Integer twos;
    @Min(0)
    @Max(15)
    private Integer threes;
    @Min(0)
    @Max(20)
    private Integer fours;
    @Min(0)
    @Max(25)
    private Integer fives;
    @Min(0)
    @Max(30)
    private Integer sixes;

    @Min(0)
    @Max(30)
    private Integer threeOfAKind;
    @Min(0)
    @Max(30)
    private Integer fourOfAKind;
    private Integer fullHouse;
    private Integer smallStraight;
    private Integer largeStraight;
    @Min(0)
    private Integer yahtzee;
    @Min(0)
    @Max(30)
    private Integer chance;

    public Scorecard() {

    }

    public Scorecard(String playerId) {
        this.playerId = playerId;
    }

    public Scorecard(Scorecard card) {
        this.playerName = card.playerName;
        this.playerId = card.playerId;
        this.matchId = card.matchId;
        this.ones = card.ones;
        this.twos = card.twos;
        this.threes = card.threes;
        this.fours = card.fours;
        this.fives = card.fives;
        this.sixes = card.sixes;
        this.threeOfAKind = card.threeOfAKind;
        this.fourOfAKind = card.fourOfAKind;
        this.fullHouse = card.fullHouse;
        this.smallStraight = card.smallStraight;
        this.largeStraight = card.largeStraight;
        this.yahtzee = card.yahtzee;
        this.chance = card.chance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Integer getOnes() {
        return ones;
    }

    public void setOnes(Integer ones) {
        this.ones = ones;
    }

    public Integer getTwos() {
        return twos;
    }

    public void setTwos(Integer twos) {
        this.twos = twos;
    }

    public Integer getThrees() {
        return threes;
    }

    public void setThrees(Integer threes) {
        this.threes = threes;
    }

    public Integer getFours() {
        return fours;
    }

    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public Integer getFives() {
        return fives;
    }

    public void setFives(Integer fives) {
        this.fives = fives;
    }

    public Integer getSixes() {
        return sixes;
    }

    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }

    public Integer getThreeOfAKind() {
        return threeOfAKind;
    }

    public void setThreeOfAKind(Integer threeOfAKind) {
        this.threeOfAKind = threeOfAKind;
    }

    public Integer getFourOfAKind() {
        return fourOfAKind;
    }

    public void setFourOfAKind(Integer fourOfAKind) {
        this.fourOfAKind = fourOfAKind;
    }

    public Integer getFullHouse() {
        return fullHouse;
    }

    public void setFullHouse(Integer fullHouse) {
        this.fullHouse = fullHouse;
    }

    public Integer getSmallStraight() {
        return smallStraight;
    }

    public void setSmallStraight(Integer smallStraight) {
        this.smallStraight = smallStraight;
    }

    public Integer getLargeStraight() {
        return largeStraight;
    }

    public void setLargeStraight(Integer largeStraight) {
        this.largeStraight = largeStraight;
    }

    public Integer getYahtzee() {
        return yahtzee;
    }

    public void setYahtzee(Integer yahtzee) {
        this.yahtzee = yahtzee;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public Integer getTotalUpper() {
        return nullToZero(ones) + nullToZero(twos) + nullToZero(threes) + nullToZero(fours) + nullToZero(fives)
                + nullToZero(sixes);
    }

    private Integer nullToZero(Integer number) {
        return number == null ? 0 : number;
    }

    public Integer getBonus() {
        return getTotalUpper() >= 63 ? 35 : 0;
    }

    public Integer getTotalUpperInclBonus() {
        return getTotalUpper() >= 63 ? getTotalUpper() + getBonus() : getTotalUpper();
    }

    public Integer getTotalLower() {
        return nullToZero(threeOfAKind) + nullToZero(fourOfAKind) + nullToZero(getFullHouse())
                + nullToZero(getSmallStraight()) + nullToZero(getLargeStraight()) + nullToZero(yahtzee)
                + nullToZero(chance);
    }

    public Integer getGrandTotal() {
        return getTotalUpperInclBonus() + getTotalLower();
    }

    @Override
    public String toString() {
        return "[" + ones + "," + twos + "," + threes + "," + fours + "," + fives + "," + sixes + "]";
    }

    public Integer get(String fieldName) {
        Integer value = null;
        try {
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            value = (Integer) this.getClass().getDeclaredMethod(getterName).invoke(this);
        } catch (Exception e) {
            return null;
        }
        return value;
    }
}
