package main.java.application.main;

import main.java.application.dao.Document;
import main.java.application.dao.DocumentIO;
import main.java.application.dao.XMLDocument;


import java.io.IOException;


/**
 * Created by ruogu on 10/4/14.
 */
public class Main {
    public static void main(String[] Args) throws IOException {
        System.out.println("In Main");

        String xmlContent = DocumentIO.getContent("/home/ruogu/practice/coding_exercise/ipsoft_perf_counters_xml_sample_data.xml");

        Document document = new XMLDocument();
        //((XMLDocument)document).setSchemaFile("/home/ruogu/practice/coding_exercise/ipsoft_perf_counters_xml_schema.xsd");
        document.setContent(xmlContent);
    }
}
