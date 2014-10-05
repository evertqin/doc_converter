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
        String xmlContent = DocumentIO.getContent("/home/ruogu/practice/coding_exercise/ipsoft_perf_counters_xml_sample_data.xml");

        BaseDocument xmlDocument = new XMLDocument();
        xmlDocument.parse(xmlContent);
        xmlDocument.setFilterRule(DocumentHeaders.SITE_ID, "101");
        xmlDocument.setOrderRule(DocumentHeaders.HOST_ID, SortType.DESC);

        //xmlDocument.generate();

        String csvContent = DocumentIO.getContent("/home/ruogu/practice/coding_exercise/ipsoft_perf_counters_csv_sample_data.txt");
        BaseDocument csvDocument = new CSVDocument();
        csvDocument.parse(csvContent);
        csvDocument.setFilterRule(DocumentHeaders.SITE_ID, "101");
        csvDocument.setOrderRule(DocumentHeaders.HOST_ID, SortType.DESC);
        csvDocument.generate(System.out);


    }
}
