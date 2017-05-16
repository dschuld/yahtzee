package net.hundredtickets.yahtzee.model;

public class Round {

	private Integer dice1;
	private Integer dice2;
	private Integer dice3;
	private Integer dice4;
	private Integer dice5;

	private boolean saveDice1;
	private boolean saveDice2;
	private boolean saveDice3;
	private boolean saveDice4;
	private boolean saveDice5;

	public Integer getDice1() {
		return dice1;
	}

	public void setDice1(Integer dice1) {
		this.dice1 = dice1;
	}

	public Integer getDice2() {
		return dice2;
	}

	public void setDice2(Integer dice2) {
		this.dice2 = dice2;
	}

	public Integer getDice3() {
		return dice3;
	}

	public void setDice3(Integer dice3) {
		this.dice3 = dice3;
	}

	public Integer getDice4() {
		return dice4;
	}

	public void setDice4(Integer dice4) {
		this.dice4 = dice4;
	}

	public Integer getDice5() {
		return dice5;
	}

	public void setDice5(Integer dice5) {
		this.dice5 = dice5;
	}

	public boolean isSaveDice1() {
		return saveDice1;
	}

	public void setSaveDice1(boolean saveDice1) {
		this.saveDice1 = saveDice1;
	}

	public boolean isSaveDice2() {
		return saveDice2;
	}

	public void setSaveDice2(boolean saveDice2) {
		this.saveDice2 = saveDice2;
	}

	public boolean isSaveDice3() {
		return saveDice3;
	}

	public void setSaveDice3(boolean saveDice3) {
		this.saveDice3 = saveDice3;
	}

	public boolean isSaveDice4() {
		return saveDice4;
	}

	public void setSaveDice4(boolean saveDice4) {
		this.saveDice4 = saveDice4;
	}

	public boolean isSaveDice5() {
		return saveDice5;
	}

	public void setSaveDice5(boolean saveDice5) {
		this.saveDice5 = saveDice5;
	}

}
