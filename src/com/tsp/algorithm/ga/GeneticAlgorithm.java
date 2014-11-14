package com.tsp.algorithm.ga;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.ga.Population;
import com.tsp.model.path.Path;

public class GeneticAlgorithm implements Algorithm {
    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        Population population = new Population(300, beginPath, tsp);
        Evolver evolver = new Evolver(0, 10, true);
        final int EVOLVS = 300;//todo params;
        for (int i = 0; i < EVOLVS; i++){
            population = evolver.evolvePopulation(population, tsp);
        }
        return population.getFittest(tsp);
    }
}
