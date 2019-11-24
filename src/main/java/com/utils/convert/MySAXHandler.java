package com.utils.convert;/*
package com.utils.convertUtils;

import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

*/
/**
 * @author YangWenjun
 * @date 2019/7/10 15:25
 * @project hook
 * @title: MySAXHandler
 * @description:
 *//*

public class MySAXHandler extends DefaultHandler {
    boolean hasAttribute = false;
    Attributes attributes = null;

    public void startDocument() throws SAXException {
        // System.out.println("文档开始打印了");
    }

    public void endDocument() throws SAXException {
        // System.out.println("文档打印结束了");
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("users")) {
            return;
        }
        if (qName.equals("user")) {
            return;
        }
        if (attributes.getLength() > 0) {
            this.attributes = attributes;
            this.hasAttribute = true;
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (hasAttribute && (attributes != null)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(attributes.getQName(0) + ":"
                        + attributes.getValue(0));
            }
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        System.out.print(new String(ch, start, length));
    }
}
*/
