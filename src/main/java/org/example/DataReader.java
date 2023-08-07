package org.example;
import java.io.IOException;
import java.util.List;

public interface DataReader {
    List<String[]> readData(String filePath) throws IOException;
}