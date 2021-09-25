package com.vetproject;

import java.sql.*;

public class DataBaseConnection {
    public Connection dataBaseLink;

    public Connection getConnection() {
        String databaseName = "VetProject";
        String user = "";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/";

        try{
          Class.forName("com.mysql.jdbc.Driver");
          dataBaseLink = DriverManager.getConnection(url + databaseName + "?useSSL=false", user, password);
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return dataBaseLink;
    }
}
