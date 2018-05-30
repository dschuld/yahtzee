package net.hundredtickets.yahtzee.model;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Roll {

    private Integer remainingRolls;

    private int[] dice = new int[5];

    private boolean[] saveDice = new boolean[5];

    public Integer getDice1() {
        return dice[0];
    }

    public void setDice1(Integer dice) {
        this.dice[0] = dice;
    }

    public Integer getDice2() {
        return dice[1];
    }

    public void setDice2(Integer dice) {
        this.dice[1] = dice;
    }

    public Integer getDice3() {
        return dice[2];
    }

    public void setDice3(Integer dice) {
        this.dice[2] = dice;
    }

    public Integer getDice4() {
        return dice[3];
    }

    public void setDice4(Integer dice) {
        this.dice[3] = dice;
    }

    public Integer getDice5() {
        return dice[4];
    }

    public void setDice5(Integer dice) {
        this.dice[4] = dice;
    }

    public boolean isSaveDice1() {
        return saveDice[0];
    }

    public void setSaveDice1(boolean saveDice) {
        this.saveDice[0] = saveDice;
    }

    public boolean isSaveDice2() {
        return saveDice[1];
    }

    public void setSaveDice2(boolean saveDice) {
        this.saveDice[1] = saveDice;
    }

    public boolean isSaveDice3() {
        return saveDice[2];
    }

    public void setSaveDice3(boolean saveDice) {
        this.saveDice[2] = saveDice;
    }

    public boolean isSaveDice4() {
        return saveDice[3];
    }

    public void setSaveDice4(boolean saveDice) {
        this.saveDice[3] = saveDice;
    }

    public boolean isSaveDice5() {
        return saveDice[4];
    }

    public void setSaveDice5(boolean saveDice) {
        this.saveDice[4] = saveDice;
    }

    public Integer getRemainingRolls() {
        return remainingRolls;
    }

    public void setRemainingRolls(Integer remainingRolls) {
        this.remainingRolls = remainingRolls;
    }

    @Override
    public String toString() {
        return "dice:" + Arrays.toString(dice) + ",saved:" + Arrays.toString(saveDice) + ",remaining:" + remainingRolls;
    }

    public Stream<Integer> getSavedDiceStream() {
        Supplier<Integer> s = new Supplier<Integer>() {

            int currentElement = 0;

            @Override
            public Integer get() {

                while (currentElement < 5 && !saveDice[currentElement]) {
                    currentElement++;
                }

                if (currentElement >= 5) {
                    return null;
                } else {
                    return dice[currentElement++];
                }

            }

        };

        return Stream.generate(s).limit(5);

    }

    public boolean[] getSavedDice() {
        return saveDice;
    }

    /**
     * Sets the dice to the given values.
     */
    public void setDice(Integer dice1, Integer dice2, Integer dice3, Integer dice4, Integer dice5) {
        this.dice[0] = dice1;
        this.dice[1] = dice2;
        this.dice[2] = dice3;
        this.dice[3] = dice4;
        this.dice[4] = dice5;
    }

}
