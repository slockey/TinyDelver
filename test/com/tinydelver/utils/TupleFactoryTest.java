package com.tinydelver.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TupleFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateCardinalPointsWithXCoordBelowZero() throws Exception {

        int x = -1;
        int y = 0;

        ArrayList<Tuple<Integer, Integer>> result = TupleFactory.generateCardinalPoints(x, y);

        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateCardinalPointsWithYCoordBelowZero() throws Exception {

        int x = 0;
        int y = -1;

        ArrayList<Tuple<Integer, Integer>> result = TupleFactory.generateCardinalPoints(x, y);

        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateCardinalPointsWithXCoordAboveMaxInt() throws Exception {

        int x = Integer.MAX_VALUE + 1;
        int y = 0;

        ArrayList<Tuple<Integer, Integer>> result = TupleFactory.generateCardinalPoints(x, y);

        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateCardinalPointsWithYCoordAboveMaxInt() throws Exception {

        int x = 0;
        int y = Integer.MAX_VALUE + 1;

        ArrayList<Tuple<Integer, Integer>> result = TupleFactory.generateCardinalPoints(x, y);

        assertNotNull(result);
    }

    @Test
    public void shouldGenerateCardinalPoints() throws Exception {

        int x = 0;
        int y = 0;

        ArrayList<Tuple<Integer, Integer>> result = TupleFactory.generateCardinalPoints(x, y);

        assertNotNull(result);
        assertEquals(8, result.size());
        assertEquals(new Tuple<Integer, Integer>(-1, 0), result.get(0));
        assertEquals(new Tuple<Integer, Integer>(-1, -1), result.get(1));
        assertEquals(new Tuple<Integer, Integer>(0, -1), result.get(2));
        assertEquals(new Tuple<Integer, Integer>(1, -1), result.get(3));
        assertEquals(new Tuple<Integer, Integer>(1, 0), result.get(4));
        assertEquals(new Tuple<Integer, Integer>(1, 1), result.get(5));
        assertEquals(new Tuple<Integer, Integer>(0, 1), result.get(6));
        assertEquals(new Tuple<Integer, Integer>(-1, 1), result.get(7));
    }

}
