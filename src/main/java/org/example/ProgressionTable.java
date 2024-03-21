package org.example;

import java.sql.*;
import java.util.ArrayList;

public class ProgressionTable {

    public static int insertRow(int id, String type, int start, int step, int countTerm, Connection connection) throws SQLException {
        String sql = "INSERT INTO Progression (id, type, start, step, countTerm) Values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, type);
        preparedStatement.setInt(3, start);
        preparedStatement.setInt(4, step);
        preparedStatement.setInt(5, countTerm);

        int rows = preparedStatement.executeUpdate();

        return rows;
    }

    public static ResultSet selectRows(Connection connection) throws SQLException {
        String query = "SELECT * FROM Progression";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }

    public static ResultSet where(Connection connection, String type) throws SQLException {
        String query = "SELECT * FROM Progression" + " WHERE type = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, type);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }


    public static void transactionDirtyRead(Connection connection, Integer value, String typeUpdate, String typeDelete) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        String updateQuery = "UPDATE Progression SET" + " start = ?" + " WHERE type = ?";
        PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateQuery);

        preparedStatementUpdate.setInt(1, value);
        preparedStatementUpdate.setString(2, typeUpdate);

        String deleteQuery = "DELETE FROM Progression WHERE type = ?";
        PreparedStatement preparedStatementDelete = connection.prepareStatement(deleteQuery);

        preparedStatementDelete.setString(1, typeDelete);

        Savepoint savepoint = connection.setSavepoint();

        try {
            int resultSetUpdate = preparedStatementUpdate.executeUpdate();
            int resultSetDelete = preparedStatementDelete.executeUpdate();

           connection.commit();
           System.out.println("Transaction committed.");
           System.out.println("ResultSetUpdate: " + resultSetUpdate);
           System.out.println("ResultSetDelete: " + resultSetDelete);
        } catch(SQLException e) {
           connection.rollback(savepoint);
           System.out.println("Transaction rolled back.");
           e.printStackTrace();
        }
    }

}
