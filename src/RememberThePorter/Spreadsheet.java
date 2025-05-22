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
    private String SPREADSHEET_TITLE;
    private final int ROWS;
    private final int COLUMNS;
    private String lastSavedName = "";
    private String lastSavedPath = "";

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

    protected String updateSpreadsheetTitle(String newName) {
        SPREADSHEET_TITLE = newName;
        return "Spreadsheet renamed to: " + SPREADSHEET_TITLE;
    }

    @Override
    public String processCommand(String command) {
        String[] commandWords = command.split(" ", 3);
        if(commandWords[0].equalsIgnoreCase("clear")) {
            if (commandWords.length == 1) {
                clearAll();
            } else {
                try {
                    clearCell(commandWords[1]);
                } catch (Exception e) {
                    return "Invalid cell.";
                }
            }
            return getGridText();
        } else if(command.equalsIgnoreCase("save")) {
            boolean savedSuccessfully;

            try {
                savedSuccessfully = save();
            } catch(Exception e) {
                return e.toString();
            }

            if(savedSuccessfully) {
                return "Saved successfully as " + lastSavedName + " at " + lastSavedPath + "!";
            } else {
                return "Save unsuccessful.";
            }
        } else if(commandWords[0].equalsIgnoreCase("save")) {
            boolean savedSuccessfully;

            try {
                savedSuccessfully = save(commandWords[1]);
            } catch (Exception e) {
                return e.toString();
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
                return "An error occurred while opening the file.";
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

    public boolean save() throws IOException {
        boolean savedSuccessfully;
        if(!lastSavedName.isEmpty() && !lastSavedPath.isEmpty()) {
            File fileToSave = new File(lastSavedPath);
            if(!fileToSave.exists()) {
                fileToSave.createNewFile();
            }

            String textToSave = getTextToSave();

            savedSuccessfully = writeToFile(lastSavedPath, textToSave);
        } else {
            throw new IOException("This file has not yet been saved, and therefore a file name must be specified. Use \"help save\" for more information.");
        }
        return savedSuccessfully;
    }

    public boolean save(String file) throws IOException {
        boolean savedSuccessfully;
        String fullFilePath;

        if(!file.endsWith(".csv")) {
            file = file + ".csv";
        }

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

        String textToSave = getTextToSave();

        savedSuccessfully = writeToFile(fullFilePath, textToSave);

        if(savedSuccessfully) {
            this.lastSavedName = file;
            this.lastSavedPath = fullFilePath;
        }

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

    private String getTextToSave() {
        StringBuilder textToSave = new StringBuilder();
        for(int row = 0; row < getRows(); row++) {
            StringBuilder lineToSave = new StringBuilder();
            for(int column = 0; column < getColumns(); column++) {
                String cellToSave = CELLS[row][column].fullCellText();
                if(cellToSave.startsWith("\"")) {
                    cellToSave = "\"\"" + cellToSave;
                }
                if(cellToSave.endsWith("\"")) {
                    cellToSave = cellToSave + "\"\"";
                }
                if(cellToSave.contains(",")) {
                    if(!cellToSave.startsWith("\"")) {
                        cellToSave = "\"" + cellToSave;
                    }
                    if(!cellToSave.endsWith("\"")) {
                        cellToSave = cellToSave + "\"";
                    }
                }
                lineToSave.append(cellToSave).append(", ");
            }
            lineToSave = new StringBuilder(lineToSave.substring(0, lineToSave.length() - 1) + "\n");
            textToSave.append(lineToSave);
        }
        return textToSave.toString();
    }

    private boolean writeToFile(String fileToWriteTo, String textToWrite) throws IOException {
        PrintWriter writer = new PrintWriter(fileToWriteTo, StandardCharsets.UTF_8);
        try {
            writer.write(textToWrite);
            writer.close();
            return true;
        } catch(Exception e) {
            writer.close();
            return false;
        }
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
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String fileNameWithExtension = Path.of(file).getFileName().toString();
        String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4);

        String fileContents = Files.readString(Path.of(file), StandardCharsets.UTF_8);
        String[] contentLines = fileContents.split("\n");
        String[] lineOne = contentLines[0].split(",");
        Spreadsheet sheet = Texcel.newSheet(fileNameWithoutExtension, contentLines.length, lineOne.length);

        String[][] cellContents = new String[contentLines.length][lineOne.length];
        for(int line = 0; line < contentLines.length; line++) {
            String[] thisLine = contentLines[line].split(",");
            for(int cell = 0; cell < thisLine.length; cell++) {
                cellContents[line][cell] = thisLine[cell];
                while(cellContents[line][cell].startsWith(" ") && cellContents[line][cell].length() > 1) {
                    cellContents[line][cell] = cellContents[line][cell].substring(1);
                }
            }
        }

        for(int column = 0; column < cellContents[0].length; column++) {
            for(int row = 0; row < cellContents.length; row++) {
                String cell = alphabet.substring(column, column + 1) + (row + 1);
                if(!cellContents[row][column].equals(" ") && !cellContents[row][column].isEmpty()) {
                    sheet.processCommand(cell + " = " + cellContents[row][column]);
                }
            }
        }

        return sheet.getGridText();
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
            } else if(command[2].startsWith("(") && command[2].endsWith(")")) {
                CELLS[coordinates[0]][coordinates[1]] = new FormulaCell(command[2]);
                return getGridText();
            } else if(command[2].matches("[0-9]+(\\.[0-9]+)?")) {
                CELLS[coordinates[0]][coordinates[1]] = new ValueCell(command[2]);
                return getGridText();
            } else {
                CELLS[coordinates[0]][coordinates[1]] = new TextCell(command[2]);
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
        for(int column = 1; column <= getColumns(); column++) {
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