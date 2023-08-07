package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataReader implements DataReader {
    @Override
    public List<String[]> readData(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader lineReader = new BufferedReader(new FileReader(filePath))) {
            String lineText;
            while ((lineText = lineReader.readLine()) != null) {
                String[] values = lineText.split(",");
                data.add(values);
            }
        }
        return data;
    }
}
