package com.tsp.model.path;

import com.tsp.model.TSPInstance;

public interface PathFactory {

    /**
     * Constructs a single Path for the given problem.
     */
    public abstract Path create(TSPInstance tsp);

}
