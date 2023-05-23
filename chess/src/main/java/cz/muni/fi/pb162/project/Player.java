package cz.muni.fi.pb162.project;

/**
 * Representation of player
 * @author Alex Popovic
 * @param name player's name
 * @param color player's color
 */
public record Player(String name, Color color) {
    @Override
    public String toString() {
        return name + "-" + color;
    }
}
