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
    private static final int SITE_META_COUNT = 3;
    private static final int TOTAL_FIELDS = 10;

    private String host_name_tag = "close";
    private String ip_address_tag = "close";
    private String os_tag = "close";
    private String load_avg_1min_tag = "close";
    private String load_avg_5min_tag = "close";
    private String load_avg_15min_tag = "close";
    private int fieldCounter = 0;

    private Map<String, String> mCurrentParsingInfo;

    private List<Map<String, String>> mParsedXML;

    public DocumentHandler() {
        mCurrentParsingInfo = new HashMap<String, String>();
        mParsedXML = new ArrayList<Map<String, String>>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //super.characters(ch, start, length);
        //System.out.println(ch);

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
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if(qName.equalsIgnoreCase("site")) {

            mCurrentParsingInfo.put(DocumentHeaders.SITE_ID, attributes.getValue("id"));
            mCurrentParsingInfo.put(DocumentHeaders.SITE_NANE, attributes.getValue("name"));
            mCurrentParsingInfo.put(DocumentHeaders.SITE_LOCATION, attributes.getValue("location"));
            fieldCounter = SITE_META_COUNT;
        }

        if(qName.equalsIgnoreCase("host")) {
            mCurrentParsingInfo.put(DocumentHeaders.HOST_ID, attributes.getValue("id"));
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase("host_name")) {
            host_name_tag = "open";
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase("ip_address")) {
            ip_address_tag = "open";
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase("os")) {
            os_tag = "open";
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_1MIN)) {
            load_avg_1min_tag = "open";
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_5MIN)) {
            load_avg_5min_tag = "open";
            fieldCounter++;
        }

        if(qName.equalsIgnoreCase(DocumentHeaders.LOAD_AVG_15MIN)) {
            load_avg_15min_tag = "open";
            fieldCounter++;
        }

        if(fieldCounter == TOTAL_FIELDS) {
            mParsedXML.add(new HashMap<String, String>(mCurrentParsingInfo));
            fieldCounter = SITE_META_COUNT;
        }
    }

    public List<Map<String, String>> getParsedXML() {
        return mParsedXML;
    }
}
