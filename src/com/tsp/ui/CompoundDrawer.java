package com.tsp.ui;

import com.tsp.model.TSPInstance;
import com.tsp.model.path.Path;

public class CompoundDrawer implements Drawer {

    private final Drawer[] drawers;

    public CompoundDrawer(Drawer... drawers) {
        this.drawers = drawers;
    }

    @Override
    public void draw(TSPInstance tsp, Path path) {
        for (Drawer drawer : drawers) {
            drawer.draw(tsp, path);
        }
    }

}
