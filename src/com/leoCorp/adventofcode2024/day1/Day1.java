package com.leoCorp.adventofcode2024.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: ./Day1 input_name");
        }

        System.out.println(getTotalDistance(args[0]));
    }

    private static int getTotalDistance(String fileName) {
        var filePath = "res/" + fileName + ".txt";
        var list1 = new ArrayList<Integer>();
        var list2 = new ArrayList<Integer>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(string -> {
                var nums = string.split("\\s+");

                if (nums.length != 2) {
                    throw new IllegalStateException("Wrong format (expected: number  number)");
                }

                list1.add(Integer.parseInt(nums[0]));
                list2.add(Integer.parseInt(nums[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list1.size() != list2.size()) {
            throw new IllegalStateException("Lists must be same size!!!");
        }

        Collections.sort(list1);
        Collections.sort(list2);


        var totalDistance = 0;

        for (int i = 0; i < list1.size(); i++) {
            totalDistance += Math.abs(list1.get(i) - list2.get(i));
        }

        return totalDistance;
    }
}
