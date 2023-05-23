package cz.muni.fi.pb162.project;

/**
 * @author Alex Popovic
 * @param squares squares of board
 * @param round round of game
 */
public record Memento(Piece[][] squares, int round) {
}
