package com.tsp.ui.console;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.tsp.model.Path;
import com.tsp.model.TSPInstance;
import com.tsp.ui.Drawer;

public class ConsoleDrawer implements Drawer {

    private static final NumberFormat FORMATTER = new DecimalFormat("#0.00");

    @Override
    public void draw(TSPInstance tsp, Path path) {
        System.out.println();
        System.out.println("Name: " + tsp.getName());
        System.out.println("Description: " + tsp.getDescription());
        final int count = path.count();
        for (int i = 0; i < count - 1; i++) {
            System.out.print("(" + path.edgeAt(i) + ")->");
        }
        System.out.println("(" + path.edgeAt(count - 1) + ")");
        System.out.println("Length: " + FORMATTER.format(path.cost(tsp)));
    }

}
