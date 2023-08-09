package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import java.sql.SQLException;


public class dbInsertion {

    public static void insertData(Connection con, List<String[]> data) throws SQLException {

        // Apply SQL operations using prepared statement
        String sql = "INSERT INTO final (product_id, product_name, product_vendor, product_quantity, product_price, product_weight, product_length, product_region, product_category, product_discount)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = con.prepareStatement(sql);

        int count = 0;
        int batchSize=20;
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


    }
}