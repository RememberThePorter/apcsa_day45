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
        Spreadsheet sheet = sheets.getFirst();
        currentSheet = sheet;

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println(sheet.processCommand(input));
            }
        }
        scanner.close();
    }

    public static void processCommand(String command) {
        if(command.equalsIgnoreCase("new")) {
            newSheet();
        } else {
            System.out.println(currentSheet.processCommand(command));
        }
    }

    private static void newSheet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter spreadsheet title: ");
        String title = scanner.nextLine();
        while(title.isEmpty()) {
            System.out.println("Title can't be blank! Enter a title: ");
            title = scanner.nextLine();
        }

        System.out.println("Enter number of rows for spreadsheet: ");
        int rows = scanValidNum();

        System.out.print("Enter number of columns to use for spreadsheet: ");
        int cols = scanValidNum();

        sheets.add(new Spreadsheet(title, rows, cols));
    }

    private static int scanValidNum() {
        Scanner scanner = new Scanner(System.in);

        int num;
        try {
            num = scanner.nextInt();
        } catch(InputMismatchException e) {
            System.out.print("You must enter a valid number! Enter a valid number: ");
            num = scanValidNum();
        }
        return num;
    }
}
