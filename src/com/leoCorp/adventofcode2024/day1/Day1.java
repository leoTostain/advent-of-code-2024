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
        var list1 = new ArrayList<Integer>();
        var list2 = new ArrayList<Integer>();

        populateListFromFile(list1, list2, args[0]);
        System.out.println(getTotalDistance(list1, list2));
    }

    private static void populateListFromFile(ArrayList<Integer> list1, ArrayList<Integer> list2, String fileName) {
        var filePath = "res/" + fileName + ".txt";

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
    }

    private static int getTotalDistance(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        if (list1.size() != list2.size()) {
            throw new IllegalStateException("Lists must be of the same size.");
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
