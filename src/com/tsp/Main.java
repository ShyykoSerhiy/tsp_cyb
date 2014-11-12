package com.tsp;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.algorithm.simple.SimpleAlgorithm;
import com.tsp.model.Path;
import com.tsp.model.TSPInstance;
import com.tsp.solver.CompoundSolver;
import com.tsp.solver.SimpleSolver;
import com.tsp.solver.Solver;
import com.tsp.ui.Drawer;
import com.tsp.ui.EmptyDrawer;

public class Main {

    public static void main(String[] argc) throws FileNotFoundException,
            SAXException, IOException, ParserConfigurationException {
        final Algorithm algorithm = new SimpleAlgorithm();
        final Drawer drawer = new EmptyDrawer();

        final ComputationCallback drawCallback = new ComputationCallback() {

            @Override
            public void onComputation(TSPInstance tsp, Path path) {
                // TODO run on main thread
                drawer.draw(tsp, path);
            }

        };
        
        final Solver solver = new CompoundSolver(
                new SimpleSolver(TSPInstance.fromXml("data/br17.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/bays29.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv33.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv35.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/swiss42.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/p43.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv44.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv47.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/att48.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ry48p.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/eil51.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/berlin52.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ft53.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv55.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/ftv64.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/eil76.xml"), algorithm, drawCallback),
                new SimpleSolver(TSPInstance.fromXml("data/eil101.xml"), algorithm, drawCallback));

        new Thread(new Runnable() {
            
            @Override
            public void run() {
                solver.solve();
            }

        }).start();
    }

}
