package com.tsp.algorithm.sa;

public class PowSequence implements Sequence {

    private final Sequence wrapped;
    private final double pow;

    public PowSequence(Sequence wrapped, double pow) {
        this.wrapped = wrapped;
        this.pow = pow;
    }

    @Override
    public double valueAt(int index) {
        return Math.pow(wrapped.valueAt(index), pow);
    }

}
