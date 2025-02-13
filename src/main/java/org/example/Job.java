package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import lombok.*;

import java.io.IOException;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Job {

    // "position","domain","city","company","experience","english","salary","technology","specialization","period","title","group","framework"
    // "QA/QC/SDET","Ecommerce",12,1,3,5,1220,"","Manual QA","2024-12",3,2,""
    // "Software Engineer","Adtech / Advertising",1,1,6,4,6500,"Java","Back-end","2024-12",4,1,"Hibernate,Spring,Vue.js,Ruby on Rails"
    // "QA/QC/SDET","Security",1,1,3,5,1700,"","Manual QA","2024-12",3,2,""

    private String position;
    private String domain;
    private String city;
    private int company;
    private int experience;
    private int english;
    private int salary;
    private String technology;
    private String specialization;
    private String period;
    private int title;
    private int group;
    private String framework;

    // Метод для створення об'єкта Job з рядка CSV
    public static Job fromCsv(String line) {

        try {
            // String[] fields = line.split(","); - працював некоректно тому вирішив використати CSVParser
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .build();
            String[] fields = parser.parseLine(line);

            return new Job(
                    stringDefaultValue(fields[0], ""),          // position
                    stringDefaultValue(fields[1], ""),          // domain
                    stringDefaultValue(fields[2], ""),          // city
                    intDefaultValue(fields[3], 0),              // company
                    intDefaultValue(fields[4], 0),              // experience
                    intDefaultValue(fields[5], 0),              // english
                    intDefaultValue(fields[6], 0),              // salary
                    stringDefaultValue(fields[7], ""),          // technology
                    stringDefaultValue(fields[8], ""),          // specialization
                    stringDefaultValue(fields[9], ""),          // period
                    intDefaultValue(fields[10], 0),             // title
                    intDefaultValue(fields[11], 0),             // group
                    stringDefaultValue(fields[12], "")          // framework
            );
        } catch (IOException e) {
            throw new RuntimeException("Помилка під час розбору CSV рядка", e);
        }
    }

    /**
     * Метод для обробки числових значень
     */
    public static int intDefaultValue(String value, int defaultValue) {

        if (value == null) {

            return defaultValue;
        }
        if (value.trim().isEmpty()) {

            return defaultValue;
        }

        if (value.trim().equals("NA")) {
            return defaultValue;
        }

        return Integer.parseInt(value.trim());
        // NumberFormatException
    }

    /**
     * Метод для обробки рядків
     */
    public static String stringDefaultValue(String value, String defaultValue) {

        if (value == null) {
            return defaultValue;
        }
        if (value.trim().isEmpty()) {
            return defaultValue;
        }
        if (value.trim().equals("NA")) {
            return defaultValue;
        }
        return value.trim();
    }
}