package RememberThePorter;

import RememberThePorter.Interfaces.Location;

public class SpreadsheetLocation implements Location {
    private String loc;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public SpreadsheetLocation(String loc) {
        this.loc = loc;
    }
    @Override
    public int getRow() {
        return Integer.parseInt(loc.substring(1)) - 1;
    }

    @Override
    public int getCol() {
        return Character.toUpperCase(loc.charAt(0)) - 'A';
    }

    public char getColChar(int c) {
        return LETTERS.charAt(c);
    }
}
