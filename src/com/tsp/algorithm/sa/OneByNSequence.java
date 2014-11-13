package com.tsp.algorithm.sa;

public class OneByNSequence implements Sequence {

    @Override
    public double valueAt(int index) {
        return 1d / (index + 1);
    }

}
