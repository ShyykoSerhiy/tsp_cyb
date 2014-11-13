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

    /* package */Path(List<Integer> list) {
        data = new LinkedList<Integer>(list);
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final int count = count();
        for (int i = 0; i < count - 1; i++) {
            builder.append("(" + edgeAt(i) + ")->");
        }
        builder.append("(" + edgeAt(count - 1) + ")");
        return builder.toString();
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
