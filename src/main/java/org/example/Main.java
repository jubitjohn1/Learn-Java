package org.example;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        if (args.length != 1) {
//            System.err.println("Usage: java Main <file_path>");
//            return;
//        }

        String configFilePath = "config.properties";
        String csvFilePath = "C:\\Users\\jubit.john\\IdeaProjects\\maven_project\\src\\main\\resources\\Demo.json";
//        String csvFilePath = args[0];

        int batchSize = 20;
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            return;
        }

        String dbURL = properties.getProperty("dbURL");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            // Explicitly load the PostgreSQL JDBC driver class
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found. Make sure the driver JAR is in the classpath.");
            return;
        }
        Connection con = DriverManager.getConnection(dbURL, username, password);
        if (con != null) {
            System.out.println("Connection OK");

            DataReader dataReader;
            if (csvFilePath.toLowerCase().endsWith(".csv")) {
                dataReader = new CSVDataReader();
            } else if (csvFilePath.toLowerCase().endsWith(".json")) {
                dataReader = new JSONDataReader();
            } else {
                System.err.println("Unsupported file format");
                return;
            }

            // Read data from the file using the DataReader
            List<String[]> data;
            try {
                data = dataReader.readData(csvFilePath);
            } catch (IOException e) {
                System.err.println("Error reading data from file: " + e.getMessage());
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