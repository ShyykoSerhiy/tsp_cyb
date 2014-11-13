package com.tsp.model.path;

import com.tsp.model.TSPInstance;

/**
 * A Factory that creates path that goes through all points one by one in order
 * they appear in problem definition and returns to start point.
 */
public class RoundedFactory extends LineFactory {

    public RoundedFactory() {
        this(0);
    }

    public RoundedFactory(int startIndex) {
        super(startIndex);
    }

    @Override
    public Path create(TSPInstance tsp) {
        final Path superPath = super.create(tsp);
        superPath.add(getStartIndex());
        return superPath;
    }

}
