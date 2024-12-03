package com.leoCorp.adventofcode2024.day2;

import java.util.List;
import java.util.stream.IntStream;

public class Report {
    private final List<Integer> levels;
    private final boolean isIncreasing;

    public Report(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contains at least 2 numbers.");
        }

        levels = numbers;
        isIncreasing = levels.getFirst() < levels.get(1);
    }

    private boolean levelIsSafe(Integer level, Integer nextLevel) {
        return ((isIncreasing && nextLevel >= level) || (!isIncreasing && nextLevel <= level))
                && Math.abs(level - nextLevel) >= 1
                && Math.abs(level - nextLevel) <= 3;
    }

    public boolean isSafe() {
        return IntStream.range(0, levels.size() - 1).allMatch(i -> levelIsSafe(levels.get(i), levels.get(i + 1)));
    }
}
