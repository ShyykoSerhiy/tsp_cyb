package com.tsp.algorithm.simple;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

public class SimpleAlgorithm implements Algorithm {

    @Override
    public Path compute(TSPInstance tsp, Path beginPath,
            ComputationCallback callback) {
        final int count = tsp.count();
        Path path = beginPath;
        double cost = path.cost(tsp);
        boolean pathChanged = true;
        while (pathChanged) {
            pathChanged = false;
            for (int i = 1; i < count - 1 && !pathChanged; i++) {
                for (int j = 1; j < count - 1 && !pathChanged; j++) {
                    final Path copy = path.clone();
                    copy.swap(i, j);
                    final double copyCost = copy.cost(tsp);
                    if (copyCost < cost) {
                        cost = copyCost;
                        path = copy;
                        pathChanged = true;
                    }
                }
            }
        }
        return path;
    }

}
