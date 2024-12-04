package com.leoCorp.adventofcode2024.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: ./Day3 input_name");
        }

        var content = getStringFromFile(args[0]);
        System.out.println(computeMulFromString(content));
    }

    private static String getStringFromFile(String filename) {
        var filePath = "res/" + filename + ".txt";

        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static Integer computeMulFromString(String content) {
        var regex = "mul\\(\\d{1,3},\\d{1,3}\\)";

        return Pattern.compile(regex)
                .matcher(content)
                .results() // correspondences stream (MatchResult)
                .mapToInt(match -> {
                    var group = match.group();
                    var length = group.length();
                    var nums = match.group().substring(4, length - 1).split(",");
                    return Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
                })
                .sum();
    }
}
