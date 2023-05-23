package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.moves.Move;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toSet;

/**
 * Representation of chess piece
 * @author Alex Popovic
 */
public class Piece implements Prototype {
    private final long id;
    private static final AtomicLong COUNTER = new AtomicLong();
    private final Color color;
    private final PieceType pieceType;
    private final List<Move> moves;

    /**
     * Creates new piece with unique id and increments counter
     * @param color of the piece
     * @param pieceType type of the piece
     */
    public Piece(Color color, PieceType pieceType) {
        id = COUNTER.getAndIncrement();
        this.color = color;
        this.pieceType = pieceType;
        moves = pieceType.getPieceMoves();
    }

    /**
     * Get all possible moves for piece in given game
     * @param game current game
     * @return set of possible moves
     */
    public Set<Coordinates> getAllPossibleMoves(Game game) {
        var currentPosition = game.getBoard().findCoordinatesOfPieceById(id);
        return getMoves().stream()
                .map(moveType -> moveType.getAllowedMoves(game, currentPosition))
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    public List<Move> getMoves() {
        return moves;
    }

    public long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Piece makeClone() {
        return new Piece(color, pieceType);
    }

    @Override
    public String toString() {
        return pieceType.getSymbols(color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece && id == ((Piece) obj).getId();
    }
}
