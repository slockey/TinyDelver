package com.tinydelver.utils.dice;

public enum DiceValue {

	D0(0),
	D2(2),
	D4(4),
	D6(6),
	D8(8),
	D10(10),
	D12(12),
	D20(20);

	private final int value;
	
	private DiceValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
