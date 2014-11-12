package com.tsp.model.path;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tsp.model.TSPInstance;

public class Path {

    private final List<Integer> data;

    /* package */Path() {
        data = new LinkedList<Integer>();
    }

    /* package */Path(Path from) {
        data = new LinkedList<Integer>(from.data);
    }

    /* package */void add(int edge) {
        data.add(edge);
    }

    public void swap(int firstIndex, int secondIndex) {
        Collections.swap(data, firstIndex, secondIndex);
    }

    public int edgeAt(int index) {
        return data.get(index);
    }

    public int count() {
        return data.size();
    }

    @Override
    public Path clone() {
        return new Path(this);
    }

    public double cost(TSPInstance tsp) {
        final int count = count();
        double cost = 0;
        for (int i = 0; i < count - 1; i++) {
            cost += tsp.cost(data.get(i), data.get(i + 1));
        }
        return cost;
    }
}
