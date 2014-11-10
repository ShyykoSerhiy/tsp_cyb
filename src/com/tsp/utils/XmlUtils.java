package com.tsp.utils;

import org.w3c.dom.Element;

public final class XmlUtils {

    private XmlUtils() {
    }

    public static String getTextContent(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

}
