package org.example;

import java.sql.*;

public class JDBC {
    private String url = "jdbc:postgresql://localhost:5432/java";
    private String userName = "admin";
    private String password = "admin";

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }
}
