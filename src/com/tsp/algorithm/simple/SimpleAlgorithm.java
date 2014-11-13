package com.tsp.algorithm.simple;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

public class SimpleAlgorithm implements Algorithm {

    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        final int count = tsp.count();
        Path path = beginPath;
        // calculate path's cost
        double cost = path.cost(tsp);
        boolean pathChanged = true;
        // looking for path improvements while possible
        while (pathChanged) {
            pathChanged = false;
            // going through full table and trying to swap two points in path
            for (int i = 1; i < count - 1 && !pathChanged; i++) {
                for (int j = 1; j < count - 1 && !pathChanged; j++) {
                    // create a copy of path to experiment with
                    final Path copy = path.clone();
                    // swap to points in path and measure cost
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
