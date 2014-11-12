package com.tsp.ui;

import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

public interface Drawer {

    void draw(TSPInstance tsp, Path path);

}
