package main.java.application.dao;

import main.java.application.constants.DocumentHeaders;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruogu on 10/4/14.
 */
public class DocumentHandler extends DefaultHandler {
    private String host_name_tag = "close";
    private String ip_address_tag = "close";
    private String os_tag = "close";
    private String load_avg_1min_tag = "close";
    private String load_avg_5min_tag = "close";
    private String load_avg_15min_tag = "close";

    private Map<String, String> mCurrentParsingInfo;

    private List<Map<String, String>> mParsedXML;

    public DocumentHandler() {
        mCurrentParsingInfo = new HashMap<String, String>();
        mParsedXML = new ArrayList<Map<String, String>>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(host_name_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.HOST_NAME, new String(ch, start, length));
            host_name_tag = "close";
            return;
        }

        if(ip_address_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.IP_ADDRESS, new String(ch, start, length));
            ip_address_tag = "close";
            return;
        }

        if(os_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.OPERATIVE_SYSTEM, new String(ch, start, length));
            os_tag = "close";
            return;
        }

        if(load_avg_1min_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.LOAD_AVG_1MIN, new String(ch, start, length));
            load_avg_1min_tag = "close";
            return;
        }

        if(load_avg_5min_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.LOAD_AVG_5MIN, new String(ch, start, length));
            load_avg_5min_tag = "close";
            return;
        }

        if(load_avg_15min_tag == "open") {
            mCurrentParsingInfo.put(DocumentHeaders.LOAD_AVG_15MIN, new String(ch, start, length));
            load_avg_15min_tag = "close";

            return;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("host"))
            mParsedXML.add(new HashMap<String, String>(mCurrentParsingInfo));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if(qName.equalsIgnoreCase("site")) {
            mCurrentParsingInfo.put(DocumentHeaders.SITE_ID, attributes.getValue("id"));
            mCurrentParsingInfo.put(DocumentHeaders.SITE_NANE, attributes.getValue("name"));
            mCurrentParsingInfo.put(DocumentHeaders.SITE_LOCATION, attributes.getValue("location"));
        }

        if(qName.equalsIgnoreCase("host")) {
            mCurrentParsingInfo.put(DocumentHeaders.HOST_ID, attributes.getValue("id"));
        }

        if(qName.equalsIgnoreCase("host_name")) {
            host_name_tag = "open";

        }

        if(qName.equalsIgnoreCase("ip_address")) {
            ip_address_tag = "open";

        }

        if(qName.equalsIgnoreCase("os")) {
            os_tag = "open";
        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_1MIN)) {
            load_avg_1min_tag = "open";
        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_5MIN)) {
            load_avg_5min_tag = "open";

        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_15MIN)) {
            load_avg_15min_tag = "open";
        }
    }

    public List<Map<String, String>> getParsedXML() {
        return mParsedXML;
    }
}
