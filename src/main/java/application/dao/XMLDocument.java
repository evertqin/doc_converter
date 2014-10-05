package main.java.application.dao;

import main.java.application.constants.DocumentHeaders;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.sound.midi.Soundbank;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by ruogu on 10/4/14.
 */
public class XMLDocument extends GenericDocument {
    public static final Logger log = Logger.getLogger(XMLDocument.class.getName());

    private static Map<Integer, String> levelTags;

    private static Document doc;

    public XMLDocument() {
        super();
        levelTags = new HashMap<Integer, String>();
        levelTags.put(0, "Site");
        levelTags.put(1, "Hosts");
        levelTags.put(2, "Host");
    }

    @Override
    public void generate(OutputStream out) {
        log.info("Start processing filters and sort");
        process();
        generateXML(out);

    }
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc),
                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    private void generateXML(OutputStream out) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            Element rootElement = doc.createElement("Sites");
            Node rootNode = doc.appendChild(rootElement);
            //populate the DOM
            System.out.println(mProcessedData.size());
            for(Map<String, String> entry : mProcessedData) {
                System.out.println(entry);
            }
            generateXML(0, 0, rootNode);
            printDocument(doc, out);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private int generateXML(int level, int indexOnList, Node node) {
        String tag = levelTags.get(level);
        if(indexOnList >= mProcessedData.size()) {
            return indexOnList;
        }

        Map<String, String> curr = mProcessedData.get(indexOnList);
        Map<String, String> next = indexOnList + 1 < mProcessedData.size() ? mProcessedData.get(indexOnList + 1): null;

        while(indexOnList < mProcessedData.size() && level == 0) {
            Element siteElement = doc.createElement(tag);
            siteElement.setAttribute("id", mProcessedData.get(indexOnList).get(DocumentHeaders.SITE_ID));
            siteElement.setAttribute("name", mProcessedData.get(indexOnList).get(DocumentHeaders.SITE_NANE));
            siteElement.setAttribute("location", mProcessedData.get(indexOnList).get(DocumentHeaders.SITE_LOCATION));
            Node siteNode = node.appendChild(siteElement);
            indexOnList = generateXML(level + 1, indexOnList, siteNode);
        }

        if(level == 1) {
            Element hostsElement = doc.createElement(tag);
            Node hostsNode = node.appendChild(hostsElement);
            return generateXML(level + 1, indexOnList, hostsNode);
        }

        if(level == 2) {
            Element hostElement = doc.createElement(tag);
            hostElement.setAttribute("id", curr.get(DocumentHeaders.HOST_ID));
            Node hostNode = node.appendChild(hostElement);
            // poplulate
            Element hostNameElement = doc.createElement("Host_name");
            hostNameElement.setTextContent(curr.get(DocumentHeaders.HOST_NAME));
            hostNode.appendChild(hostNameElement);
            Element ipAddressElement = doc.createElement("IP_address");
            ipAddressElement.setTextContent(curr.get(DocumentHeaders.IP_ADDRESS));
            hostNode.appendChild(ipAddressElement);
            Element loadAvg1MinElement = doc.createElement("Load_avg_1min");
            loadAvg1MinElement.setTextContent(curr.get(DocumentHeaders.LOAD_AVG_1MIN));
            hostNode.appendChild(loadAvg1MinElement);
            Element loadAvg5MinElement = doc.createElement("Load_avg_5min");
            loadAvg1MinElement.setTextContent(curr.get(DocumentHeaders.LOAD_AVG_5MIN));
            hostNode.appendChild(loadAvg5MinElement);
            Element loadAvg15MinElement = doc.createElement("Load_avg_15min");
            loadAvg1MinElement.setTextContent(curr.get(DocumentHeaders.LOAD_AVG_15MIN));
            hostNode.appendChild(loadAvg15MinElement);
            if(next == null || curr.get(DocumentHeaders.SITE_ID) != next.get(DocumentHeaders.SITE_ID)) {
                return indexOnList + 1;
            } else {
                return generateXML(level, indexOnList + 1, node);
            }
        }
        return indexOnList;
    }



    @Override
    public void parse(String content) {
        // we use XML to represent all the dates
        log.info("Start Parsing XML document");
        parseXml(content);

    }

    private void parseXml(String content) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            DocumentHandler docHandler = new DocumentHandler();
            saxParser.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))),
                    docHandler);
            mRawData = docHandler.getParsedXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
