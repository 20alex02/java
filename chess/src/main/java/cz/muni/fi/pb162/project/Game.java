package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.EmptySquareException;
import cz.muni.fi.pb162.project.exceptions.InvalidFormatOfInputException;
import cz.muni.fi.pb162.project.exceptions.NotAllowedMoveException;
import cz.muni.fi.pb162.project.utils.BoardNotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Representation of board game
 * @author Alex Popovic
 */
public abstract class Game implements Playable {
    private final Player playerOne;
    private final Player playerTwo;
    private final Board board;
    private StateOfGame stateOfGame;
    private final Stack<Memento> mementoHistory = new Stack<>();
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Creates new game
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Game(Player playerOne, Player playerTwo) {
        this(playerOne, playerTwo, new Board());
    }

    /**
     * Creates new game
     * @param playerOne first player
     * @param playerTwo second player
     * @param board board
     */
    public Game(Player playerOne, Player playerTwo, Board board) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;
        this.stateOfGame = StateOfGame.PLAYING;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public StateOfGame getStateOfGame() {
        return stateOfGame;
    }
    public void setStateOfGame(StateOfGame stateOfGame) {
        this.stateOfGame = stateOfGame;
    }

    /**
     * Get current player
     * @return player based on the round number
     */
    public Player getCurrentPlayer() {
        if (playerOne.color() == Color.WHITE) {
            return board.getRound() % 2 == 1 ? playerTwo : playerOne;
        }
        return board.getRound() % 2 == 1 ? playerOne : playerTwo;
    }

    /**
     * Puts piece on board
     * @param letterNumber number of column
     * @param number number of row
     * @param piece piece to be put on the board
     */
    public void putPieceOnBoard(int letterNumber, int number, Piece piece) {
        board.putPieceOnBoard(letterNumber, number, piece);
    }

    /**
     * Updates status of the game
     */
    abstract void updateStatus();

    /**
     * Gets input from player in form of chess notation
     * @return coordinates
     */
    private Coordinates getInputFromPlayer() {
        var position = SCANNER.next().trim();
        if (position.length() != 2) {
            throw new InvalidFormatOfInputException("Invalid length! Expected 2");
        }
        var letterNumber = position.charAt(0);
        if (letterNumber < 'a' || letterNumber > 'h') {
            throw new InvalidFormatOfInputException("Invalid letter! Expected [a-h]");
        }
        int number;
        try {
            number = Integer.parseInt(String.valueOf(position.charAt(1)));
        } catch (NumberFormatException e) {
            throw new InvalidFormatOfInputException(e);
        }
        if (number < 1 || number > 8) {
            throw new InvalidFormatOfInputException("Invalid number! Expected [1-8]");
        }
        return BoardNotation.getCoordinatesOfNotation(letterNumber, number);
    }

    @Override
    public void move(Coordinates oldPosition, Coordinates newPosition) {
        var piece = getBoard().getPiece(oldPosition);
        if (piece == null) {
            return;
        }
        putPieceOnBoard(newPosition.letterNumber(), newPosition.number(), piece);
        putPieceOnBoard(oldPosition.letterNumber(), oldPosition.number(), null);
    }

    private void validateMove(Coordinates oldPosition, Coordinates newPosition)
            throws EmptySquareException, NotAllowedMoveException {
        var piece = getBoard().getPiece(oldPosition);
        if (piece == null) {
            throw new EmptySquareException();
        }
        if (!piece.getAllPossibleMoves(this).contains(newPosition)) {
            throw new NotAllowedMoveException();
        }
    }

    @Override
    public void play() throws EmptySquareException, NotAllowedMoveException {
        while (stateOfGame == StateOfGame.PLAYING) {
            Coordinates oldPosition = getInputFromPlayer();
            Coordinates newPosition = getInputFromPlayer();
            validateMove(oldPosition, newPosition);
            move(oldPosition, newPosition);
            board.setRound(board.getRound() + 1);
            updateStatus();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOne, playerTwo, board, stateOfGame);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game game)) {
            return false;
        }
        return Objects.equals(playerOne, game.getPlayerOne()) &&
                Objects.equals(playerTwo, game.getPlayerTwo()) &&
                Objects.equals(stateOfGame, game.getStateOfGame()) &&
                Objects.equals(board, game.getBoard());
    }

    @Override
    public void hitSave() {
        mementoHistory.push(board.save());
    }

    @Override
    public void hitUndo() {
        if (mementoHistory.isEmpty()) {
            return;
        }
        Memento memento = mementoHistory.pop();
        board.setRound(memento.round());
        board.setSquares(memento.squares());
    }

    public Collection<Memento> getMementoHistory() {
        return Collections.unmodifiableCollection(mementoHistory);
    }

    /**
     * Finds all possible moves for current player
     * @return sorted set of moves in reversed order
     */
    public Set<Coordinates> allPossibleMovesByCurrentPlayer() {
        Supplier<TreeSet<Coordinates>> moves = () -> new TreeSet<>(Comparator.reverseOrder());
        return Arrays.stream(board.getAllPiecesFromBoard())
                .filter(piece -> piece.getColor() == getCurrentPlayer().color())
                .map(piece -> piece.getAllPossibleMoves(this))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(moves));
    }
}
