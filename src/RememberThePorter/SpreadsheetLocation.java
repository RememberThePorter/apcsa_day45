package RememberThePorter;

import RememberThePorter.Interfaces.Location;

public class SpreadsheetLocation implements Location {
    private String loc;

    public SpreadsheetLocation(String loc) {
        this.loc = loc;
    }
    @Override
    public int getRow() {
        return Integer.parseInt(loc.substring(1)) - 1;
    }

    @Override
    public int getCol() {
        String col = loc.substring(0, 1);

        return switch (col) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            case "I" -> 8;
            case "J" -> 9;
            case "K" -> 10;
            case "L" -> 11;
            default -> 0;
        };
    }
}
