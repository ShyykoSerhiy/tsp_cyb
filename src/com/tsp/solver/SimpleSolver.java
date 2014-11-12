package com.tsp.solver;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.model.Path;
import com.tsp.model.TSPInstance;

public class SimpleSolver implements Solver {

    private static final String FORMAT = "%10s: %10.2f %10d %10d";

    private TSPInstance tsp;
    private Algorithm algorithm;
    private ComputationCallback callback;

    public SimpleSolver(TSPInstance tsp, Algorithm algorithm,
            ComputationCallback callback) {
        this.tsp = tsp;
        this.algorithm = algorithm;
        this.callback = callback;
    }

    @Override
    public void solve() {
        final long startTime = System.currentTimeMillis();
        final Path bestPath = algorithm.compute(tsp, callback);
        final long time = System.currentTimeMillis() - startTime;
        final double bestCost = bestPath.cost(tsp);
        final int eps = (int) (100 * (bestCost - tsp.getOptimal()) / tsp
                .getOptimal());
        System.out.println(String.format(FORMAT, tsp.getName(), bestCost, eps,
                time));
    }
}
