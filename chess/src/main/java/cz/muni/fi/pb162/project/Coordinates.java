package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.utils.BoardNotation;

/**
 * Coordinates record
 * @param letterNumber of row
 * @param number of column
 * @author Alex Popovic
 */
public record Coordinates(int letterNumber, int number) implements Comparable<Coordinates> {

    /**
     * Computes average of coordinates
     * @return average of coordinates
     */
    public double averageOfCoordinates() {
        return (letterNumber + number) / 2.0;
    }

    /**
     * Adds coordinates by summing their parameters
     * @param other coordinates
     * @return this coordinates with modified parameters
     */
    public Coordinates add(Coordinates other) {
        return new Coordinates(letterNumber + other.letterNumber, number + other.number);
    }

    @Override
    public String toString() {
        return BoardNotation.getNotationOfCoordinates(letterNumber, number);
    }
    @Override
    public int compareTo(Coordinates o) {
        int letterDifference = letterNumber - o.letterNumber;
        return letterDifference == 0 ? number - o.number : letterDifference;
    }
}
