package com.tsp.utils;

public final class ArrayUtils {

    private ArrayUtils() {

    }

    public static double[][] createEmpty(int count) {
        final double[][] result = new double[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                result[i][j] = 0;
            }
        }
        return result;
    }

}
