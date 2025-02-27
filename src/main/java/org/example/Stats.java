package org.example;

import lombok.*;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Stats {

    private Integer minValue;
    private Integer maxValue;
    private Integer avgValue;


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

    public static Stats statsTeeing(List<Job> list) {

        return list.stream()
                .map(Job::getSalary)
                .collect(Collectors.teeing(
                        Collectors.minBy(Integer::compareTo),
                        Collectors.maxBy(Integer::compareTo),
                        (min, max) -> {

                            double avg = list.stream().mapToInt(Job::getSalary).average().orElse(0);

                            return new Stats(min.orElse(0), max.orElse(0), (int) avg);
                        }
                ));
    }


    public static Stats statsSummary(List<Job> list) {

        IntSummaryStatistics stats = list.stream()
                .mapToInt(Job::getSalary)
                .summaryStatistics();

        return new Stats(stats.getMin(), stats.getMax(), (int) stats.getAverage());
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
