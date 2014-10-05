package main.java.application.main;

import main.java.application.constants.DocumentHeaders;
import main.java.application.constants.SortType;
import main.java.application.dao.*;


import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by ruogu on 10/4/14.
 */
public class Main {
    public static void main(String[] Args) throws IOException {
        System.out.println("In Main");
        String xmlContent = DocumentIO.getContent("src/resources/ipsoft_perf_counters_xml_sample_data.xml");

        //Test case #1: Generate CSV output from XML input, output must be sorted by by operating system ASC, then by host id DESC.
        BaseDocument xmlDocument = new XMLDocument();
        xmlDocument.parse(xmlContent);
        xmlDocument.setOrderRule(DocumentHeaders.OPERATIVE_SYSTEM, SortType.ASC);
        xmlDocument.setOrderRule(DocumentHeaders.HOST_ID, SortType.DESC);
        xmlDocument.generate(System.out);
        BaseDocument testCase1 = new CSVDocument((GenericDocument)xmlDocument);
        testCase1.generate(System.out);


        // Test case #2: Generate CSV output from XML input, filtering by site_name=’ NY-01’ and sort by host name ASC.
        ((GenericDocument) xmlDocument).clearRules();
        xmlDocument.setFilterRule(DocumentHeaders.SITE_NANE, "NY-01");
        xmlDocument.setOrderRule(DocumentHeaders.LOAD_AVG_1MIN, SortType.ASC);
        BaseDocument testCase2 = new CSVDocument((GenericDocument)xmlDocument);
        testCase2.generate(System.out);

        // Test case #3: Generate XML output from XML input, sorted by load average (1 min) ASC, then host id DESC.
        ((GenericDocument) xmlDocument).clearRules();
        xmlDocument.setOrderRule(DocumentHeaders.LOAD_AVG_1MIN, SortType.ASC);
        xmlDocument.setOrderRule(DocumentHeaders.HOST_ID, SortType.DESC);
        xmlDocument.generate(System.out);
    }
}
