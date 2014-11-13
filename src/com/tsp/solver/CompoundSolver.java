package com.tsp.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CompoundSolver implements Solver {

    private static final SolverResult WORST = new SolverResult(Double.MAX_VALUE, null,
            Long.MAX_VALUE, 100);

    private final Collection<Solver> solvers;

    public CompoundSolver() {
        solvers = new ArrayList<Solver>();
    }

    public CompoundSolver(Solver... solvers) {
        this.solvers = Arrays.asList(solvers);
    }

    public void add(Solver solver) {
        solvers.add(solver);
    }

    public void remove(Solver solver) {
        solvers.remove(solver);
    }

    public void clear() {
        solvers.clear();
    }

    @Override
    public SolverResult solve() {
        SolverResult bestResult = WORST;
        for (Solver solver : solvers) {
            final SolverResult result = solver.solve();
            if (bestResult.getCost() > result.getCost()) {
                bestResult = result;
            }
        }
        return bestResult;
    }

}
