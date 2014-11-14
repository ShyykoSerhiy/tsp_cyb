package com.tsp.model.ga;

import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;
import com.tsp.model.path.RandomFactory;

/**
 * Heavily inspired by http://www.theprojectspot.com/tutorial-post/applying-a-genetic-algorithm-to-the-travelling-salesman-problem/5
 */
public class Population {
    private Path[] paths;

    public Population(int populationSize, Path initial, TSPInstance tsp) {
        paths = new Path[populationSize];
        if (populationSize > 0) {
            saveTour(0, initial); // ensuring that we have "starting" population
            for (int i = 1; i < populationSize(); i++) {
                Path newPath = new RandomFactory().create(tsp);
                saveTour(i, newPath);
            }
        }
    }

    public Population(int populationSize) {
        paths = new Path[populationSize];
    }

    /**
     * Saves a tour
     */
    public void saveTour(int index, Path path) {
        paths[index] = path;
    }

    /**
     * Gets a tour from population
     */

    public Path getTour(int index) {
        return paths[index];
    }

    /**
     * Gets the best tour in the population
     */

    public Path getFittest(TSPInstance tspInstance) {
        Path fittest = paths[0];
        double fittestFit = 1/fittest.cost(tspInstance);
        for (int i = 1; i < populationSize(); i++) {
            double anotherFit = 1/getTour(i).cost(tspInstance);
            if (anotherFit > fittestFit) {
                fittestFit = anotherFit;
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    /**
     * Gets population size
     */
    public int populationSize() {
        return paths.length;
    }
}
