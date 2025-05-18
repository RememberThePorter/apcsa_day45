package RememberThePorter;

import RememberThePorter.Interfaces.Cell;
import RememberThePorter.Interfaces.Grid;
import RememberThePorter.Interfaces.Location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Spreadsheet implements Grid {
    private final Cell[][] CELLS;
    private final String SPREADSHEET_TITLE;
    private final int ROWS;
    private final int COLUMNS;

    public Spreadsheet(String title, int rows, int cols) {
        this.CELLS = new Cell[rows][cols];
        this.SPREADSHEET_TITLE = title;
        this.ROWS = CELLS.length;
        this.COLUMNS = CELLS[0].length;

        populateSheet();
    }

    public String getSpreadsheetTitle() {
        return SPREADSHEET_TITLE;
    }

    @Override
    public String processCommand(String command) {
        String[] commandWords = command.split(" ", 3);
        if(commandWords[0].equalsIgnoreCase("clear")) {
            if(commandWords.length == 1) {
                clearAll();
            } else {
                try {
                    clearCell(commandWords[1]);
                } catch(Exception e) {
                    return "Invalid cell.";
                }
            }
            return getGridText();
        } else if(commandWords[0].equalsIgnoreCase("save")) {
            boolean savedSuccessfully;

            try {
                savedSuccessfully = save(commandWords[1]);
            } catch (Exception e) {
                return "Invalid file name.";
            }

            if(savedSuccessfully) {
                return "Saved successfully!";
            } else {
                return "Save unsuccessful.";
            }
        } else if(commandWords[0].equalsIgnoreCase("open")) {
            try {
                return open(commandWords[1]);
            } catch (Exception e) {
                return "Invalid file name.";
            }
        } else if(commandWords[0].equalsIgnoreCase("help")) {
            if(commandWords.length == 1) {
                return Help.help();
            } else {
                try {
                    return Help.help(commandWords[1]);
                } catch(Exception e) {
                    return "Invalid command.";
                }
            }
        } else if(command.isEmpty()) {
            return "";
        } else {
            if(commandWords.length == 1) {
                if(commandWords[0].equalsIgnoreCase("show")) {
                    return getGridText();
                } else {
                    try {
                        return inspectCell(commandWords[0]);
                    } catch (Exception e) {
                        return "Invalid command.";
                    }
                }
            } else {
                try {
                    return updateCell(commandWords);
                } catch(Exception e) {
                    return "Invalid command.";
                }
            }
        }
    }

    public boolean save(String file) throws IOException {
        boolean savedSuccessfully = false;

        String fullFilePath;

        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("windows")) {
            fullFilePath = getWindowsFilePath(file);
        } else if(os.contains("mac") || os.contains("linux")) {
            fullFilePath = getMacOrLinuxFilePath(file);
        } else {
            return false;
        }

        File fileToSave = new File(fullFilePath);
        if(!fileToSave.exists()) {
            fileToSave.createNewFile();
        }
        PrintWriter writer = new PrintWriter(fullFilePath, StandardCharsets.UTF_8);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                char columnLetter = alphabet.charAt(column);
                int rowAdjustedForZeroIndex = row + 1;

                String type = getCellType(row, column);
                if(!type.equals("EmptyCell")) {
                    String stringToPrint = columnLetter + rowAdjustedForZeroIndex + "," + type + "," + CELLS[row][column].fullCellText();
                    writer.println(stringToPrint);
                }
            }
        }

        String textToSave = "";
        for(int row = 0; row < getRows(); row++) {
            String lineToSave = "";
            for(int column = 0; column < getColumns(); column++) {
                lineToSave = lineToSave + CELLS[row][column].fullCellText() + ",";
            }
            lineToSave = lineToSave.substring(0, lineToSave.length() - 1) + "\n";
            textToSave = textToSave + lineToSave;
        }

        try {
            writer.write(textToSave);
            savedSuccessfully = true;
        } catch(Exception e) {
            savedSuccessfully = false;
        }

        writer.close();
        return savedSuccessfully;
    }

    private String getWindowsFilePath(String file) {
        Scanner scanner = new Scanner(System.in);
        String fullFilePath;

        System.out.print("Where would you like to save the file? Leave blank to save to Downloads. ");
        fullFilePath = scanner.nextLine();
        if(fullFilePath.isEmpty()) {
            fullFilePath = System.getProperty("user.home") + "\\Downloads\\" + file;
        } else {
            if(fullFilePath.endsWith("\\")) {
                fullFilePath = fullFilePath + file;
            } else {
                fullFilePath = fullFilePath + "\\" + file;
            }
        }

        return fullFilePath;
    }

    private String getMacOrLinuxFilePath(String file) {
        Scanner scanner = new Scanner(System.in);
        String fullFilePath;

        System.out.print("Where would you like to save the file? Leave blank to save to Downloads. ");
        fullFilePath = scanner.nextLine();
        if(fullFilePath.isEmpty()) {
            fullFilePath = System.getProperty("user.home") + "/Downloads/" + file;
        } else {
            if(fullFilePath.endsWith("/")) {
                fullFilePath = fullFilePath + file;
            } else {
                fullFilePath = fullFilePath + "/" + file;
            }
        }
        return fullFilePath;
    }

    private String getCellType(int row, int column) {
        String cellType = switch (CELLS[row][column]) {
            case TextCell ignored -> "TextCell";
            case FormulaCell ignored -> "FormulaCell";
            case PercentCell ignored -> "PercentCell";
            case ValueCell ignored -> "ValueCell";
            case EmptyCell ignored -> "EmptyCell";
            case null, default -> "RealCell";
        };

        return cellType;
    }

    public String open(String file) throws IOException {
        clearAll();
        String fileContents = Files.readString(Path.of(file), StandardCharsets.UTF_8);
        String[] contentLines = fileContents.split("\n");
        for(String line : contentLines) {
            String[] contentInformation = line.split(",");
            if(contentInformation[1].equals("PercentCell")) {
                String[] wholeAndDecimal = contentInformation[2].split("\\.", 2);
                String percentFromDecimal;
                if(wholeAndDecimal[1].length() > 2) {
                    int whole = (Integer.parseInt(wholeAndDecimal[0]) * 100) + Integer.parseInt(wholeAndDecimal[1].substring(0, 2));
                    String decimal = wholeAndDecimal[1].substring(2);
                    percentFromDecimal = whole + "." + decimal + "%";
                } else {
                    percentFromDecimal = (Integer.parseInt(wholeAndDecimal[0]) * 100) + wholeAndDecimal[1] + "%";
                }
                if(percentFromDecimal.startsWith("0")) {
                    percentFromDecimal = percentFromDecimal.substring(1);
                }
                processCommand(contentInformation[0] + " = " + percentFromDecimal);
            } else {
                processCommand(contentInformation[0] + " = " + contentInformation[2]);
            }
        }
        return getGridText();
    }

    public void clearAll() {
        for(int row = 0; row < getRows(); row++) {
            for(int column = 0; column < getColumns(); column++) {
                CELLS[row][column] = new EmptyCell();
            }
        }
    }

    public void clearCell(String cell) {
        int[] coordinates = getCellCoordinates(cell);

        CELLS[coordinates[0]][coordinates[1]] = new EmptyCell();
    }

    public String inspectCell(String cell) {
        int[] coordinates = getCellCoordinates(cell);

        return CELLS[coordinates[0]][coordinates[1]].fullCellText();
    }

    public String updateCell(String[] command) {
        if(command[1].equals("=")) {
            int[] coordinates = getCellCoordinates(command[0]);
            if(command[2].endsWith("%")) {
                CELLS[coordinates[0]][coordinates[1]] = new PercentCell(command[2]);
                return getGridText();
            } else if(command[2].startsWith("\"") && command[2].endsWith("\"")) {
                CELLS[coordinates[0]][coordinates[1]] = new TextCell(command[2]);
                return getGridText();
            } else if(command[2].startsWith("(") && command[2].endsWith(")")) {
                CELLS[coordinates[0]][coordinates[1]] = new FormulaCell(command[2]);
                return getGridText();
            } else {
                CELLS[coordinates[0]][coordinates[1]] = new ValueCell(command[2]);
                return getGridText();
            }
        } else {
            return("Error: No equals");
        }
    }

    public int[] getCellCoordinates(String cell) {
        SpreadsheetLocation spreadsheetLocation = new SpreadsheetLocation(cell);
        int row = spreadsheetLocation.getRow();
        int column = spreadsheetLocation.getColumn();

        return new int[] {row, column};
    }

    private int getRows() {
        return ROWS;
    }
    

    private int getColumns() {
        return COLUMNS;
    }

    @Override
    public Cell getCell(Location location) {
        return CELLS[location.getRow()][location.getColumn()];
    }

    @Override
    public String getGridText() {
        String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder grid = new StringBuilder("Current Spreadsheet: " + SPREADSHEET_TITLE + "\n   |");
        for(int column = 1; column <= getColumns(); column ++) {
            grid.append(alphabet.charAt(column) + "         |");
        }
        grid.append("\n");

        for(int row = 0; row < getRows(); row++) {
            grid.append(row + 1);
            if((row + 1) < 10) {
                grid.append(" ");
            }
            grid.append(" |");
            for(int column = 0; column < getColumns(); column++) {
                grid.append(CELLS[row][column].abbreviatedCellText());
                grid.append(" ".repeat(Math.max(0, (10 - CELLS[row][column].abbreviatedCellText().length()))));
                grid.append("|");
            }
            grid.append("\n");
        }
        return grid.toString();
    }

    private void populateSheet() {
        for(int row = 0; row < CELLS.length; row++) {
            for(int column = 0; column < CELLS[0].length; column++) {
                CELLS[row][column] = new EmptyCell();
            }
        }
    }
}