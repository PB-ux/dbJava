package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.createConnection();


//        List<Progression> progressions = getProgression();
//        insertRows(progressions, conn);

//        getWhere(conn);

        ProgressionTable.transactionDirtyRead(conn, 10, "geometric", "arithmetic");

        conn.close();

    }

    private static List<Progression> getProgression() {
        FileHandler fileHandler;
        List<Progression> progressionList = null;

        try {
            fileHandler = new FileHandler("resources/logfile.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            ProgressionFileManager fileManager = new ProgressionFileManager("resources/data.csv", logger);
            progressionList = fileManager.readProgressionFromCSV();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return progressionList;
    }

    private static void insertRows(List<Progression> progressions, Connection conn) throws SQLException {
        for (int i = 0; i < progressions.size(); i++) {
            Progression progression = progressions.get(i);
            if (progression instanceof ArithmeticProgression) {
                ArithmeticProgression arithmeticProgression = (ArithmeticProgression) progression;
                int start = arithmeticProgression.start;
                int step = arithmeticProgression.step;
                int terms = arithmeticProgression.terms;
                ProgressionTable.insertRow(i+1, "arithmetic", start, step, terms, conn);
            } else if (progression instanceof GeometricProgression) {
                GeometricProgression geometricProgression = (GeometricProgression) progression;
                int start = geometricProgression.start;
                int step = geometricProgression.step;
                int terms = geometricProgression.terms;
                ProgressionTable.insertRow(i+1, "geometric", start, step, terms, conn);
            }
        }
    }

    private static void getWhere(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите тип прогрессии: ");
        String type = scanner.nextLine();
        scanner.close();

        ResultSet resultSet = ProgressionTable.where(conn, type);

        while(resultSet.next()) {
            System.out.println("Id: " + resultSet.getInt("id"));
            System.out.println("Type: " + resultSet.getString("type"));
            System.out.println("Start: " + resultSet.getInt("start"));
            System.out.println("Step: " + resultSet.getInt("step"));
            System.out.println("countTerm: " + resultSet.getInt("countterm"));
        }

    }
}