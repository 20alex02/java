package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.Coordinates;

/**
 * Board notation utility
 * @author Alex Popovic
 */
public final class BoardNotation {
    private static final char REFERENCE_LETTER = 'a';

    private BoardNotation() {
    }

    /**
     * Transforms coordinates to notation
     * @param letterNumber of column
     * @param number of row
     * @return notation for given coords
     */
    public static String getNotationOfCoordinates(int letterNumber, int number) {
        char letter = (char) (REFERENCE_LETTER + letterNumber);
        number++;
        return letter + "" + number;
    }

    /**
     * Transforms notation to coordinates
     * @param letter of column
     * @param number of row
     * @return coordinates for giver notation
     */
    public static Coordinates getCoordinatesOfNotation(char letter, int number) {
        return new Coordinates(letter - REFERENCE_LETTER, number - 1);
    }
}
