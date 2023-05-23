package cz.muni.fi.pb162.hw01.impl.displays;

/**
 * Representation of seven segment display
 * @author Alex Popovic
 */
public class SegmentDisplay implements Display {
    private final int size;
    private final SevenSegmentCell[] cells;

    /**
     * Creates seven segment display with given size
     * @param size size of the display
     */
    public SegmentDisplay(int size) {
        this.size = size;
        cells = new SevenSegmentCell[size];
        for (int cell = 0; cell < size; cell++) {
            cells[cell] = new SevenSegmentCell();
        }
    }

    public int getSize() {
        return size;
    }

    public SevenSegmentCell[] getCells() {
        return cells;
    }

    @Override
    public void set(String text) {
        set(0, text);
    }

    @Override
    public void set(int pos, String text) {
        if (text.length() + pos < size) {
            text += " ".repeat(size - text.length() - pos);
        }
        for (int cell = pos; cell < size; cell++) {
            cells[cell].setEncodedSymbol(text.charAt(cell - pos));
        }
    }

    @Override
    public void clear() {
        for (int cell = 0; cell < size; cell++) {
            cells[cell].setEncodedSymbol(' ');
        }
    }

    @Override
    public void clear(int pos) {
        cells[pos].setEncodedSymbol(' ');
    }
}
