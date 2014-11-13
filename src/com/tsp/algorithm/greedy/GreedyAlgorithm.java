package com.tsp.algorithm.greedy;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

import java.util.HashSet;
import java.util.Set;

/**
 * http://habrahabr.ru/post/151954/
 */
public class GreedyAlgorithm implements Algorithm {
    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        Set<Integer> addedVertexes = new HashSet<Integer>(tsp.count());

        // starting from any vertex
        int startVertex = 0;

        Path path = new Path();
        // start and finish vertexes are the same
        path.add(startVertex);
        path.add(startVertex);
        // add to visited
        addedVertexes.add(startVertex);

        while(addedVertexes.size() < tsp.count()) {
            double maxDist = -1;
            int furthestVertex = -1;
            // first find the furthest vertex for all vertexes added in Path
            for (int i = 0; i < path.count() - 1; i++) {
                double tempMinDist = Double.MAX_VALUE;
                int tempClosestVertex = -1;
                for (int j = 0; j < tsp.count(); j++) {
                    if (j != path.edgeAt(i) && !addedVertexes.contains(j)) {
                        final double cost = tsp.cost(path.edgeAt(i), j);
                        if (cost < tempMinDist) {
                            tempMinDist = cost;
                            tempClosestVertex = j;
                        }
                    }
                }

                if (tempMinDist > maxDist || maxDist == -1) {
                    maxDist = tempMinDist;
                    furthestVertex = tempClosestVertex;
                }
            }

            // find a place to put found vertex where it will increase the path length least
            Double minDist = Double.MAX_VALUE;
            int bestPlace = -1;
            for (int i = 0; i < path.count() - 1; i++) {
                final double tempDist = tsp.cost(path.edgeAt(i), furthestVertex)
                        + tsp.cost(furthestVertex, path.edgeAt(i+1));
                if (tempDist < minDist) {
                    minDist = tempDist;
                    bestPlace = i + 1;
                }
            }
            // place vertex in path
            path.insert(bestPlace, furthestVertex);
            // mark furthest vertex as visited
            addedVertexes.add(furthestVertex);
        }
        return path;
    }
}
