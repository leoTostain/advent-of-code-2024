package com.leoCorp.adventofcode2024.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Report {
    private final List<Integer> levels;

    public Report(List<Integer> numbers) {
        if (numbers == null || numbers.size() < 2) {
            throw new IllegalArgumentException("List must contains at least 2 numbers.");
        }

        levels = numbers;
    }

    private boolean isIncreasing(List<Integer> list) {
        return list.getFirst() < list.get(1);
    }

    private boolean levelIsSafe(boolean increasing, Integer level, Integer nextLevel) {
        return ((increasing && nextLevel > level) || (!increasing && nextLevel < level))
                && Math.abs(level - nextLevel) >= 1
                && Math.abs(level - nextLevel) <= 3;
    }

    public boolean isSafe() {
        var increasing = isIncreasing(levels);
        if (IntStream.range(0, levels.size() - 1).allMatch(i -> levelIsSafe(increasing, levels.get(i), levels.get(i + 1)))) {
            return true;
        } else {
            return isSafeDampened();
        }
    }

    private boolean isSafe(List<Integer> levels) {
        var increasing = isIncreasing(levels);
        return IntStream.range(0, levels.size() - 1).allMatch(i -> levelIsSafe(increasing, levels.get(i), levels.get(i + 1)));
    }

    public boolean isSafeDampened() {
        var increasing = isIncreasing(levels);

        for (int i = 0; i < levels.size() - 1; i++) {
            if( !levelIsSafe(increasing, levels.get(i), levels.get(i + 1)) ) {
                var copyLevels = new ArrayList<>(levels);
                copyLevels.remove(i);

                if (isSafe(copyLevels)) {
                    return true;
                }

                if (i > 0) {
                    copyLevels = new ArrayList<>(levels);
                    copyLevels.remove(i - 1);

                    if (isSafe(copyLevels)) {
                        return true;
                    }
                }

                copyLevels = new ArrayList<>(levels);
                copyLevels.remove(i + 1);
                return isSafe(copyLevels);
            }
        }

        return true;
    }
}
