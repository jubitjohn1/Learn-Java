package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONDataReader implements DataReader {
    @Override
    public List<String[]> readData(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonContent);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String product_id = String.valueOf(jsonObject.get("product_id"));
                String product_name = (String) jsonObject.get("product_name");
                String product_vendor = (String) jsonObject.get("product_vendor");
                String product_quantity = String.valueOf(jsonObject.get("product_quantity"));
                String product_price = String.valueOf(jsonObject.get("product_price"));
                String product_weight = String.valueOf(jsonObject.get("product_weight"));
                String product_length = String.valueOf(jsonObject.get("product_length"));
                String product_region = (String) jsonObject.get("product_region");
                String product_category = (String) jsonObject.get("product_category");
                String product_discount = String.valueOf(jsonObject.get("product_discount"));

                String[] values = new String[]{
                        product_id,
                        product_name,
                        product_vendor,
                        product_quantity,
                        product_price,
                        product_weight,
                        product_length,
                        product_region,
                        product_category,
                        product_discount
                };
                data.add(values);
            }
        } catch (ParseException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}
