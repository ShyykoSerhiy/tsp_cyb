package com.tsp.solver;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;
import com.tsp.model.path.PathFactory;

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
    public void solve(PathFactory factory) {
        final long startTime = System.currentTimeMillis();
        double bestCost = Double.MAX_VALUE;
        Path bestPath;
        long bestPathTime = 0;
        int bestEps = 0;

        for (Path startPath : factory.create(tsp)) {
            Path path = algorithm.compute(tsp, startPath, callback);
            final long currentTime = System.currentTimeMillis() - startTime;
            double cost = path.cost(tsp);
            if (cost < bestCost) {
                bestPath = path;
                bestCost = cost;
                bestPathTime = currentTime;
                bestEps = (int) (100 * (bestCost - tsp.getOptimal()) / tsp
                        .getOptimal());
            }
        }
        System.out.println(String.format(FORMAT, tsp.getName(), bestCost, bestEps,
                bestPathTime));
        // TODO use bestPath to fill the table sheets
    }
}
