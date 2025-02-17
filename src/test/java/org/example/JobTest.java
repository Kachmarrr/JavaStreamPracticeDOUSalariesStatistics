package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class JobTest {
    final String csvFileDou = "C:\\Users\\admin\\IdeaProjects\\JavaStreamPracticeDOUSalariesStatistics\\src\\main\\resources\\swd-2024-12.csv";

    private List<Job> jobs;
    @BeforeEach
    void setUp() {

        jobs = Job.readFileAndConvertedToList();
    }

    @Test
    void ReadAllFileTest() throws IOException {

        long lineCount = Files.lines(Paths.get(csvFileDou)).count();
        assertEquals(lineCount - 1, jobs.size());
    }

    @Test
    void StreamConditionJavaTest(){

        List<Job> javaJobs = jobs.stream()
                .filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Java"))
                .toList();

        assertEquals(621, javaJobs.size());
    }

    @Test
    void calculatorSalaryJavaTest() {

        List<Job> javaJobs = jobs.stream()
                .filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Java"))
                .toList();

        Map<String, Integer> map = Job.calculator(javaJobs);

        map.forEach((key, value) -> System.out.println(key + " " + value));
    }

    @Test
    void calculatorSalaryPythonTest() {

        List<Job> pythonJobs = jobs.stream()
                .filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Python"))
                .toList();

        Map<String, Integer> map = Job.calculator(pythonJobs);

        map.forEach((key, value) -> System.out.println(key + " " + value));
    }

    @Test
    void mapReduce() {

        List<Job> javaJobs = jobs.stream()
                //.filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Java"))
                .toList();

        Map<String, Map<String, Double>> groupA = Job.mapReduce(
                javaJobs,
                Job::getPosition,
                Job::getTechnology,
                Job::getSalary
        );

        groupA.forEach((key, value) -> System.out.println(key + " " + value));

        //System.out.println(groupA);
    }

    @Test
    void stats() {

        List<Job> javaJobs = jobs.stream()
                .filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Java"))
                .toList();

        Stats stats1 = Job.statsTeeing(javaJobs);
        Stats stats2 = Job.statsSummary(javaJobs);

        assertEquals(stats1, stats2);

        System.out.println(stats1);

    }
}