package org.example;
import utils.databaseUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String csvFilePath = "C:\\Users\\jubit.john\\IdeaProjects\\maven_project\\src\\main\\resources\\Demo.json";

        int batchSize = 20;

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



            // Apply SQL operations using prepared statement
            String sql = "INSERT INTO final (product_id, product_name, product_vendor, product_quantity, product_price, product_weight, product_length, product_region, product_category, product_discount)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = con.prepareStatement(sql);

            int count = 0;
            for (String[] rowData : data) {
                String product_id = rowData[0];
                String product_name = rowData[1];
                String product_vendor = rowData[2];
                String product_quantity = rowData[3];
                String product_price = rowData[4];
                String product_weight = rowData[5];
                String product_length = rowData[6];
                String product_region = rowData[7];
                String product_category = rowData[8];
                String product_discount = rowData[9];

                int id = Integer.parseInt(product_id);
                statement.setInt(1, id);

                statement.setString(2, product_name);
                statement.setString(3, product_vendor);

                int pQuantity = Integer.parseInt(product_quantity);
                statement.setInt(4, pQuantity);

                float price = Float.parseFloat(product_price);
                statement.setFloat(5, price);

                float weight = Float.parseFloat(product_weight);
                statement.setFloat(6, weight);

                float length = Float.parseFloat(product_length);
                statement.setFloat(7, length);

                statement.setString(8, product_region);
                statement.setString(9, product_category);

                float discount = Float.parseFloat(product_discount);
                statement.setFloat(10, discount);

                statement.addBatch();

                if (++count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            // Execute any remaining queries in the batch
            statement.executeBatch();

            // Close resources
            statement.close();
            con.close();



        } else {
            System.out.println("Connection Failed");
        }


    }
}