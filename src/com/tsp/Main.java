package com.tsp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import com.tsp.algorithm.simple.LocalDeterminedSearch;
import org.xml.sax.SAXException;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.algorithm.simple.SimpleAlgorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;
import com.tsp.model.path.PathFactory;
import com.tsp.model.path.RoundedFactory;
import com.tsp.solver.CompoundSolver;
import com.tsp.solver.SimpleSolver;
import com.tsp.solver.Solver;
import com.tsp.ui.Drawer;
import com.tsp.ui.EmptyDrawer;

public class Main {

    public final static String[] xmlNames = {
            "br17", "bays29", "ftv33", "ftv35", "swiss42", "p43", "ftv44", "ftv47", "att48",
            "ry48p", "eil51", "berlin52", "ft53", "ftv55", "ftv64", "eil76", "eil101"
    };

    private final static int NUMBER_OF_PATHS = 1;

    private final static String SIMPLE_ALGO = "simple";
    private final static String LOCAL_DETERMINED_SEARCH_ALGO = "lds";

    // all algorithms available
    private final static Map<String, Algorithm> algorithms = new HashMap<String, Algorithm>() {{
        put(SIMPLE_ALGO, new SimpleAlgorithm());
        put(LOCAL_DETERMINED_SEARCH_ALGO, new LocalDeterminedSearch());
    }};

    public static void main(String[] argc) throws FileNotFoundException, SAXException, IOException,
            ParserConfigurationException {
        final Algorithm algorithm = algorithms.get(SIMPLE_ALGO);
        final Drawer drawer = new EmptyDrawer();
        final PathFactory factory = new RoundedFactory(NUMBER_OF_PATHS);

        final ComputationCallback drawCallback = new ComputationCallback() {

            @Override
            public void onComputation(TSPInstance tsp, Path path) {
                // TODO run on main thread
                drawer.draw(tsp, path);
            }

        };

        // create list of solvers for all problem definitions
        List<Solver> solvers = new ArrayList<Solver>(xmlNames.length);
        for (String xmlName : xmlNames) {
            solvers.add(new SimpleSolver(TSPInstance.fromXml(xmlName), algorithm, drawCallback));
        }

        // pack everything in one compound solver
        final Solver solver = new CompoundSolver(solvers.toArray(new Solver[solvers.size()]));

        new Thread(new Runnable() {

            @Override
            public void run() {
                solver.solve(factory);
            }

        }).start();
    }

}
