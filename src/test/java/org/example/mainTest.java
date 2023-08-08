package org.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import utils.databaseUtil;
import org.example.CSVDataReader;

import org.junit.Test;
import static org.junit.Assert.*;

public class mainTest {

    @Test
    public void testMainMethod() throws SQLException, ClassNotFoundException, IOException {
//        // Set up mock objects and test data
//        DataReader mockDataReader = mock(DataReader.class);
//        Connection mockConnection = mock(Connection.class);
//        PreparedStatement mockStatement = mock(PreparedStatement.class);
//
//        // Create an instance of Main with mock objects
//        Main main = new Main(mockDataReader, mockConnection, mockStatement);
//
//        // Set up test data
//        List<String[]> testData = new ArrayList<>();
//        testData.add(new String[]{"1", "Product 1", "Vendor 1", "10", "100.0", "5.0", "10.0", "Region 1", "Category 1", "0.1"});
//        when(mockDataReader.readData(anyString())).thenReturn(testData);
//
//        // Verify interactions and assertions
//        verify(mockConnection).prepareStatement(anyString());
//        verify(mockStatement, times(1)).executeBatch();
//
//        // Additional assertions can be added based on your requirements


        Connection Result = databaseUtil.getConnection();

        assertNotNull(Result);

        CSVDataReader csvDataReader = new CSVDataReader();

        csvDataReader.readData()



        };


    }
