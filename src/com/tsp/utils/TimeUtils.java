package com.tsp.utils;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static long methodTime(Runnable method) {
        final long startTime = System.currentTimeMillis();
        method.run();
        return System.currentTimeMillis() - startTime;
    }

}
