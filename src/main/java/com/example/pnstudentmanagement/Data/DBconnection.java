package com.example.pnstudentmanagement.Data;

import com.example.pnstudentmanagement.Data.models.Student;

import java.sql.*;
import java.util.ArrayList;

public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost/pnstudet"; //jdbc:mysql is required
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    //CONNECT DATABASE
    public DBconnection(){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connect successfully!!!");
        } catch (SQLException e) {
            System.out.println("Connect failed!!!");
            throw new RuntimeException(e);
        }
    }
    // SELECT STUDENTS FROM DATABASE IN TEMINAL
    public ArrayList<Student> getStudent() {
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try {
            ResultSet results = connection.prepareStatement(sql).executeQuery();
            while (results.next()) {
                Student std = new Student(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getFloat("score")
                );
                list.add(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return list;
    }

    //ADD VALUE TO DATABASE
    public void add( Student std){
        String sql = "INSERT INTO students (name, score) VALUE ('" + std.name+"','"+std.score+"')";
        try {
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("add successfully ('" + std.name+"','"+std.score+"') ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //UPDATE VAlUE TO DATABASE
    public void update(int id,Student std){
        String sql = "UPDATE students SET name='"+std.name+"', score = "+std.score+" WHERE id="+ id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("update successfully ('" + std.name+"','"+std.score+"') ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DELETE VALUE IN DATABASE
    public void delete(int id){
        String sql = "DELETE FROM students WHERE id="+ id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("delete successfully ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
