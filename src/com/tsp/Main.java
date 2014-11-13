package com.tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import com.tsp.algorithm.greedy.GreedyAlgorithm;
import org.xml.sax.SAXException;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.algorithm.lds.LocalDeterminedSearch;
import com.tsp.algorithm.sa.OneByNSequence;
import com.tsp.algorithm.sa.PowSequence;
import com.tsp.algorithm.sa.SimulatedAnnealingAlgorithm;
import com.tsp.algorithm.simple.SimpleAlgorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.DefinedPathFactory;
import com.tsp.model.path.Path;
import com.tsp.model.path.PathFactory;
import com.tsp.model.path.RoundedFactory;
import com.tsp.solver.CompoundSolver;
import com.tsp.solver.SimpleSolver;
import com.tsp.solver.SolverResult;
import com.tsp.ui.Drawer;
import com.tsp.ui.EmptyDrawer;
import com.tsp.utils.XmlUtils;

public class Main {

    private static final String FORMAT = "%10s: %10.2f %10d %10d";

    public final static String[] XML_NAMES = { "br17", "bays29", "ftv33", "ftv35", "swiss42",
            "p43", "ftv44", "ftv47", "att48", "ry48p", "eil51", "berlin52", "ft53", "ftv55",
            "ftv64", "eil76", "eil101" };

    private final static String ALGO_SIMPLE = "simple";
    private final static String ALGO_LOCAL_DETERMINED_SEARCH = "lds";
    private final static String ALGO_SIMULATED_ANNEALING = "sa";
    private final static String ALGO_GREEDY = "greedy";

    // all algorithms available
    private final static Map<String, Algorithm> ALGORITHMS = new HashMap<String, Algorithm>() {
        {
            put(ALGO_SIMPLE, new SimpleAlgorithm());
            put(ALGO_LOCAL_DETERMINED_SEARCH, new LocalDeterminedSearch(5));
            put(ALGO_SIMULATED_ANNEALING, new SimulatedAnnealingAlgorithm(5, Math.E,
                    new PowSequence(new OneByNSequence(), 0.0001)));
            put(ALGO_GREEDY, new GreedyAlgorithm());
        }
    };

    public static void main(String[] argc) {
        final Algorithm algorithm = ALGORITHMS.get(ALGO_GREEDY);
        final Drawer drawer = new EmptyDrawer();

        final ComputationCallback drawCallback = new ComputationCallback() {

            @Override
            public void onComputation(TSPInstance tsp, Path path) {
                // TODO run on main thread
                drawer.draw(tsp, path);
            }

        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (String problem : XML_NAMES) {
                        final CompoundSolver compoundSolver = new CompoundSolver();
                        final TSPInstance tsp = TSPInstance.fromXml(problem);
                        for (PathFactory factory : getFactoriesFor(problem)) {
                            compoundSolver.add(new SimpleSolver(tsp, algorithm, drawCallback,
                                    factory));
                        }
                        final SolverResult result = compoundSolver.solve();
                        System.out.println(String.format(FORMAT, tsp.getName(), result.getCost(),
                                result.getEps(), result.getTime()));
                        // TODO use bestPath to fill the table sheets
                    }
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private static Iterable<PathFactory> getFactoriesFor(String problem)
            throws FileNotFoundException {
        final Collection<PathFactory> pathes = new ArrayList<PathFactory>();
        final Scanner scanner = new Scanner(new File(XmlUtils.permutationForName(problem)));
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            final String[] values = line.split(" ");
            final int size = values.length;
            final List<Integer> permutation = new ArrayList<Integer>(size);
            for (int i = 0; i < size; i++) {
                permutation.add(Integer.parseInt(values[i]));
            }
            pathes.add(new RoundedFactory(new DefinedPathFactory(permutation)));
        }
        scanner.close();
        return pathes;
    }

}
