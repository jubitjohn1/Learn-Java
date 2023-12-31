package org.example;
import utils.databaseUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String csvFilePath = "src\\main\\resources\\Demo.json";

        Connection con;
        try {
            con = databaseUtil.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            return;
        }
        if (con != null) {
            System.out.println("Connection OK");

            DataReader dataReader;
            if (csvFilePath.toLowerCase().endsWith(".csv")) {
                dataReader = new CSVDataReader();
            } else if (csvFilePath.toLowerCase().endsWith(".json")) {
                dataReader = new JSONDataReader();
            } else {
                logger.severe("Unsupported file format");
                return;
            }

            // Read data from the file using the DataReader
            List<String[]> data;
            try {
                data = dataReader.readData(csvFilePath);
            } catch (IOException e) {
                logger.severe("Error reading data from file: " + e.getMessage());
                return;
            }

            dbInsertion.insertData(con,data);


        } else {
            System.out.println("Connection Failed");
        }


    }
}