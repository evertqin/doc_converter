package main.java.application.main;

import main.java.application.dao.Document;
import main.java.application.dao.XMLDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ruogu on 10/4/14.
 */
public class Main {
    public static void main(String[] Args) throws IOException {
        System.out.println("In Main");

        Path path = Paths.get("/home/ruogu/practice/coding_exercise/ipsoft_perf_counters_xml_schema.xsd");

        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        StringBuffer sb = new StringBuffer();
        String line = null;
        while((line = reader.readLine()) != null) {
            sb.append(line);
            System.out.println(line);
        }
        Document document = new XMLDocument();
        document.setHeader(sb.toString());

    }
}
