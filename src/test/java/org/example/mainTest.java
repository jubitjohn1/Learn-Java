package org.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.databaseUtil;
import org.junit.Test;
import static org.junit.Assert.*;

public class mainTest {

    @Test
    public void testConnection() throws SQLException,ClassNotFoundException,IOException {

        Connection Result = databaseUtil.getConnection();

        assertNotNull(Result);
    }

    @Test
    public void testCsvMethod() throws SQLException, ClassNotFoundException, IOException {

        CSVDataReader csvDataReader = new CSVDataReader();

        List<String[]> res= csvDataReader.readData("C:\\Users\\jubit.john\\IdeaProjects\\maven_project\\src\\test\\resources\\testDemo.csv");
        List<String[]> expectedData = new ArrayList<>();
        String[] dataRow = {
                "1", "Eldon Base for stackable storage shelf", "Muhammed MacIntyre", "3", "-213.25", "38.94", "35", "Nunavut", "Storage & Organization", "0.8"
        };
        expectedData.add(dataRow);

        assertEquals(expectedData.size(), res.size());
        for (int i = 0; i < expectedData.size(); i++) {
            assertArrayEquals(expectedData.get(i), res.get(i));
        }


        };


};

