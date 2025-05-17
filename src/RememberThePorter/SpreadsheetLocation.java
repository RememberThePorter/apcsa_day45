package RememberThePorter;

import RememberThePorter.Interfaces.Location;

public class SpreadsheetLocation implements Location {
    private final String LOCATION;

    public SpreadsheetLocation(String location) {
        this.LOCATION = location;
    }

    @Override
    public int getRow() {
        return Integer.parseInt(LOCATION.substring(1)) - 1;
    }

    @Override
    public int getColumn() {
        return Character.toUpperCase(LOCATION.charAt(0)) - 'A';
    }
}
