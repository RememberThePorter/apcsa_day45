package RememberThePorter;

import java.util.Scanner;

public class Texcel {
    public static void main(String[] args) {
        Spreadsheet sheet = new Spreadsheet();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println(sheet.processCommand(input));
            }
        }
    }
}
