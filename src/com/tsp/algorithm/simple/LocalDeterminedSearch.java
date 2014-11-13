package com.tsp.algorithm.simple;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

/**
 * local determined search algorithm as it was defined on original lections
 */
public class LocalDeterminedSearch implements Algorithm {
    // maximum length between points that can be swapped
    // can be tweaked to optimize algo
    private static final int PARAM_P = 1;

    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        final int count = tsp.count();
        Path path = beginPath;
        // calculate path's cost
        double cost = path.cost(tsp);
        boolean pathChanged = true;
        // margins of area around vertex to look for swap
        int delta_left, delta_right;
        // looking for path improvements while possible
        while (pathChanged) {
            pathChanged = false;
            // going through all the points in path
            for (int i = 0; i < Math.max(count - PARAM_P, 1) && !pathChanged; i++) {
                // and trying to swap with points that are no further than PARAM_P from current
                delta_left = i + 1;//optimized from Math.max(i - PARAM_P, 0);
                delta_right = Math.min(i + PARAM_P, count - 1);
                for (int j = delta_left; j <= delta_right && !pathChanged; j++) {
                    if (i == j) {
                        continue;
                    }
                    // create a copy of path
                    final Path copy = path.clone();
                    // swap two points in path and measure cost
                    copy.swap(i, j);
                    final double copyCost = copy.cost(tsp);
                    if (copyCost < cost) {
                        // update cost if found better
                        cost = copyCost;
                        // also change the path itself to operate with updated one on next step
                        path = copy;
                        pathChanged = true;
                    }
                }
            }
        }
        return path;
    }
}
