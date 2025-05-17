package RememberThePorter;

public class Help {
    public static String help() {
        return "Here's a list of commands you can use:\n" +
                "help             - Shows this dialogue\n" +
                "help [command]   - Shows help for a specific command\n" +
                "save [file]      - Saves the current spreadsheet to a file\n" +
                "open [file]      - Opens a file\n" +
                "show             - Shows the current state of the spreadsheet\n" +
                "clear            - Clears the entire spreadsheet\n" +
                "clear [cell]     - Clears the specified cell\n" +
                "[cell]           - Shows the contents of the specified cell\n" +
                "[cell] = [value] - Sets the contents of the specified cell to the specified value\n" +
                "\n" +
                "To view more help for the final two commands, use \"help cell\"";
    }

    public static String help(String command) {
        return switch (command) {
            case "help" -> helpHelp();
            case "save" -> helpSave();
            case "open" -> helpOpen();
            case "show" -> helpShow();
            case "clear" -> helpClear();
            case "cell" -> helpCell();
            default -> "That command does not exist.";
        };
    }

    private static String helpHelp() {
        return "Entering \"help\" shows a list of all commands that can be used.\n" +
                "Adding a parameter shows the help dialogue for the command enetered as the parameter (for example, \"help help\" shows this dialogue).";
    }

    private static String helpSave() {
        return "Entering \"save\" followed by a file name saves the current state of the spreadsheet to the indicated file.\n" +
                "Texcel's CSV format is not compatible with Google Sheets or Microsoft Excel.";
    }

    private static String helpOpen() {
        return "Entering \"open\" followed by a file name opens the spreadsheet contained in the indicated file.\n" +
                "Google Sheets' and Microsoft Excel's CSV formats are not compatible with Texcel.";
    }

    private static String helpShow() {
        return "Entering \"show\" prints the current state of the spreadsheet.";
    }

    private static String helpClear() {
        return "Entering \"clear\" deletes all content from the spreadsheet.\n" +
                "Adding a cell name as a parameter deletes the content from the indicated cell only.";
    }

    private static String helpCell() {
        return "Entering a cell name prints the contents of the indicated cell.\n" +
                "Entering a cell name followed by \" = \" and some content changes the contents of the indicated cell to the indicated content. Remember that text input must be in quotes.";
    }
}
