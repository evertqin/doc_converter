package main.java.application.dao;

import com.sun.xml.internal.ws.util.StringUtils;
import main.java.application.constants.DocumentHeaders;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by ruogu on 10/4/14.
 */
public class CSVDocument extends GenericDocument {
    @Override
    public void parse(String content) {
        String[] lines = content.split("[\\r\\n]+");

        String[] headers = lines[0].split(", ");
        for (int i = 1; i < lines.length; ++i) {
            Map<String, String> dataLine = new HashMap<String, String>();
            String[] tokens = lines[i].split(", ");
            if (tokens.length != headers.length) {
                throw new RuntimeException("Have missing field in the csv");
            }

            for (int j = 0; j < tokens.length; ++j) {
                dataLine.put(headers[j], tokens[j]);
            }
            mRawData.add(dataLine);
        }

    }

    @Override
    public void generate(OutputStream out) {
        process();
        // First generate header
        String header = generateHeader() + '\n';
        try {
            out.write(header.getBytes());
            for (Map<String, String> dataLine : mProcessedData) {
                StringBuilder sb = new StringBuilder();

                sb.append(dataLine.get(DocumentHeaders.SITE_ID)).append(", ")
                        .append(dataLine.get(DocumentHeaders.SITE_NANE)).append(", ")
                        .append(dataLine.get(DocumentHeaders.SITE_LOCATION)).append(", ")
                        .append(dataLine.get(DocumentHeaders.HOST_ID)).append(", ")
                        .append(dataLine.get(DocumentHeaders.HOST_NAME)).append(", ")
                        .append(dataLine.get(DocumentHeaders.IP_ADDRESS)).append(", ")
                        .append(dataLine.get(DocumentHeaders.OPERATIVE_SYSTEM)).append(", ")
                        .append(dataLine.get(DocumentHeaders.LOAD_AVG_1MIN)).append(", ")
                        .append(dataLine.get(DocumentHeaders.LOAD_AVG_5MIN)).append(", ")
                        .append(dataLine.get(DocumentHeaders.LOAD_AVG_15MIN)).append('\n');
                out.write(sb.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateHeader() {
        return DocumentHeaders.SITE_ID + "," + DocumentHeaders.SITE_NANE + "," + DocumentHeaders.SITE_LOCATION + ","
                + DocumentHeaders.HOST_ID + "," + DocumentHeaders.HOST_NAME + "," + DocumentHeaders.IP_ADDRESS + ","
                + DocumentHeaders.OPERATIVE_SYSTEM + "," + DocumentHeaders.LOAD_AVG_1MIN + "," + DocumentHeaders.LOAD_AVG_5MIN + ","
                + DocumentHeaders.LOAD_AVG_15MIN;

    }
}
