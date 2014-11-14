package com.tsp.model.path;

import com.tsp.model.TSPInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomFactory implements PathFactory {

    private final int startIndex;

    public RandomFactory() {
        this(0);
    }

    public RandomFactory(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Path create(TSPInstance tsp) {
        final int count = tsp.count();
        final List<Integer> data = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            data.add((i + startIndex) % count);
        }
        Collections.shuffle(data);
        return new Path(data);
    }

    protected int getStartIndex() {
        return startIndex;
    }

}
