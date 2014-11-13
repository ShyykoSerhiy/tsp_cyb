package com.tsp.generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tsp.Main;
import com.tsp.utils.XmlUtils;

/**
 * Random path permutation generator
 */
public class InitialPathGenerator {

    // required by definition
    private static final int PERMUTATION_NUMBER = 10;

    public static void main(String[] argc) throws ParserConfigurationException, SAXException,
            IOException {
        // iterate through all xmls and generate random permutations for them
        for (String xmlName : Main.XML_NAMES) {
            // get vertex count for given graph
            int vertexCount = getVertexCountInXml(xmlName);
            // generate and write permutations
            PrintWriter writer = new PrintWriter(XmlUtils.permutationForName(xmlName), "UTF-8");
            for (int i = 0; i < PERMUTATION_NUMBER; i++) {
                writer.println(generatePermutationForVertexNumber(vertexCount));
            }
            writer.close();
        }
    }

    private static String generatePermutationForVertexNumber(int count) {
        // generate list of consequent numbers
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        // shuffle it
        Collections.shuffle(list);

        // build a string from shuffled list
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(String.valueOf(list.get(i)));
            builder.append(" ");
        }

        return builder.toString();
    }

    private static int getVertexCountInXml(String xmlName) throws IOException, SAXException,
            ParserConfigurationException {
        InputStream xmlStream = null;
        try {
            xmlStream = new FileInputStream(XmlUtils.xmlForName(xmlName));
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(xmlStream);
            final Element rootElement = document.getDocumentElement();

            final Element graphElement = (Element) rootElement.getElementsByTagName("graph")
                    .item(0);
            final NodeList vertexList = graphElement.getElementsByTagName("vertex");

            return vertexList.getLength();
        } finally {
            if (xmlStream != null) {
                xmlStream.close();
            }
        }
    }

}
