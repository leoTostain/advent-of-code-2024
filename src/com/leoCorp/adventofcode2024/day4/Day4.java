package com.leoCorp.adventofcode2024.day4;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: ./Day4 input_name");
        }

        var strs = getStringListFromFile(args[0]);
        System.out.println(findTextInListString("XMAS", strs));
        System.out.println(findCrossInListString("MAS", strs));
    }

    private static List<String> getStringListFromFile(String filename) {
        var filePath = "res/" + filename + ".txt";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.toList();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static long findCrossInListString(String toFind, List<String> toLookIn) {
        var sum = 0L;
        var toFindSize = toFind.length();
        var reverseToFind = new StringBuilder(toFind).reverse().toString();
        var numberOfHorizontalDiagonal = toLookIn.size() - (toFindSize - 1);

        // Diagonal Left to Right index arrays
        int[] DLRSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];
        int[] backwardDLRSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];

        // Diagonal Right to Left index arrays
        int[] DRLSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];
        int[] backwardDRLSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];

        List<int[]> coordinates = new ArrayList<>();

        for (var y = 0; y < toLookIn.size(); y++) {
            var line = toLookIn.get(y);
            var charArray = line.toCharArray();

            for (var x = 0; x < charArray.length; x++) {
                var letter = charArray[x];

                // Looking for diagonals Left to Right
                var arrayIndex = getDiagonalLeftToRightIndex(numberOfHorizontalDiagonal, x, y);
                if (arrayIndex >= 0) {
                    if (lookForWord(toFind, letter, DLRSearchingIndexArray, arrayIndex)) {
                        for (int[] coord : coordinates) {
                            // if other diagonal found in x - 2
                            if (coord[0] == y && coord[1] == x - 2) {
                                sum++;
                            }
                        }
                    }
                    if (lookForWord(reverseToFind, letter, backwardDLRSearchingIndexArray, arrayIndex)) {
                        for (int[] coord : coordinates) {
                            // if other diagonal found in x - 2
                            if (coord[0] == y && coord[1] == x - 2) {
                                sum++;
                            }
                        }
                    }
                }

                // Looking for diagonals Right to Left
                arrayIndex = getDiagonalRightToLeftIndex(numberOfHorizontalDiagonal * 2 - 1, toFindSize, x, y);
                if (arrayIndex >= 0) {
                    if (lookForWord(toFind, letter, DRLSearchingIndexArray, arrayIndex)) {
                        coordinates.add(new int[]{y, x});
                    }
                    if (lookForWord(reverseToFind, letter, backwardDRLSearchingIndexArray, arrayIndex)) {
                        coordinates.add(new int[]{y, x});
                    }
                }
            }

        }

        return sum;
    }

    private static long findTextInListString(String toFind, List<String> toLookIn) {
        var sum = 0L;
        var toFindSize = toFind.length();
        var reverseToFind = new StringBuilder(toFind).reverse().toString();
        var numberOfHorizontalDiagonal = toLookIn.size() - (toFindSize - 1);

        // Vertical index arrays
        int[] verticalSearchingIndexArray = new int[toLookIn.getFirst().length()];
        int[] backwardVerticalSearchingIndexArray = new int[toLookIn.getFirst().length()];

        // Diagonal Left to Right index arrays
        int[] DLRSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];
        int[] backwardDLRSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];

        // Diagonal Right to Left index arrays
        int[] DRLSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];
        int[] backwardDRLSearchingIndexArray = new int[numberOfHorizontalDiagonal * 2 - 1];

        for (var y = 0; y < toLookIn.size(); y++) {
            var line = toLookIn.get(y);
            var charArray = line.toCharArray();

            sum += findHorizontal(toFind, reverseToFind, line);
            for (var x = 0; x < charArray.length; x++) {
                var letter = charArray[x];

                // Looking from Up to Down
                if (lookForWord(toFind, letter, verticalSearchingIndexArray, x)) {
                    sum++;
                }

                // Looking from Down to Up
                if (lookForWord(reverseToFind, letter, backwardVerticalSearchingIndexArray, x)) {
                    sum++;
                }

                // Looking for diagonals Left to Right
                var arrayIndex = getDiagonalLeftToRightIndex(numberOfHorizontalDiagonal, x, y);
                if (arrayIndex >= 0) {
                    if (lookForWord(toFind, letter, DLRSearchingIndexArray, arrayIndex)) {
                        sum++;
                    }
                    if (lookForWord(reverseToFind, letter, backwardDLRSearchingIndexArray, arrayIndex)) {
                        sum++;
                    }
                }

                // Looking for diagonals Right to Left
                arrayIndex = getDiagonalRightToLeftIndex(numberOfHorizontalDiagonal * 2 - 1, toFindSize, x, y);
                if (arrayIndex >= 0) {
                    if (lookForWord(toFind, letter, DRLSearchingIndexArray, arrayIndex)) {
                        sum++;
                    }
                    if (lookForWord(reverseToFind, letter, backwardDRLSearchingIndexArray, arrayIndex)) {
                        sum++;
                    }
                }
            }
        }

        return sum;
    }

    private static long findHorizontal(String toFind, String reverseToFind, String line) {
        var sum = 0L;

        sum += Pattern.compile(toFind)
                .matcher(line)
                .results()
                .count();

        sum += Pattern.compile(reverseToFind)
                .matcher(line)
                .results()
                .count();

        return sum;
    }

    // Return true if last letter of the word to find is found
    private static boolean lookForWord(String toFind, char letter, int[] searchingIndexArray, int arrayIndex) {
        var founded = false;
        var searchingIndex = searchingIndexArray[arrayIndex];

        if (letter == toFind.charAt(searchingIndex)) {
            searchingIndex++;
        } else if (letter == toFind.charAt(0)) {
            searchingIndex = 1;
        } else {
            searchingIndex = 0;
        }

        if (searchingIndex == toFind.length()) {
            founded = true;
            searchingIndex = 0;
        }
        searchingIndexArray[arrayIndex] = searchingIndex;
        return founded;
    }

    private static int getDiagonalLeftToRightIndex(int numberOfHorizontalDiagonal, int x, int y) {
        var indexDif = x - y;

        // Checking if index permit to find a diagonal
        if (indexDif >= 0) {
            if (indexDif < numberOfHorizontalDiagonal ) {
                return indexDif;
            }
        } else {
            indexDif = Math.abs(indexDif);
            if (indexDif < numberOfHorizontalDiagonal ) {
                return numberOfHorizontalDiagonal - 1 + indexDif;
            }
        }

        return -1;
    }

    private static int getDiagonalRightToLeftIndex(int numberOfDiagonal, int toFindSize, int x, int y) {
        var indexSum = x + y;

        if (indexSum >= toFindSize - 1 && indexSum < numberOfDiagonal + (toFindSize - 1)) {
            return indexSum - (toFindSize - 1);
        }

        return -1;
    }
}
