package com.tsp.utils;

import org.w3c.dom.Element;

public final class XmlUtils {

    private XmlUtils() {
    }

    public static String getTextContent(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    public static String xmlForName(String name) {
        return "data/" + name + ".xml";
    }

    public static String permutationForName(String name) {
        return "data/" + name + "_permutations.txt";
    }

}
