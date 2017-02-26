package com.tinydelver.utils;

import java.util.ArrayList;

public class TupleFactory {

	public static ArrayList<Tuple<Integer,Integer>> generateCardinalPoints(int x, int y) {
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
