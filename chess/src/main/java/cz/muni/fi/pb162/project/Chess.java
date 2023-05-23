package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.exceptions.InvalidFormatOfInputException;
import cz.muni.fi.pb162.project.exceptions.MissingPlayerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representation of chess
 * @author Alex Popovic
 */
public class Chess extends Game implements GameWritable {
    @Override
    public void write(OutputStream os) throws IOException {
        writeJson(os, this);
    }

    @Override
    public void write(File file) throws IOException {
        try (FileOutputStream os = new FileOutputStream(file)) {
            writeJson(os, this);
        }
    }

    @Override
    public void writeJson(OutputStream os, Object object) throws IOException {
        GameWritable.super.writeJson(os, object);
    }

    /**
     * Builder class
     * @author Alex Popovic
     */
    public static class Builder implements Buildable<Chess>, GameReadable {
        private Player playerOne = null;
        private Player playerTwo = null;
        private final Board board;

        /**
         * Creates new builder
         */
        public Builder() {
            board = new Board();
        }

        @Override
        public Chess build() throws MissingPlayerException {
            if (playerOne == null || playerTwo == null) {
                throw new MissingPlayerException("Not enough players! Expected 2");
            }
            return new Chess(playerOne, playerTwo, board);
        }

        /**
         * Adds new player
         * @param player player to be added
         * @return builder object
         */
        public Builder addPlayer(Player player) {
            if (playerOne == null) {
                playerOne = player;
            } else if (playerTwo == null) {
                playerTwo = player;
            }
            return this;
        }

        /**
         * Adds piece to board
         * @param piece piece to be added
         * @param e column
         * @param i row
         * @return builder object
         */
        public Builder addPieceToBoard(Piece piece, char e, int i) {
            board.putPieceOnBoard(e - 'a', i, piece);
            return this;
        }

        private PieceType parseType(String string) {
            PieceType type;
            try {
                type = PieceType.valueOf(string);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatOfInputException("Invalid format", e);
            }
            return type;
        }

        private Color parseColor(String string) {
            Color color;
            try {
                color = Color.valueOf(string);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatOfInputException("Invalid format", e);
            }
            return color;
        }
        private void parseLines(List<String> lines) {
            int commaIndex;
            PieceType type;
            Color color;
            for (int line = 0; line < lines.size(); line++) {
                String[] tokens = lines.get(line).split(";");
                if (tokens.length != Board.SIZE) {
                    throw new InvalidFormatOfInputException("Expected" + Board.SIZE + "rows, got" + tokens.length);
                }
                for (int token = 0; token < tokens.length; token++) {
                    if (Objects.equals(tokens[token], "_")) {
                        board.putPieceOnBoard(line, token, null);
                    } else if ((commaIndex = tokens[token].indexOf(',')) != -1) {
                        type = parseType(tokens[token].substring(0, commaIndex));
                        color = parseColor(tokens[token].substring(commaIndex + 1));
                        board.putPieceOnBoard(line, token, new Piece(color, type));
                    } else {
                        throw new InvalidFormatOfInputException("Invalid format of piece");
                    }
                }

            }
        }

        private void parseHeader(String header) {
            String[] tokens = header.split(";");
            if (tokens.length != 2) {
                throw new InvalidFormatOfInputException("Invalid format");
            }
            int dashIndex;
            Color color;
            String name;
            for (String token : tokens) {
                if ((dashIndex = token.indexOf('-')) != 1) {
                    name = token.substring(0, dashIndex);
                    color = parseColor(token.substring(dashIndex + 1));
                    if (playerOne == null) {
                        playerOne = new Player(name, color);
                    } else if (playerTwo == null) {
                        playerTwo = new Player(name, color);
                    }
                } else {
                    throw new InvalidFormatOfInputException("Invalid format");
                }
            }
        }

        @Override
        public Builder read(InputStream is) throws IOException {
            return read(is, false);
        }

        @Override
        public Builder read(InputStream is, boolean hasHeader) throws IOException {
            List<String> lines = new ArrayList<>();
            String line;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            if (hasHeader) {
                parseHeader(lines.remove(0));
            }
            if (lines.size() != Board.SIZE) {
                throw new InvalidFormatOfInputException("Expected" + Board.SIZE + "columns, got" + lines.size());
            }
            parseLines(lines);
            return this;
        }

        @Override
        public Builder read(File file) throws IOException {
            try (InputStream is = new FileInputStream(file)) {
                return read(is);
            }
        }

        @Override
        public Builder read(File file, boolean hasHeader) throws IOException {
            try (InputStream is = new FileInputStream(file)) {
                return read(is, hasHeader);
            }
        }
    }

    /**
     * Creates new game
     * @param playerOne first player
     * @param playerTwo second player
     */
    public Chess(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
    }

    /**
     * Creates new game
     * @param playerOne first player
     * @param playerTwo second player
     * @param board board
     */
    public Chess(Player playerOne, Player playerTwo, Board board) {
        super(playerOne, playerTwo, board);
    }

    @Override
    public void updateStatus() {
        Piece[] kings = Arrays.stream(getBoard().getAllPiecesFromBoard())
                .filter(piece -> piece.getPieceType() == PieceType.KING)
                .toArray(Piece[]::new);
        if (kings.length == 2) {
            setStateOfGame(StateOfGame.PLAYING);
        }
        if (kings.length == 1) {
            setStateOfGame(kings[0].getColor() == Color.WHITE ?
                    StateOfGame.WHITE_PLAYER_WIN :
                    StateOfGame.BLACK_PLAYER_WIN);
        }
    }

    @Override
    public void setInitialSet() {
        for (int col = 0; col < Board.SIZE; col++) {
            getBoard().putPieceOnBoard(col, 1, new Piece(Color.WHITE, PieceType.PAWN));
            getBoard().putPieceOnBoard(col, Board.SIZE - 2, new Piece(Color.BLACK, PieceType.PAWN));
            switch (col) {
                case 0, 7 -> {
                    getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.ROOK));
                    getBoard().putPieceOnBoard(col, Board.SIZE - 1, new Piece(Color.BLACK, PieceType.ROOK));
                }
                case 1, 6 -> {
                    getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.KNIGHT));
                    getBoard().putPieceOnBoard(col, Board.SIZE - 1, new Piece(Color.BLACK, PieceType.KNIGHT));
                }
                case 2, 5 -> {
                    getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.BISHOP));
                    getBoard().putPieceOnBoard(col, Board.SIZE - 1, new Piece(Color.BLACK, PieceType.BISHOP));
                }
                case 3 -> {
                    getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.QUEEN));
                    getBoard().putPieceOnBoard(col, Board.SIZE - 1, new Piece(Color.BLACK, PieceType.QUEEN));
                }
                case 4 -> {
                    getBoard().putPieceOnBoard(col, 0, new Piece(Color.WHITE, PieceType.KING));
                    getBoard().putPieceOnBoard(col, Board.SIZE - 1, new Piece(Color.BLACK, PieceType.KING));
                }
                default -> {}
            }
        }
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

    private void promoteIfNeeded(Coordinates position, Piece piece) {
        if (piece == null) {
            return;
        }
        if (piece.getPieceType() != PieceType.PAWN) {
            return;
        }
        if ((position.number() != 0 && position.number() != 7)) {
            return;
        }

        Piece queen = new Piece(piece.getColor(), PieceType.QUEEN);
        putPieceOnBoard(position.letterNumber(), position.number(), queen);
    }
}
