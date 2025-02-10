package RememberThePorter;

import RememberThePorter.Interfaces.Cell;
import RememberThePorter.Interfaces.Grid;
import RememberThePorter.Interfaces.Location;

import java.util.Arrays;

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
        String[] split = command.split(" ", 3);
        if(split[0].equals("clear")) {
            if(split.length == 1) {
                clearAll();
            } else {
                clearCell(split[1]);
            }
            return getGridText();
        } else if(command.isEmpty()) {
            return "";
        } else {
            if(split.length == 1) {
                return inspectCell(split[0]);
            } else {
                System.out.println(Arrays.toString(split));
                return updateCell(split);
            }
        }
    }

    public void clearAll() {
        for(int row = 0; row < getRows(); row++) {
            for(int col = 0; col < getCols(); col++) {
                cells[row][col] = new EmptyCell();
            }
        }
    }

    public void clearCell(String loc) {
        int[] coords = getCoordinates(loc);

        cells[coords[0]][coords[1]] = new EmptyCell();
    }

    public String inspectCell(String loc) {
        int[] coords = getCoordinates(loc);

        return cells[coords[0]][coords[1]].fullCellText();
    }

    public String updateCell(String[] command) {
        if(command[1].equals("=")) {
            int[] coords = getCoordinates(command[0]);
            if(command[2].startsWith("\"") && command[2].endsWith("\"")) {
                command[2] = command[2].substring(1, command[2].length() - 1);
            }
            System.out.println(command[0] + ", " + command[1] + ", " + command[2]);
            cells[coords[0]][coords[1]] = new TextCell(command[2]);
            System.out.println(cells[coords[0]][coords[1]].fullCellText());
            System.out.println(cells[coords[0]][coords[1]].abbreviatedCellText());
            return getGridText();
        } else {
            return("Error: No equals");
        }
    }

    public int[] getCoordinates(String loc) {
        SpreadsheetLocation sheetloc = new SpreadsheetLocation(loc);
        int row = sheetloc.getRow();
        int col = sheetloc.getCol();

        return new int[] {row, col};
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
        StringBuilder output = new StringBuilder("   |A         |B         |C         |D         |E         |F         |G         |H         |I         |J         |K         |L         |\n");
        for(int row = 0; row < getRows(); row++) {
            output.append(row + 1);
            if((row + 1) < 10) {
                output.append(" ");
            }
            output.append(" |");
            for(int col = 0; col < getCols(); col++) {
                output.append(cells[row][col].abbreviatedCellText());
                output.append(" ".repeat(Math.max(0, (10 - cells[row][col].abbreviatedCellText().length()))));
                output.append("|");
            }
            output.append("\n");
        }
        return output.toString();
    }
}