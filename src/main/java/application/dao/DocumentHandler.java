package main.java.application.dao;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Hashtable;

/**
 * Created by ruogu on 10/4/14.
 */
public class DocumentHandler extends DefaultHandler {
    private Hashtable tags;

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startDocument() throws SAXException {
        tags = new Hashtable();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName);
    }
}
