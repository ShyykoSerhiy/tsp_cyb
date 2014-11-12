package com.tsp.solver;


public class CompoundSolver implements Solver {

    private Solver[] solvers;

    public CompoundSolver(Solver... solvers) {
        this.solvers = solvers;
    }

    @Override
    public void solve() {
        for (Solver solver : solvers) {
            solver.solve();
        }
    }

}
