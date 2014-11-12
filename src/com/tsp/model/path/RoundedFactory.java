package com.tsp.model.path;

import com.tsp.model.TSPInstance;

import java.util.ArrayList;
import java.util.List;

public class RoundedFactory extends PathFactory {

    private int startIndex;

    public RoundedFactory(int numberOfPaths) {
        this(numberOfPaths, 0);
    }

    public RoundedFactory(int numberOfPaths, int startIndex) {
        super(numberOfPaths);
        this.startIndex = startIndex;
    }

    /**
     * Creates path that goes through all points one by one in order they appear in problem
     * definition and returns to start point
     * @param tsp problem definition
     * @return resulting list of {@link com.tsp.model.path.Path}
     */
    @Override
    public List<Path> create(TSPInstance tsp) {
        // generate path
        final int count = tsp.count();
        final Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.add((i + startIndex) % count);
        }
        path.add(startIndex);

        // generate a list of paths
        List paths = new ArrayList(mNumberOfPaths);
        for (int i = 0; i < mNumberOfPaths; i++) {
            paths.add(path);
        }
        return paths;
    }

}
