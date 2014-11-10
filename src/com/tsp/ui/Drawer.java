package com.tsp.ui;

import com.tsp.model.Path;
import com.tsp.model.TSPInstance;

public interface Drawer {

    void draw(TSPInstance tsp, Path path);

}
