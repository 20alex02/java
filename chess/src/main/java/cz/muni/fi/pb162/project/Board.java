package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.utils.BoardNotation;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of chess board
 * @author Alex Popovic
 */
public class Board implements Originator<Memento> {
    public static final int SIZE = 8;
    private int round;
    private Piece[][] squares = new Piece[SIZE][SIZE];

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setSquares(Piece[][] squares) {
        this.squares = squares;
    }

    /**
     * Checks whether the given square is valid
     *
     * @param col number
     * @param row number
     * @return true if piece is within the bounds of board, else false
     */
    public static boolean inRange(int col, int row) {
        return (col >= 0 && col < SIZE) && (row >= 0 && row < SIZE);
    }

    /**
     * Same as {@link #inRange(int, int)}
     */
    public static boolean inRange(Coordinates coordinates) {
        return inRange(coordinates.letterNumber(), coordinates.number());
    }

    /**
     * Checks if square is empty
     *
     * @param col number
     * @param row number
     * @return true if empty, else false
     */
    public boolean isEmpty(int col, int row) {
        return getPiece(col, row) == null;
    }

    /**
     * @param col number
     * @param row number
     * @return piece on given col and row, null if square is empty or
     * not within bounds of board
     */
    public Piece getPiece(int col, int row) {
        if (!inRange(col, row)) {
            return null;
        }
        return squares[col][row];
    }

    /**
     * Same as {@link #getPiece(int, int)}
     *
     * @param coordinates coordinates
     * @return piece at given coordinates
     */
    public Piece getPiece(Coordinates coordinates) {
        return getPiece(coordinates.letterNumber(), coordinates.number());
    }

    /**
     * Puts piece on board at given col and row
     *
     * @param col   number
     * @param row   number
     * @param piece to be put on board
     */
    public void putPieceOnBoard(int col, int row, Piece piece) {
        if (inRange(col, row)) {
            squares[col][row] = piece;
        }
    }

    /**
     * Finds coordinates of piece given by id
     *
     * @param id of piece
     * @return coordinates of square
     */
    public Coordinates findCoordinatesOfPieceById(long id) {
        for (int col = 0; col < squares.length; col++) {
            for (int row = 0; row < squares[col].length; row++) {
                Piece piece = getPiece(col, row);
                if (piece != null && piece.getId() == id) {
                    return new Coordinates(col, row);
                }
            }
        }
        return null;
    }

    public Piece[] getAllPiecesFromBoard() {
        return Arrays.stream(squares)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .toArray(Piece[]::new);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("  ");
        for (int row = 0; row < Board.SIZE; row++) {
            str.append("  ").append(row + 1).append(" ");
        }
        str.append(System.lineSeparator())
                .append("  --------------------------------")
                .append(System.lineSeparator());

        for (int col = 0; col < Board.SIZE; col++) {
            str.append(BoardNotation.getNotationOfCoordinates(col, 0).substring(0, 1).toUpperCase()).append(" |");
            for (int row = 0; row < Board.SIZE; row++) {
                str.append(" ");
                if (squares[col][row] != null) {
                    str.append(squares[col][row]);
                } else {
                    str.append(" ");
                }
                str.append(" |");
            }
            str.append(System.lineSeparator()).append("  --------------------------------");
            if (col != Board.SIZE - 1) {
                str.append(System.lineSeparator());
            }
        }
        return str.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(squares), round);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board board)) {
            return false;
        }
        return round == board.getRound() && Arrays.deepEquals(squares, board.squares);
    }

    @Override
    public Memento save() {
        Piece[][] newSquares = new Piece[SIZE][SIZE];
        for (int col = 0; col < SIZE; col++) {
            System.arraycopy(squares[col], 0, newSquares[col], 0, SIZE);
        }
        return new Memento(newSquares, round);
    }

    @Override
    public void restore(Memento save) {
        round = save.round();
        squares = save.squares();
    }
}
