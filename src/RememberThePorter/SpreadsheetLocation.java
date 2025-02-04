package RememberThePorter;

import RememberThePorter.Interfaces.Location;

public class SpreadsheetLocation implements Location {
    private String loc;

    public SpreadsheetLocation(String loc) {
        this.loc = loc;
    }
    @Override
    public int getRow() {
        return Integer.parseInt(loc.substring(1));
    }

    @Override
    public int getCol() {
        String col = loc.substring(0, 1);
        int colNum;
        switch(col) {
            case "A": colNum = 1;
            case "B": colNum = 2;
            case "C": colNum = 3;
            case "D": colNum = 4;
            case "E": colNum = 5;
            case "F": colNum = 6;
            case "G": colNum = 7;
            case "H": colNum = 8;
            case "I": colNum = 9;
            case "J": colNum = 10;
            case "K": colNum = 11;
            case "L": colNum = 12;
            default: colNum = 0;
        }

        return colNum;
    }
}
