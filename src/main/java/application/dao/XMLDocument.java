package main.java.application.dao;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by ruogu on 10/4/14.
 */
public class XMLDocument extends GenericDocument {

    @Override
    public String generate() {
        // first filter the data
         return null;
    }


    @Override
    public void setContent(String content) {
        // we use XML to represent all the datas
        parserXml(content);
        this.mContent = content;
    }

    private void parserXml(String content) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))),
                    new DocumentHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
