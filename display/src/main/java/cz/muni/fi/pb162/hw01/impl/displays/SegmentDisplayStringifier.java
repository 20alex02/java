package cz.muni.fi.pb162.hw01.impl.displays;

import java.util.Arrays;

/**
 * Stringifier for seven segment display
 * @author Alex Popovic
 */
public class SegmentDisplayStringifier implements DisplayStringifier{

    @Override
    public boolean canStringify(Display display) {
        return display instanceof SegmentDisplay;
    }

    @Override
    public String[] asLines(Display display) {
        if (!canStringify(display)) {
            return null;
        }
        SegmentDisplay segmentDisplay = (SegmentDisplay) display;
        String[] lines = new String[3];
        Arrays.fill(lines, "");
        for (int cell = 0; cell < segmentDisplay.getSize(); cell++) {
            symbolToString(segmentDisplay.getCells()[cell].getEncodedSymbol(), lines);
        }
        return lines;
    }

    private void symbolToString(byte symbol, String[] lines) {
        String bits = String.format("%7s", Integer.toBinaryString(symbol)).replace(' ', '0');
        lines[0] += " " + symbolToChar(bits, 0) + " ";
        lines[1] += symbolToChar(bits, 5) + symbolToChar(bits, 6) + symbolToChar(bits, 1);
        lines[2] += symbolToChar(bits, 4) + symbolToChar(bits, 3) + symbolToChar(bits, 2);
    }

    private String symbolToChar(String bits, int segment) {
        if (bits.charAt(segment) == '0') {
            return " ";
        }
        if (segment % 3 == 0) {
            return "_";
        }
        return "|";
    }
}
