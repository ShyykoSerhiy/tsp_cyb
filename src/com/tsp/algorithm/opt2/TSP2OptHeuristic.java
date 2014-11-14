/* Copyright 2012 David Hadka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.tsp.algorithm.opt2;

import com.tsp.algorithm.Algorithm;
import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the 2-opt heuristic for the traveling salesman problem.
 * The 2-opt heuristic searches for any two edges in a tour that can be
 * rearranged to produce a shorter tour.  For example, a tour with any edges
 * that intersect can be shortened by removing the intersection.
 */
public class TSP2OptHeuristic implements Algorithm {
    private int maxFailures;
	/**
	 * Constructs a new 2-opt heuristic for the specified traveling salesman
	 * problem instance.
	 * 
	 */
	public TSP2OptHeuristic(int maxFailures) {
        super();
        this.maxFailures = maxFailures;
	}

	/**
	 * Applies the 2-opt heuristic to the specified tour.
	 * 
	 * @param tour the tour that is modified by the 2-opt heuristic
	 */
	public Path apply(Path tour,TSPInstance instance) {
		boolean modified = true;

        int failures = 0;
		while (modified) {
            Path newTour = tour.clone();
			modified = false;
			for (int i = 0; i < newTour.count()-1; i++) {
				for (int j = i+2; j < newTour.count()-1; j++) {
					double d1 = instance.cost(newTour.edgeAt(i), newTour.edgeAt(i+1)) + instance.cost(newTour.edgeAt(j), newTour.edgeAt(j+1));
					double d2 =  instance.cost(newTour.edgeAt(i), newTour.edgeAt(j)) + instance.cost(newTour.edgeAt(i + 1), newTour.edgeAt(j + 1));

					// if distance can be shortened, adjust the tour
					if (d2 < d1) {
                        newTour.reverse(i+1, j);
						modified = true;
					}
				}
			}
            if (newTour.cost(instance) < tour.cost(instance)){
                tour = newTour;
            } else {
                failures++;
            }
            if (failures == maxFailures){
                break;
            }
		}
        return tour;
	}

    @Override
    public Path compute(TSPInstance tsp, Path beginPath, ComputationCallback callback) {
        return apply(beginPath, tsp);
    }
}
