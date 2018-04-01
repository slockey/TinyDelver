package com.tinydelver.utils;

import java.util.ArrayList;

public class TupleFactory {

    /**
     * Generate a tuple representing the cardinal points around a specified x/y position.
     * For purposes of this project we will limit initial coords to a minimum of 0 to a maximum 
     * of Integer.MAX_VALUE
     * 
     * Note: Order of the list is counter clock-wise from x-1/y
     * 
     * +-------+-------+-------+
     * |x-1/y+1| x/y+1 |x+1/y+1|
     * +-------+-------+-------+
     * | x-1/y |  x/y  | x+1/y |
     * +-------+-------+-------+
     * |x-1/y-1| x/y-1 | x+1/y-1
     * +-------+-------+-------+
     * 
     * @param x as positive Integer
     * @param y as positive Integer
     * @return ArrayList of tuples representing adjacent coords
     */
    public static ArrayList<Tuple<Integer,Integer>> generateCardinalPoints(int x, int y) {
        if (x < 0 || x > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("X must be an Integer between 0 and Integer.MAX_VALUE");
        }

        if (y < 0 || y > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Y must be an Integer between 0 and Integer.MAX_VALUE");
        }

        ArrayList<Tuple<Integer,Integer>> points = new ArrayList<Tuple<Integer,Integer>>();

        points.add(new Tuple<Integer,Integer>(x - 1, y));
        points.add(new Tuple<Integer,Integer>(x - 1, y - 1));
        points.add(new Tuple<Integer,Integer>(x, y - 1));
        points.add(new Tuple<Integer,Integer>(x + 1, y - 1));
        points.add(new Tuple<Integer,Integer>(x + 1, y));
        points.add(new Tuple<Integer,Integer>(x + 1, y + 1));
        points.add(new Tuple<Integer,Integer>(x, y + 1));
        points.add(new Tuple<Integer,Integer>(x - 1, y + 1));
        
        return points;
	}
}
