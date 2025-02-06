package RememberThePorter;

import RememberThePorter.Interfaces.Cell;
import RememberThePorter.Interfaces.Grid;
import RememberThePorter.Interfaces.Location;

public class Spreadsheet implements Grid {
    private Cell[][] cells;

    public Spreadsheet() {
        this.cells = new Cell[20][12];

        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new EmptyCell();
            }
        }
    }

    @Override
    public String processCommand(String command) {
        return "";
    }

    @Override
    public int getRows() {
        return 20;
    }

    @Override
    public int getCols() {
        return 12;
    }

    @Override
    public Cell getCell(Location loc) {
        return cells[loc.getCol()][loc.getRow()];
    }

    @Override
    public String getGridText() {
        String output = "   |A         |B         |C         |D         |E         |F         |G         |H         |I         |J         |K         |L         |\n";
        for(int row = 0; row < getRows(); row++) {
            output += (row + 1);
            if((row + 1) < 10) {
                output += " ";
            }
            output += " |";
            for(int col = 0; col < getCols(); col++) {
                output += cells[row][col].abbreviatedCellText();
                for(int spaces = 0; spaces < (10 - cells[row][col].abbreviatedCellText().length()); spaces++) {
                    output += " ";
                }
                output += "|";
            }
            output += "\n";
        }
        return output;
    }
}