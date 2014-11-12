package com.tsp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tsp.utils.ArrayUtils;
import com.tsp.utils.XmlUtils;

public class TSPInstance {

    private final double[][] graph;
    private String name;
    private String description;
    private double optimal;

    private TSPInstance(double[][] graph, String name, String description,
            double optimal) {
        this.graph = graph;
        this.name = name;
        this.description = description;
        this.optimal = optimal;
    }

    public int count() {
        return graph.length;
    }

    public double cost(int i, int j) {
        return graph[i][j];
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getOptimal() {
        return optimal;
    }

    public static TSPInstance fromXml(String xml) throws SAXException,
            IOException, ParserConfigurationException {
        InputStream xmlStream = null;
        try {
            xmlStream = new FileInputStream(xml);
            final Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(xmlStream);
            final Element rootElement = document.getDocumentElement();

            final Element graphElement = (Element) rootElement
                    .getElementsByTagName("graph").item(0);
            final NodeList vertexList = graphElement
                    .getElementsByTagName("vertex");
            final int count = vertexList.getLength();
            final double graph[][] = ArrayUtils.createEmpty(count);
            for (int i = 0; i < count; i++) {
                final NodeList edgeList = ((Element) vertexList.item(i))
                        .getElementsByTagName("edge");
                final int edgesCount = edgeList.getLength();
                for (int j = 0; j < edgesCount; j++) {
                    final Element edge = (Element) edgeList.item(j);
                    graph[i][Integer.parseInt(edge.getTextContent())] = Double
                            .parseDouble(edge.getAttribute("cost"));
                }
            }

            final String name = XmlUtils.getTextContent(rootElement, "name");
            final String description = XmlUtils.getTextContent(rootElement,
                    "description");
            return new TSPInstance(graph, name, description,
                    getOptimalSolution(name));
        } finally {
            if (xmlStream != null) {
                xmlStream.close();
            }
        }
    }

    private static double getOptimalSolution(String name)
            throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File("data/optimal.txt"));
        while (scanner.hasNextLine()) {
            final String[] line = scanner.nextLine().split(": ");
            if (name.equals(line[0])) {
                scanner.close();
                return Integer.parseInt(line[1]);
            }
        }
        scanner.close();
        throw new IllegalArgumentException("No optimal solution found for "
                + name);
    }

}
