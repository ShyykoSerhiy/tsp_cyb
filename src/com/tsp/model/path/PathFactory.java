package com.tsp.model.path;

import com.tsp.model.TSPInstance;

import java.util.List;

public abstract class PathFactory {

    public int mNumberOfPaths;

    /**
     * Default constructor with set number of paths
     * @param numberOfPaths number of paths to generate
     */
    public PathFactory(int numberOfPaths) {
        mNumberOfPaths =  numberOfPaths;
    }

    /**
     * Generate a number of paths
     * @param tsp graph definition
     * @return List of {@link com.tsp.model.path.Path}
     */
    public abstract List<Path> create(TSPInstance tsp);

}
