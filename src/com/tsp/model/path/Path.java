package com.tsp.model.path;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tsp.model.TSPInstance;

public class Path {

    private final List<Integer> data;

    public Path() {
        data = new LinkedList<Integer>();
    }

    public Path(Path from) {
        data = new LinkedList<Integer>(from.data);
    }

    public Path(List<Integer> list) {
        data = new LinkedList<Integer>(list);
    }

    public void add(int edge) {
        data.add(edge);
    }

    /**
     * Insert edge into path
     * @param place position into which element will be inserted
     * @param edge vertex number
     */
    public void insert(int place, int edge) {
        data.add(place, edge);
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

    /**
     * Reverses the order in which the nodes are visited between the two
     * specified indices.  The first index defines the start of the
     * reverse operation; the second index defines the stopping position.
     * Tours are cyclic, so the reverse operation, when the second index is less
     * than the first, gets wrapped around the end of the array.  The indices
     * are inclusive.
     *
     * @param i the first index, or starting index
     * @param j the second index, or stopping index
     */
    public void reverse(int i, int j) {
        while (j < i) {
            j += data.size();
        }

        for (int k = 0; k < (j - i + 1) / 2; k++) {
            int temp = data.get(i+k);
            data.set(i+k, data.get(j-k));
            data.set(j-k, temp);
        }
    }
}
