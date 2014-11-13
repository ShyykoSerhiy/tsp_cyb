package com.tsp.algorithm.sa;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;
import com.tsp.utils.RandomUtils;

public class SimulatedAnnealingAlgorithm implements Algorithm {

    // maximum length between points that can be swapped
    // can be tweaked to optimize algorithm
    private static final int PARAM_P = 1;

    private final int paramP;
    private final double eps;
    private final Sequence sequence;

    public SimulatedAnnealingAlgorithm() {
        this(PARAM_P, Math.E, new OneByNSequence());
    }

    public SimulatedAnnealingAlgorithm(int paramP, double eps, Sequence sequence) {
        if (paramP <= 0) {
            throw new IllegalArgumentException(
                    "In SA input argument paramP should be possible. Input paramP: " + paramP);
        }
        if (eps <= 0) {
            throw new IllegalArgumentException(
                    "In SA input argument eps should be possible. Input eps: " + eps);
        }
        this.paramP = paramP;
        this.eps = eps;
        this.sequence = sequence;
    }

    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        final int count = tsp.count();
        if (count <= paramP) {
            throw new IllegalArgumentException(
                    "Impossible to solve the tsp: tsp.count <= paramP! tsp.count = " + count
                            + ", paramP = " + paramP);
        }
        Path path = beginPath;
        int index = 0;
        // calculate path's cost
        double cost = path.cost(tsp);
        boolean pathChanged = true;
        // margins of area around vertex to look for swap
        int deltaLeft, deltaRight;
        // looking for path improvements while possible
        while (pathChanged) {
            pathChanged = false;
            // going through all the points in path
            for (int i = 0; i < Math.max(count - paramP, 1) && !pathChanged; i++) {
                // and trying to swap with points that are no further than
                // paramP from current
                deltaLeft = i + 1;// optimized from Math.max(i - paramP, 0);
                deltaRight = Math.min(i + paramP, count - 1);
                for (int j = deltaLeft; j <= deltaRight && !pathChanged; j++) {
                    if (i != j) {
                        // create a copy of path
                        final Path copy = path.clone();
                        // swap two points in path and measure cost
                        copy.swap(i, j);
                        final double copyCost = copy.cost(tsp);
                        final double delta = copyCost - cost;
                        if (delta < 0
                                || (delta > 0 && RandomUtils.isInRange(Math.pow(eps, -delta
                                        / sequence.valueAt(index))))) {
                            // update cost if found better
                            cost = copyCost;
                            // also change the path itself to operate with
                            // updated one on next step
                            path = copy;
                            pathChanged = true;
                            callback.onComputation(tsp, path);
                        }
                        index++;
                    }
                }
            }
        }
        return path;
    }

}
