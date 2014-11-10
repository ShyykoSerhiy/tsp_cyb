package com.tsp.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path {

    private final List<Integer> data;

    private Path() {
        data = new LinkedList<Integer>();
    }

    private Path(Path from) {
        data = new LinkedList<Integer>(from.data);
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

    public double cost(TSPInstance tsp) {
        final int count = count();
        double cost = 0;
        for (int i = 0; i < count - 1; i++) {
            cost += tsp.cost(data.get(i), data.get(i + 1));
        }
        return cost;
    }

    public static Path createRounded(int count, int start) {
        final Path path = createSimple(count, start);
        path.data.add(start);
        return path;
    }

    public static Path createSimple(int count, int start) {
        final Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.data.add((i + start) % count);
        }
        return path;
    }

    public static Path copy(Path from) {
        return new Path(from);
    }

}
