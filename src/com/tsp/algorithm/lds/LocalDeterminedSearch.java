package com.tsp.algorithm.lds;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

/**
 * local determined search algorithm as it was defined on original lectures.
 */
public class LocalDeterminedSearch implements Algorithm {
    // maximum length between points that can be swapped
    // can be tweaked to optimize algorithm
    private static final int PARAM_P = 1;

    private final int paramP;

    public LocalDeterminedSearch() {
        this(PARAM_P);
    }

    public LocalDeterminedSearch(int paramP) {
        if (paramP <= 0) {
            throw new IllegalArgumentException(
                    "In LDS input argument should be possible. Input param: " + paramP);
        }
        this.paramP = paramP;
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
                        if (copyCost < cost) {
                            // update cost if found better
                            cost = copyCost;
                            // also change the path itself to operate with
                            // updated one on next step
                            path = copy;
                            pathChanged = true;
                            callback.onComputation(tsp, path);
                        }
                    }
                }
            }
        }
        return path;
    }

}
