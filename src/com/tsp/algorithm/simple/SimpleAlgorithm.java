package com.tsp.algorithm.simple;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.Path;
import com.tsp.model.TSPInstance;

public class SimpleAlgorithm implements Algorithm {

    @Override
    public Path compute(TSPInstance tsp, ComputationCallback callback) {
        double bestCost = Double.MAX_VALUE;
        Path bestPath = null;
        for (int k = 0; k < tsp.count(); k++) {
            final Path kPath = getBest(tsp, k);
            final double kCost = kPath.cost(tsp);
            if (kCost < bestCost) {
                bestCost = kCost;
                bestPath = kPath;
            }
            callback.onComputation(tsp, kPath);
        }
        return bestPath;
    }

    private Path getBest(TSPInstance tsp, int start) {
        final int count = tsp.count();
        Path path = Path.createRounded(count, start);
        double cost = path.cost(tsp);
        boolean pathChanged = true;
        while (pathChanged) {
            pathChanged = false;
            for (int i = 1; i < count - 1 && !pathChanged; i++) {
                for (int j = 1; j < count - 1 && !pathChanged; j++) {
                    final Path copy = Path.copy(path);
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
