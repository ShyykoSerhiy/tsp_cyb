package com.tsp.utils;

import java.util.Random;

public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static boolean isInRange(double val) {
        return RANDOM.nextDouble() < val;
    }

}
