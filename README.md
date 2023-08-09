# Java Project with JDBC, PostgreSQL, and Maven Integration

This project is a practical example developed during the Developer Training program. It showcases a Java application that efficiently handles .csv and .json file data, seamlessly integrating with a PostgreSQL database.

## Requirements
* Java Development Kit (JDK)
* Apache Maven
* PostgreSQL Database Server

## How to Execute
1. Begin by cloning this repository using the command: `git clone `.
2. Set up a PostgreSQL database and create a table named "people" with the following structure:

```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(255),
    product_vendor VARCHAR(255),
    product_quantity INT,
    product_price DECIMAL(10, 2),
    product_weight DECIMAL(10, 2),
    product_length DECIMAL(10, 2),
    product_region VARCHAR(255),
    product_category VARCHAR(255),
    product_discount DECIMAL(4, 2)
);

```

By adhering to these steps, you'll be able to effortlessly experience the functionality of this Java application that leverages JDBC, PostgreSQL, and Maven technologies.
