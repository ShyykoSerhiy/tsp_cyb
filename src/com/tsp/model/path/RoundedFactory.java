package com.tsp.model.path;

import com.tsp.model.TSPInstance;

/**
 * A Factory that creates path that goes through all points one by one in order
 * they appear in problem definition and returns to start point.
 */
public class RoundedFactory implements PathFactory {

    private final PathFactory wrapped;

    public RoundedFactory(PathFactory wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Path create(TSPInstance tsp) {
        final Path wrappedResult = wrapped.create(tsp);
        if (wrappedResult.count() > 1) {
            wrappedResult.add(wrappedResult.edgeAt(0));
        }
        return wrappedResult;
    }

}
