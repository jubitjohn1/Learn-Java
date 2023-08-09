package org.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import utils.databaseUtil;
import org.junit.Test;
import static org.junit.Assert.*;


import org.mockito.MockitoAnnotations;



import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class mainTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConnection() throws SQLException,ClassNotFoundException {

        Connection Result = databaseUtil.getConnection();

        assertNotNull(Result);
    }

    @Test
    public void testCsvMethod() throws  IOException {

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

    @Test
    public void testJsonMethod() throws IOException{

        JSONDataReader jsonDataReader = new JSONDataReader();

        List<String[]> res= jsonDataReader.readData("src/test/resources/JsonTest.json");
        List<String[]> expectedData = new ArrayList<>();
        String[] dataRow = {
                "1", "Eldon Base for stackable storage shelf", "Muhammed MacIntyre", "3", "-213.25", "38.94", "35", "Nunavut", "Storage & Organization", "0.8"
        };
        expectedData.add(dataRow);

        assertEquals(expectedData.size(), res.size());
        for (int i = 0; i < expectedData.size(); i++) {
            assertArrayEquals(expectedData.get(i), res.get(i));
        }

    }


    @Test
    public void testInsertData() throws SQLException {
        List<String[]> testData = createTestData();

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        // Create an instance of your Main class or relevant class
        dbInsertion dbinsert = new dbInsertion();

        // Call the method that inserts data
        dbInsertion.insertData(mockConnection, testData);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(testData.size())).addBatch();
        verify(mockStatement).executeBatch();
        verify(mockStatement).close();
    }

    private List<String[]> createTestData() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[]{"1", "Product 1", "Vendor 1", "10", "100.0", "1.0", "10.0", "Region 1", "Category 1", "0.1"});
        testData.add(new String[]{"2", "Product 2", "Vendor 2", "20", "200.0", "2.0", "20.0", "Region 2", "Category 2", "0.2"});
        return testData;
    }

};

