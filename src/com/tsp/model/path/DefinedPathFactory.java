package com.tsp.model.path;

import java.util.List;

import com.tsp.model.TSPInstance;

public class DefinedPathFactory implements PathFactory {

    private final Path path;

    public DefinedPathFactory(List<Integer> edges) {
        this(new Path(edges));
    }

    public DefinedPathFactory(Path path) {
        this.path = path;
    }

    @Override
    public Path create(TSPInstance tsp) {
        return path.clone();
    }

}
