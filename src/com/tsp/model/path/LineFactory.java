package com.tsp.model.path;

import com.tsp.model.TSPInstance;

public class LineFactory implements PathFactory {

    private final int startIndex;

    public LineFactory() {
        this(0);
    }

    public LineFactory(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Path create(TSPInstance tsp) {
        final int count = tsp.count();
        final Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.add((i + startIndex) % count);
        }
        return path;
    }

    protected int getStartIndex() {
        return startIndex;
    }

}
