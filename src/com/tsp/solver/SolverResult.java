package com.tsp.solver;

import com.tsp.model.path.Path;

public class SolverResult {

    private final double cost;
    private final Path path;
    private final long time;
    private final int eps;

    /* package */SolverResult(double cost, Path path, long time, int eps) {
        this.cost = cost;
        this.path = path;
        this.time = time;
        this.eps = eps;
    }

    public double getCost() {
        return cost;
    }

    public Path getPath() {
        return path;
    }

    public long getTime() {
        return time;
    }

    public int getEps() {
        return eps;
    }

}
