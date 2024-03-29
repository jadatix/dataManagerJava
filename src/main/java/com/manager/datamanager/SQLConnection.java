package com.manager.datamanager;

import entities.Student;
import entities.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLConnection {
    public static void main(String[] args) {
        System.out.println(SQLConnection.getAllTeachers());
//        SQLConnection.deleteTeacher("2","1");
    }



    public static boolean isExists(String name, String lastname, String table){
        Connection connection = init();
        try {
            Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM "+table+" WHERE name ='"+
                name+"' and lastname = '"+lastname+"';");

             return result.next();

        } catch (SQLException err){
            return false;
        }
    }

    private static Connection init(){
        try{
            Connection connection;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datamanager", "postgres", "");
            if (connection != null) {
                System.out.println("Connection: we did it :)");
            } else {
                System.out.println("Connection: connection failed :(");
            }
            return  connection;
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void addTeacher(Teacher teacher) throws SQLException {
        Connection connection = init();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Teacher(id,name,lastname,email,password,gender,age,\"headOf\",subject)" +
                    "VALUES (nextval('teacher_id_seq'),'"+teacher.getName()+
                    "','"+teacher.getLastname()+
                    "','"+teacher.getEmail()+
                    "','"+teacher.getPassword()+
                    "','"+teacher.getGender()+
                    "','"+teacher.getAge()+
                    "','"+teacher.getHeadOf()+
                    "','"+teacher.getSubject().stream().collect(Collectors.joining("|"))+
                    "')";
            statement.executeUpdate(query);
    }

    public static List<Teacher> getAllTeachers(){
        Connection connection = init();
        List<Teacher> teachers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM teacher ORDER BY lastname;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                List<String> subjects = Arrays.asList(result.getString("subject").split("[|]"));
                teachers.add(new Teacher(
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("gender"),
                        Integer.parseInt(result.getString("age")),
                        result.getString("password"),
                        subjects,
                        result.getString("headOf")
                ));
            }
            return teachers;
        }catch (SQLException err){
            System.out.println("Connection error");
        }
        return null;
    }

    public static void delete(String name, String lastname, String table){
        Connection connection = init();
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM "+table+" WHERE name ='"+
                    name+"' and lastname = '"+lastname+"';";
            statement.executeUpdate(query);
        }catch (SQLException err){

        }
    }

    public static boolean updateTeacher(Teacher teacher, String name,String lastname){
        Connection connection = init();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE teacher SET name='"+ teacher.getName() +"'," +
                    "lastname='"+teacher.getLastname()+"'," +
                    "email='"+teacher.getEmail()+"'," +
                    "gender='"+teacher.getGender() + "'," +
                    "age='"+teacher.getAge()+"'," +
                    "\"headOf\"='"+teacher.getHeadOf()+"'," +
                    "subject='"+teacher.getSubject().stream().collect(Collectors.joining("|"))+"'" +
                    " WHERE name='"+name+"' and lastname='"+lastname+"';";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException err){
            System.out.println(err.getMessage());
            return false;
        }
    }

    public static void addStudent(Student student) {
        Connection connection = init();
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO student(id, name, lastname, email, password, gender, age, \"groupNum\", \"durationOfEducation\", \"startEducation\")" +
                    "VALUES (nextval('student_id_seq'),'" + student.getName() +
                    "','" + student.getLastname() +
                    "','" + student.getEmail() +
                    "','" + student.getPassword() +
                    "','" + student.getGender() +
                    "','" + student.getAge() +
                    "','" + student.getGroupNum() +
                    "','" + student.getDurationOfEducation() +
                    "','" + student.getStartEducation("yyyy-MM-dd") +
                    "')";
            statement.executeUpdate(query);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            System.out.println(err.getStackTrace());
        }
    }

    public static List<Student> getAllStudents(){
        Connection connection = init();
        List<Student> students = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM student ORDER BY lastname;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                students.add(new Student(
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("gender"),
                        Integer.parseInt(result.getString("age")),
                        result.getString("password"),
                        result.getString("groupNum"),
                        Integer.parseInt(result.getString("durationOfEducation")),
                        result.getDate("startEducation")
                ));
            }
            return students;
        }catch (SQLException err){
            System.out.println("Connection error");
        }
        return null;
    }

    public static boolean updateStudent(Student student, String name, String lastname){
        Connection connection = init();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE student SET name='"+ student.getName() +"'," +
                    "lastname='"+ student.getLastname()+"'," +
                    "email='"+ student.getEmail()+"'," +
                    "gender='"+ student.getGender() + "'," +
                    "age='"+ student.getAge()+"'," +
                    "\"groupNum\"='"+ student.getGroupNum()+"'," +
                    "\"durationOfEducation\"='"+student.getDurationOfEducation()+"'," +
                    "\"startEducation\"='"+student.getStartEducation("yyyy-MM-dd")+"'" +
                    " WHERE name='"+name+"' and lastname='"+lastname+"';";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException err){
            System.out.println(err.getMessage());
            return false;
        }
    }

}
