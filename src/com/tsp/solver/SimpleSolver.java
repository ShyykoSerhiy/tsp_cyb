package com.tsp.solver;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;
import com.tsp.model.path.PathFactory;

public class SimpleSolver implements Solver {

    private final TSPInstance tsp;
    private final Algorithm algorithm;
    private final ComputationCallback callback;
    private final PathFactory factory;

    public SimpleSolver(TSPInstance tsp, Algorithm algorithm, ComputationCallback callback,
            PathFactory factory) {
        this.tsp = tsp;
        this.algorithm = algorithm;
        this.callback = callback;
        this.factory = factory;
    }

    @Override
    public SolverResult solve() {
        final long startTime = System.currentTimeMillis();
        final Path path = algorithm.compute(tsp, factory.create(tsp), callback);
        final long duration = System.currentTimeMillis() - startTime;
        final double cost = path.cost(tsp);
        final int eps = (int) (100 * (cost - tsp.getOptimal()) / tsp.getOptimal());
        return new SolverResult(cost, path, duration, eps);
    }

}
