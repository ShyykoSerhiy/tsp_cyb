package com.tsp.solver;

import com.tsp.model.path.PathFactory;

public class CompoundSolver implements Solver {

    private Solver[] solvers;

    public CompoundSolver(Solver... solvers) {
        this.solvers = solvers;
    }

    @Override
    public void solve(PathFactory factory) {
        for (Solver solver : solvers) {
            solver.solve(factory);
        }
    }

}
