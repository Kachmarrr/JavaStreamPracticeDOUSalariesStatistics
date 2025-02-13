package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobManager {

    private final String csvFile = "C:\\Users\\admin\\IdeaProjects\\JavaStreamPractice_DOUSalariesStatistics\\src\\main\\resources\\DOUStatistic.csv";
    private final String csvFileDou = "C:\\Users\\admin\\IdeaProjects\\JavaStreamPracticeDOUSalariesStatistics\\src\\main\\resources\\swd-2024-12.csv";

    public List<Job> readFileAndConvertedToList() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileDou))) {

            return reader.lines()
                    .skip(1)
                    .map(Job::fromCsv)
                    .toList();

        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> calculator() {
        List<Job> job = this.readFileAndConvertedToList();

        int maxSalary = job.stream()
                .map(Job::getSalary)
                .max(Integer::compare)
                .orElse(0);

        int minSalary = job.stream()
                .map(Job::getSalary)
                .min(Integer::compare)
                .orElse(0);

        //int averageSalary = job.stream().map(Job::getSalary)

        // Створюємо мапу і додаємо значення
        Map<String, Integer> result = new HashMap<>();
        result.put("maxSalary", maxSalary);
        result.put("minSalary", minSalary);

        return result;
    }

}
