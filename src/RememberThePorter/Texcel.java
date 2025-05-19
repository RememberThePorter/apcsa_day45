package RememberThePorter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Texcel {
    public static ArrayList<Spreadsheet> sheets;
    public static Spreadsheet currentSheet;

    public static void main(String[] args) {
        sheets = new ArrayList<>();
        sheets.add(new Spreadsheet("Untitled Spreadsheet", 20, 12));
        currentSheet = sheets.getFirst();

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String inputtedCommand = scanner.nextLine();
            if(inputtedCommand.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println(processCommand(inputtedCommand));
            }
        }
        scanner.close();
    }

    public static String processCommand(String commandInputted) {
        if(commandInputted.equalsIgnoreCase("new")) {
            return newSheet().getGridText();
        } else {
            return currentSheet.processCommand(commandInputted);
        }
    }

    private static Spreadsheet newSheet() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter spreadsheet title: ");
        String spreadsheetTitle = scanner.nextLine();
        while(spreadsheetTitle.isEmpty()) {
            System.out.print("Title can't be blank! Enter a title: ");
            spreadsheetTitle = scanner.nextLine();
        }

        System.out.print("Enter number of rows for spreadsheet: ");
        int rows = scanValidNumber();

        System.out.print("Enter number of columns to use for spreadsheet: ");
        int columns = scanValidNumber();

        sheets.add(new Spreadsheet(spreadsheetTitle, rows, columns));
        currentSheet = sheets.getLast();
        return sheets.getLast();
    }

    public static Spreadsheet newSheet(String spreadsheetTitle, int rows, int columns) {
        sheets.add(new Spreadsheet(spreadsheetTitle, rows, columns));
        currentSheet = sheets.getLast();
        return sheets.getLast();
    }

    private static int scanValidNumber() {
        Scanner scanner = new Scanner(System.in);

        int number;
        try {
            number = scanner.nextInt();
        } catch(InputMismatchException e) {
            System.out.print("You must enter a valid number! Enter a valid number: ");
            number = scanValidNumber();
        }
        if(number <= 0) {
            System.out.print("Number cannot be less than one! Enter a number of one or higher :");
            number = scanValidNumber();
        }
        return number;
    }
}
