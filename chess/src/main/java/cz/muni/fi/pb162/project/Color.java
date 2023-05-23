package cz.muni.fi.pb162.project;

/**
 * Enumeration of piece colors
 * @author Alex Popovic
 */
public enum Color {
    WHITE,
    BLACK,
    ;

    /**
     * Get opposite color
     * @return opposite color
     */
    public Color getOppositeColor() {
        if (this.equals(BLACK)) {
            return WHITE;
        }
        return BLACK;
    }
}
