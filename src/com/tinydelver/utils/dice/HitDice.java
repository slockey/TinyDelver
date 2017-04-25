package com.tinydelver.utils.dice;

/**
 * Represents the toughness of an actor (creature, object, etc.).
 * Can be used to determine hit points.
 *
 */
public class HitDice {

	private int number;
	private DiceValue diceValue;
	
	public HitDice(int number, DiceValue diceValue) {
		this.setNumber(number);
		this.setDiceValue(diceValue);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public DiceValue getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(DiceValue diceValue) {
		this.diceValue = diceValue;
	}
	
}
