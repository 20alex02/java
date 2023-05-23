package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.moves.Diagonal;
import cz.muni.fi.pb162.project.moves.Jump;
import cz.muni.fi.pb162.project.moves.Knight;
import cz.muni.fi.pb162.project.moves.Move;
import cz.muni.fi.pb162.project.moves.Pawn;
import cz.muni.fi.pb162.project.moves.Straight;

import java.util.List;
import java.util.Map;

/**
 * Enumeration of game pieces
 * @author Alex Popovic
 */
public enum PieceType {
    KING(List.of(new Straight(1), new Diagonal(1))),
    QUEEN(List.of(new Straight(), new Diagonal())),
    BISHOP(List.of(new Diagonal())),
    KNIGHT(List.of(new Knight())),
    ROOK(List.of(new Straight())),
    PAWN(List.of(new Pawn())),
    DRAUGHTS_KING(List.of(new Diagonal(1), new Jump())),
    DRAUGHTS_MAN(List.of(new Diagonal(1, true), new Jump(true))),
    ;

    private final List<Move> pieceMoves;
    PieceType(List<Move> moves) {
        this.pieceMoves = moves;
    }

    public List<Move> getPieceMoves() {
        return pieceMoves;
    }

    /**
     * @author Alex Popovic
     * @param type piece type
     * @param color piece color
     */
    private record Colored(PieceType type, Color color) {}

    private static Map.Entry<Colored, String> bindSymbol(PieceType type, Color color, String symbol) {
        return Map.entry(new Colored(type, color), symbol);
    }
    public static final Map<Colored, String> SYMBOLS = Map.ofEntries(
        bindSymbol(KING, Color.WHITE, "♔"),
        bindSymbol(QUEEN, Color.WHITE, "♕"),
        bindSymbol(BISHOP, Color.WHITE, "♗"),
        bindSymbol(ROOK, Color.WHITE, "♖"),
        bindSymbol(KNIGHT, Color.WHITE, "♘"),
        bindSymbol(PAWN, Color.WHITE, "♙"),
        bindSymbol(KING, Color.BLACK, "♚"),
        bindSymbol(QUEEN, Color.BLACK, "♛"),
        bindSymbol(BISHOP, Color.BLACK, "♝"),
        bindSymbol(ROOK, Color.BLACK, "♜"),
        bindSymbol(KNIGHT, Color.BLACK, "♞"),
        bindSymbol(PAWN, Color.BLACK, "♟"),
        bindSymbol(DRAUGHTS_MAN, Color.WHITE, "⛀"),
        bindSymbol(DRAUGHTS_KING, Color.WHITE, "⛁"),
        bindSymbol(DRAUGHTS_MAN, Color.BLACK, "⛂"),
        bindSymbol(DRAUGHTS_KING, Color.BLACK, "⛃")
    );

    /**
     * Get string representation of the piece
     * @param color color of the piece
     * @return string representation of the piece
     */
    public String getSymbols(Color color) {
        return SYMBOLS.get(new Colored(this, color));
    }
}
