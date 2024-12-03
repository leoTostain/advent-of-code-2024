package com.leoCorp.adventofcode2024.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: ./Day1 input_name");
        }
        var leftList = new ArrayList<Integer>();
        var rightList = new ArrayList<Integer>();

        populateListFromFile(leftList, rightList, args[0]);
        System.out.println(getTotalDistance(leftList, rightList));
        System.out.println(computeSimilarityScore(leftList, rightList));
    }

    private static void populateListFromFile(ArrayList<Integer> leftList, ArrayList<Integer> rightList, String fileName) {
        var filePath = "res/" + fileName + ".txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(string -> {
                var nums = string.split("\\s+");

                if (nums.length != 2) {
                    throw new IllegalStateException("Wrong format (expected: number  number)");
                }

                leftList.add(Integer.parseInt(nums[0]));
                rightList.add(Integer.parseInt(nums[1]));
            });
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static int getTotalDistance(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        if (leftList.size() != rightList.size()) {
            throw new IllegalStateException("Lists must be of the same size.");
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        var totalDistance = 0;

        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }

        return totalDistance;
    }

    private static int computeSimilarityScore(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        var similarityScore = 0;
        var mapRightList = new HashMap<Integer, Integer>();

        rightList.forEach(num -> mapRightList.merge(num, 1, Integer::sum));

        similarityScore = leftList.stream()
                .mapToInt(num -> num * mapRightList.getOrDefault(num, 0))
                .sum();

        return similarityScore;
    }
}
