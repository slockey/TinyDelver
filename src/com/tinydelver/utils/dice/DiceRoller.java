package com.tinydelver.utils.dice;

public class DiceRoller implements IDiceRoller {

    private static final IDiceRoller roller = new DiceRoller();

    private DiceRoller() { }

    public static IDiceRoller getInstance() {
        return roller;
    }

    /* (non-Javadoc)
     * @see com.tinydelver.utils.dice.IDiceRoller#roll(int, com.tinydelver.utils.dice.DiceValue)
     */
    @Override
    public int roll(int numberOfDice, DiceValue diceValue) {
        int result = 0;
        
        for (int idx = 0; idx < numberOfDice; idx++) {
            result += (int)(Math.random() * diceValue.getValue()) + 1;
        }
        
        return result;
   }
}
