package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    public static List<Job> readFileAndConvertedToList() {
        final String csvFileDou = "C:\\Users\\admin\\IdeaProjects\\JavaStreamPracticeDOUSalariesStatistics" +
                "\\src\\main\\resources\\swd-2024-12.csv";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileDou))) {

            return reader.lines()
                    .skip(1)
                    .map(FileManager::fromCsv)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToTXTFile(List<Job> jobs) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("newFile.txt"));

        writer.write(jobs.toString());

        writer.close();
    }

    public static void writeToCSVFile(List<Job> jobs, String fileName) throws IOException {

        CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName + ".csv"));

        csvWriter.writeNext(new String[]{"position","domain","city","company","experience","english","salary",
                "technology","specialization","period","title","group","framework"});

        jobs.forEach(obj -> csvWriter.writeNext(obj.toArray()));
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

}
