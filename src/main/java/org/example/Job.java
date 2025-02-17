package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static List<Job> readFileAndConvertedToList() {
        final String csvFileDou = "C:\\Users\\admin\\IdeaProjects\\JavaStreamPracticeDOUSalariesStatistics\\src\\main\\resources\\swd-2024-12.csv";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileDou))) {

            return reader.lines()
                    .skip(1)
                    .map(Job::fromCsv)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для створення об'єкта Job з рядка CSV
    public static Job fromCsv(String line) {

        try {
            // String[] fields = line.split(","); - працював некоректно тому вирішив використати CSVParser
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .build();
            String[] fields = parser.parseLine(line);

            return new Job(
                    stringDefaultValue(fields[0]),          // position
                    stringDefaultValue(fields[1]),          // domain
                    stringDefaultValue(fields[2]),          // city
                    intDefaultValue(fields[3]),             // company
                    intDefaultValue(fields[4]),             // experience
                    intDefaultValue(fields[5]),             // english
                    intDefaultValue(fields[6]),             // salary
                    stringDefaultValue(fields[7]),          // technology
                    stringDefaultValue(fields[8]),          // specialization
                    stringDefaultValue(fields[9]),          // period
                    intDefaultValue(fields[10]),            // title
                    intDefaultValue(fields[11]),            // group
                    stringDefaultValue(fields[12])          // framework
            );
        } catch (IOException e) {
            throw new RuntimeException("Помилка під час розбору CSV рядка", e);
        }
    }

    /**
     * Метод для обробки числових значень
     */
    private static int intDefaultValue(String value) {

        if (value == null) {
            return 0;
        }
        if (value.trim().isEmpty()) {
            return 0;
        }
        if (value.trim().equals("NA")) {
            return 0;
        }

        return Integer.parseInt(value.trim());
        // NumberFormatException
    }

    /**
     * Метод для обробки рядків
     */
    private static String stringDefaultValue(String value) {

        if (value == null) {
            return "";
        }
        if (value.trim().isEmpty()) {
            return "";
        }
        if (value.trim().equals("NA")) {
            return "";
        }
        return value.trim();
    }

    public static Map<String, Integer> calculator(List<Job> list) {

        int maxSalary = list.stream()
                .map(Job::getSalary)
                .max(Integer::compare)
                .orElse(0);

        int minSalary = list.stream()
                .map(Job::getSalary)
                .min(Integer::compare)
                .orElse(0);

        int averageSalary = list.stream()
                .mapToInt(Job::getSalary)
                .sum();

        Map<String, Integer> result = new HashMap<>();
        result.put("maxSalary: ", maxSalary);
        result.put("minSalary: ", minSalary);
        result.put("averageSalary: ", averageSalary / list.size());
        result.put("medianSalary: ", median(list));

        return result;
    }

    private static int median(List<Job> list) {

        List<Integer> medianSalaryStream = list.stream()
                .map(Job::getSalary)
                .sorted()
                .toList();

        if (medianSalaryStream.size() % 2 == 1) {

            return medianSalaryStream.get(medianSalaryStream.size() / 2);
        } else {
            int mid1 = medianSalaryStream.get(medianSalaryStream.size() / 2 - 1);
            int mid2 = medianSalaryStream.get(medianSalaryStream.size() / 2);

            return (mid1 + mid2) / 2;
        }
    }

    public static <K1, K2> Map<K1, Map<K2, Double>> mapReduce(
            List<Job> list,
            Function<Job, K1> firstKeyFunction,
            Function<Job, K2> secondKeyFunction,
            Function<Job, Integer> avgSalary
    ) {
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                firstKeyFunction,
                                Collectors.groupingBy(
                                        secondKeyFunction,
                                        Collectors.averagingInt(
                                                avgSalary::apply)))
                );
    }
}