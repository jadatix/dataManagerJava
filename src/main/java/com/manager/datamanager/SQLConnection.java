package com.manager.datamanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnection {
    public static void main(String[] args) {
        try {
            Connection connection;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shop", "postgres", "");
            if (connection != null) {
                System.out.println("Connection: we did it :)");
            } else {
                System.out.println("Connection: that sucks man :(");
            }

            Statement  statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from test1table");

//            while (res.next()){
//                System.out.println(res.getString("name"));
//            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
