package RememberThePorter;

import RememberThePorter.Interfaces.Cell;
import RememberThePorter.Interfaces.Grid;
import RememberThePorter.Interfaces.Location;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
        if(split[0].equalsIgnoreCase("clear")) {
            if(split.length == 1) {
                clearAll();
            } else {
                clearCell(split[1]);
            }
            return getGridText();
        } else if(split[0].equalsIgnoreCase("save")) {
            try {
                save(split[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "Saved successfully!";
        } else if(command.isEmpty()) {
            return "";
        } else {
            if(split.length == 1) {
                return inspectCell(split[0]);
            } else {
                return updateCell(split);
            }
        }
    }

    public void save(String file) throws IOException {
        if(System.getProperty("os.name").equals("Linux")) {
            String fullFilePath = System.getProperty("user.home") + "/Downloads/" + file;
            File testFile = new File(fullFilePath);
            if(!testFile.exists()) {
                testFile.createNewFile();
            }
            PrintWriter writer = new PrintWriter(fullFilePath, StandardCharsets.UTF_8);

            for(int row = 0; row < getRows(); row++) {
                for (int col = 0; col < getCols(); col++) {
                    String colLetter = switch (col) {
                        case 0 -> "A";
                        case 1 -> "B";
                        case 2 -> "C";
                        case 3 -> "D";
                        case 4 -> "E";
                        case 5 -> "F";
                        case 6 -> "G";
                        case 7 -> "H";
                        case 8 -> "I";
                        case 9 -> "J";
                        case 10 -> "K";
                        case 11 -> "L";
                        default -> throw new IllegalStateException("Unexpected value: " + col);
                    };
                    int rowAdjusted = row + 1;

                    String type = getType(row, col);
                    if(!type.equals("EmptyCell")) {
                        String toPrint = colLetter + rowAdjusted + "," + type + "," + cells[row][col].fullCellText();
                        writer.println(toPrint);
                    }
                }
            }
            writer.close();
        }
    }

    private String getType(int row, int col) {
        String type;

        if(cells[row][col] instanceof TextCell) {
            type = "TextCell";
        } else if(cells[row][col] instanceof FormulaCell) {
            type = "FormulaCell";
        } else if(cells[row][col] instanceof PercentCell) {
            type = "PercentCell";
        } else if(cells[row][col] instanceof ValueCell) {
            type = "ValueCell";
        } else if(cells[row][col] instanceof EmptyCell) {
            type = "EmptyCell";
        } else {
            type = "RealCell";
        }
        return type;
    }

    public void open(String file) {

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
            if(command[2].endsWith("%")) {
                cells[coords[0]][coords[1]] = new PercentCell(command[2]);
                return getGridText();
            } else if(command[2].startsWith("\"") && command[2].endsWith("\"")) {
                cells[coords[0]][coords[1]] = new TextCell(command[2]);
                return getGridText();
            } else if(command[2].startsWith("(") && command[2].endsWith(")")) {
                cells[coords[0]][coords[1]] = new FormulaCell(command[2]);
                return getGridText();
            } else {
                cells[coords[0]][coords[1]] = new ValueCell(command[2]);
                return getGridText();
            }
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
        return cells[loc.getRow()][loc.getCol()];
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
