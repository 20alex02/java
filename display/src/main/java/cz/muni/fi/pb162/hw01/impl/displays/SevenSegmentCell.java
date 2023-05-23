package cz.muni.fi.pb162.hw01.impl.displays;

public class SevenSegmentCell {
    private byte encodedSymbol = 0;

    public byte getEncodedSymbol() {
        return encodedSymbol;
    }

    /**
     * Encodes symbol to seven segment representation
     * @param symbol symbol to be encoded
     */
    public void setEncodedSymbol(char symbol) {
        switch (symbol) {
            case '0' -> encodedSymbol = 0x7E;
            case '1' -> encodedSymbol = 0x30;
            case '2' -> encodedSymbol = 0x6D;
            case '3' -> encodedSymbol = 0x79;
            case '4' -> encodedSymbol = 0x33;
            case '5' -> encodedSymbol = 0x5B;
            case '6' -> encodedSymbol = 0x1F;
            case '7' -> encodedSymbol = 0x70;
            case '8' -> encodedSymbol = 0x7F;
            case '9' -> encodedSymbol = 0x73;
            case ' ' -> encodedSymbol = 0x00;
            default -> encodedSymbol = 0x4F;
        }
    }
}
