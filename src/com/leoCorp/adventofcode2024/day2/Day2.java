package com.leoCorp.adventofcode2024.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: ./Day2 input_name");
        }

        var reports = getReportsFromFile(args[0]);

        System.out.println(reports.stream().filter(Report::isSafe).count());
    }

    private static List<Report> getReportsFromFile(String filename) {
        var filePath = "res/" + filename + ".txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
           return stream.map(line -> new Report(
                    Arrays.stream(line.split("\\s+"))
                            .map(Integer::parseInt)
                            .toList()
                    )
           ).toList();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
