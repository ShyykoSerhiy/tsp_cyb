package com.tsp.model.path;

import com.tsp.model.TSPInstance;

public class RoundedFactory implements PathFactory {

    private int startIndex;

    public RoundedFactory() {
        this(0);
    }

    public RoundedFactory(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * Creates path that goes through all points one by one in order they appear in problem
     * definition and returns to start point
     * @param tsp problem definition
     * @return resulting {@link com.tsp.model.path.Path}
     */
    @Override
    public Path create(TSPInstance tsp) {
        final int count = tsp.count();
        final Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.add((i + startIndex) % count);
        }
        path.add(startIndex);
        return path;
    }

}
