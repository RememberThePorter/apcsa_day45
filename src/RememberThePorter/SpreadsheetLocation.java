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
        int colNum;
        switch(col) {
            case "A": colNum = 0; break;
            case "B": colNum = 1; break;
            case "C": colNum = 2; break;
            case "D": colNum = 3; break;
            case "E": colNum = 4; break;
            case "F": colNum = 5; break;
            case "G": colNum = 6; break;
            case "H": colNum = 7; break;
            case "I": colNum = 8; break;
            case "J": colNum = 9; break;
            case "K": colNum = 10; break;
            case "L": colNum = 11; break;
            default: colNum = 0; break;
        }

        return colNum;
    }
}
