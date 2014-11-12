package com.tsp.model.path;

import com.tsp.model.TSPInstance;

public interface PathFactory {

    Path create(TSPInstance tsp);

}
