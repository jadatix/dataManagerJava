package com.manager.datamanager;

import entities.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

public class SQLConnection {
    public void testing(String[] args) {
        try {
            Connection connection;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datamanager", "postgres", "");
            if (connection != null) {
                System.out.println("Connection: we did it :)");
            } else {
                System.out.println("Connection: that sucks man :(");
            }

            Statement  statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from test1table");

            while (res.next()){
                System.out.println(res.getString("name"));
            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int addTeacher(Teacher teacher){
        int statusCode=0;
        try{
            Connection connection;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datamanager", "postgres", "");
            if (connection != null) {
                System.out.println("Connection: we did it :)");
            } else {
                System.out.println("Connection: connection failed :(");
                statusCode=1;
            }

            Statement  statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Teacher(id,name,lastname,email,password,gender,age,\"headOf\",subject)" +
                    "VALUES (nextval('teacher_id_seq'),'"+teacher.getName()+
                    "','"+teacher.getLastname()+
                    "','"+teacher.getEmail()+
                    "','"+teacher.getPassword()+
                    "','"+teacher.getGender()+
                    "','"+teacher.getAge()+
                    "','"+teacher.getHeadOf()+
                    "','"+teacher.getSubject().stream().collect(Collectors.joining("|"))+
                    "')");

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        return statusCode;
    }
}
