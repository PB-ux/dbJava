package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgressionFileManager {
    enum ProgressionTypes {
        arithmetic,
        geometric,
    }
    public String filename;
    private Logger logger;

    ProgressionFileManager(String filename, Logger logger) {
        this.filename = filename;
        this.logger = logger;
    }

    public List<Progression> readProgressionFromCSV() {
        List<Progression> progressions = new ArrayList<>();

        try {
            FileReader file = new FileReader(this.filename);
            BufferedReader br = new BufferedReader(file);
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String type = parts[0];
                    int start = Integer.parseInt(parts[1]);
                    int step = Integer.parseInt(parts[2]);
                    int terms = Integer.parseInt(parts[3]);

                    if (type.equals(ProgressionTypes.arithmetic.name())) {
                        ArithmeticProgression arithmeticProgression = new ArithmeticProgression(start, step, terms);
                        progressions.add(arithmeticProgression);
                        logger.info("An object was created with an arithmetic progression: " + arithmeticProgression.toString());
                    } else if (type.equals(ProgressionTypes.geometric.name())) {
                        GeometricProgression geometricProgression = new GeometricProgression(start, step, terms);
                        progressions.add(geometricProgression);
                        logger.info("An object was created with an geometric progression: " + geometricProgression.toString());
                    } else {
                        logger.log(Level.SEVERE, "Incorrect type");
                    }
                } else {
                    logger.log(Level.SEVERE, "Incorrect csv");
                }
            }
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Read csv failed");
            e.printStackTrace();
        }

        return progressions;
    }

}
