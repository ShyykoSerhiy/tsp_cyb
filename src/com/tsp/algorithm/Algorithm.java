package com.tsp.algorithm;

import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

public interface Algorithm {

    Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback);

    interface ComputationCallback {

        void onComputation(TSPInstance tsp, Path path);

    }

}
