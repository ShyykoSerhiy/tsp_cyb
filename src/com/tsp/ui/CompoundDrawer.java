package com.tsp.ui;

import com.tsp.model.Path;
import com.tsp.model.TSPInstance;

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
