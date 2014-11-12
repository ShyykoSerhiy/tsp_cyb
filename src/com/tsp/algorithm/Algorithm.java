package com.tsp.algorithm;

import com.tsp.model.Path;
import com.tsp.model.TSPInstance;

public interface Algorithm {

    Path compute(TSPInstance tsp, ComputationCallback callback);

    interface ComputationCallback {

        void onComputation(TSPInstance tsp, Path path);

    }

}
