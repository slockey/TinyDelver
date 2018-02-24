package com.tinydelver.utils.dice;

public class DiceRoller {

	public static int roll(int numberOfDice, DiceValue diceValue) {
		int result = 0;
		
		for (int idx = 0; idx < numberOfDice; idx++) {
			result += (int)(Math.random() * diceValue.getValue()) + 1;
		}
		
		return result;
	}
}
