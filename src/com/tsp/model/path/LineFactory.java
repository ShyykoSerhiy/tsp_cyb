package com.tsp.model.path;

import com.tsp.model.TSPInstance;

import java.util.ArrayList;
import java.util.List;

public class LineFactory extends PathFactory {

    private int startIndex;

    public LineFactory(int numberOfPaths) {
        this(numberOfPaths, 0);
    }

    public LineFactory(int numberOfPaths, int startIndex) {
        super(numberOfPaths);
        this.startIndex = startIndex;
    }

    @Override
    public List<Path> create(TSPInstance tsp) {
        // generate path
        final int count = tsp.count();
        final Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.add((i + startIndex) % count);
        }

        // generate a list of paths
        List paths = new ArrayList(mNumberOfPaths);
        for (int i = 0; i < mNumberOfPaths; i++) {
            paths.add(path);
        }
        return paths;
    }

}
