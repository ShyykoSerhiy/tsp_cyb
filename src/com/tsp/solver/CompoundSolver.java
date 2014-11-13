package com.tsp.solver;

import sun.java2d.pipe.SolidTextRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        int threadNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);

        try {
            // initiate parallel run
            Set<Future<SolverResult>> futureTasks = new HashSet<Future<SolverResult>>();
            for (final Solver solver : solvers) {
                futureTasks.add(executor.submit(new Callable<SolverResult>() {
                    @Override
                    public SolverResult call() throws Exception {
                        return solver.solve();
                    }
                }));
            }

            // obtain results and find the best
            for (Future<SolverResult> future : futureTasks) {
                final SolverResult result = future.get();
                if (bestResult.getCost() > result.getCost()) {
                    bestResult = result;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {
                executor.shutdownNow();
            }
        }
        return bestResult;
    }

}
