package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.EmptySquareException;
import cz.muni.fi.pb162.project.exceptions.NotAllowedMoveException;

/**
 * Interface for any playable object
 * @author Alex Popovic
 */
public interface Playable extends Caretaker {
    /**
     * Sets initial set of playable object
     */
    void setInitialSet();

    /**
     * Performs a move with given coordinates
     * @param first coordinates
     * @param second coordinates
     */
    void move(Coordinates first, Coordinates second);

    /**
     * Plays the game of playable object until the end of the game
     */
    void play() throws EmptySquareException, NotAllowedMoveException;
}
