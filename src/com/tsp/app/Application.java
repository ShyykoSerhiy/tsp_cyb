package com.tsp.app;

import com.tsp.algorithm.Algorithm;
import com.tsp.algorithm.Algorithm.ComputationCallback;
import com.tsp.model.Path;
import com.tsp.model.TSPInstance;
import com.tsp.ui.Drawer;
import com.tsp.utils.TimeUtils;

public class Application {

    private final TSPInstance tsp;
    private final Algorithm algorithm;
    private final Drawer drawer;

    private final Runnable algorithmRunnable = new Runnable() {

        @Override
        public void run() {
            algorithm.compute(tsp, drawCallback);
        }

    };

    private final Runnable asyncRunnable = new Runnable() {

        @Override
        public void run() {
            System.out.println("Algorithm time is "
                    + TimeUtils.methodTime(algorithmRunnable) + "ms");
        }

    };

    private final ComputationCallback drawCallback = new ComputationCallback() {

        @Override
        public void onComputation(TSPInstance tsp, Path path) {
            // TODO run on main thread
            drawer.draw(tsp, path);
        }

    };

    public Application(TSPInstance tsp, Algorithm algorithm, Drawer drawer) {
        this.tsp = tsp;
        this.algorithm = algorithm;
        this.drawer = drawer;
    }

    public void start() {
        new Thread(asyncRunnable).start();
    }

}
