package com.tsp;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.tsp.algorithm.simple.SimpleAlgorithm;
import com.tsp.app.Application;
import com.tsp.model.TSPInstance;
import com.tsp.ui.console.ConsoleDrawer;

public class Main {

    public static void main(String[] argc) throws FileNotFoundException,
            SAXException, IOException, ParserConfigurationException {
        new Application(TSPInstance.fromXml("data/burma14.xml"),
                new SimpleAlgorithm(), new ConsoleDrawer()).start();
    }

}
