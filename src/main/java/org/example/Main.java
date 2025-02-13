package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        JobManager manager = new JobManager();

        List<Job> jobs = manager.readFileAndConvertedToList();

        List<Job> javaJobs = jobs.stream()
                .filter(job -> job.getPosition().equals("Software Engineer"))
                .filter(job -> job.getTechnology().equals("Java"))
                .toList();

        manager.calculator();

        //jobs.forEach(System.out::println);
        //javaJobs.forEach(System.out::println);

        //System.out.println(jobs.size());
        //System.out.println(javaJobs.size());

        Map<String, Integer> map = manager.calculator();

        map.forEach((key, value) -> System.out.println(key + " " + value));

    }
}