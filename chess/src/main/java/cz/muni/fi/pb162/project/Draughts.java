package cz.muni.fi.pb162.project;

import java.util.Arrays;

/**
 * Draughts representation
 * @author Alex Popovic
 */
public class Draughts extends Game {
    /**
     * Creates new game
     *
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Draughts(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
    }

    @Override
    public void updateStatus() {
        if (Arrays.stream(getBoard().getAllPiecesFromBoard()).noneMatch(piece -> piece.getColor() == Color.WHITE)) {
            setStateOfGame(StateOfGame.BLACK_PLAYER_WIN);
        }
        if (Arrays.stream(getBoard().getAllPiecesFromBoard()).noneMatch(piece -> piece.getColor() == Color.BLACK)) {
            setStateOfGame(StateOfGame.WHITE_PLAYER_WIN);
        }
    }

    @Override
    public void setInitialSet() {
        for (int col = 0; col < Board.SIZE; col++) {
            if (col % 2 == 0) {
                getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.DRAUGHTS_MAN));
                getBoard().putPieceOnBoard(col, 2, new Piece(Color.WHITE, PieceType.DRAUGHTS_MAN));
                getBoard().putPieceOnBoard(col, 6, new Piece(Color.BLACK, PieceType.DRAUGHTS_MAN));
            } else {
                getBoard().putPieceOnBoard(col, 1, new Piece(Color.WHITE, PieceType.DRAUGHTS_MAN));
                getBoard().putPieceOnBoard(col, 5, new Piece(Color.BLACK, PieceType.DRAUGHTS_MAN));
                getBoard().putPieceOnBoard(col, 7, new Piece(Color.BLACK, PieceType.DRAUGHTS_MAN));
            }
        }
    }

    private void promoteIfNeeded(Coordinates position, Piece piece) {
        if (piece.getPieceType() != PieceType.DRAUGHTS_MAN) {
            return;
        }
        if ((position.number() != 0 && position.number() != 7)) {
            return;
        }

        Piece queen = new Piece(piece.getColor(), PieceType.DRAUGHTS_KING);
        putPieceOnBoard(position.letterNumber(), position.number(), queen);
    }

    @Override
    public void move(Coordinates oldPosition, Coordinates newPosition) {
        var piece = getBoard().getPiece(oldPosition);
        if (piece == null) {
            return;
        }
        super.move(oldPosition, newPosition);
        promoteIfNeeded(newPosition, getBoard().getPiece(newPosition));
    }
}
