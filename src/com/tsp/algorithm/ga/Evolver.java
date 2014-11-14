package com.tsp.algorithm.ga;

import com.tsp.model.TSPInstance;
import com.tsp.model.ga.Population;
import com.tsp.model.path.Path;

import java.util.ArrayList;
import java.util.List;

public class Evolver {

    /* GA parameters */
    private final double mutationRate;
    private final int tournamentSize;
    private final boolean elitism;

    public Evolver(double mutationRate, int tournamentSize, boolean elitism) {
        this.mutationRate = mutationRate;
        this.tournamentSize = tournamentSize;
        this.elitism = elitism;
    }

    // Evolves a population over one generation
    public Population evolvePopulation(Population pop, TSPInstance tspInstance) {
        int citiesAmount = tspInstance.count();
        Population newPopulation = new Population(pop.populationSize());

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest(tspInstance));
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            Path parent1 = tournamentSelection(pop, tspInstance);
            Path parent2 = tournamentSelection(pop, tspInstance);
            // Crossover parents
            Path child = crossover(parent1, parent2, citiesAmount);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i), mutationRate);
        }

        return newPopulation;
    }

    // Applies crossover to a set of parents and creates offspring
    public Path crossover(Path parent1, Path parent2, int citiesAmount) {
        // Create new child path
        List<Integer> cities = new ArrayList<>();
        for (int k = 0; k < citiesAmount; k++){
            cities.add(-1);
        }
        Path child = new Path(cities);

        // Get start and end sub path positions for parent1's path
        int startPos = (int) (Math.random() * parent1.count());
        int endPos = (int) (Math.random() * parent1.count());

        // Loop and add the sub path from parent1 to our child
        for (int i = 0; i < citiesAmount; i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                cities.set(i, parent1.edgeAt(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    cities.set(i, parent1.edgeAt(i));
                }
            }
        }

        // Loop through parent2's city path
        for (int i = 0; i < citiesAmount; i++) {
            // If child doesn't have the city add it
            if (!cities.contains(parent2.edgeAt(i))) {
                // Loop to find a spare position in the child's path
                for (int ii = 0; ii < citiesAmount; ii++) {
                    // Spare position found, add city
                    if (cities.get(ii) == -1) {
                        cities.set(ii, parent2.edgeAt(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    // Mutate a path using swap mutation
    private static void mutate(Path path, double mutationRate) {
        // Loop through path cities
        for(int tourPos1=0; tourPos1 < path.count(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the path
                int tourPos2 = (int) (path.count() * Math.random());
                // Swap them around
                path.swap(tourPos1, tourPos2);
            }
        }
    }

    // Selects candidate path for crossover
    private Path tournamentSelection(Population pop, TSPInstance tspInstance) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize);
        // For each place in the tournament get a random candidate path and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest path
        Path fittest = tournament.getFittest(tspInstance);
        return fittest;
    }
}
