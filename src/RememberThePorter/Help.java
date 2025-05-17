package RememberThePorter;

public class Help {
    public static String help() {
        return """
                Here's a list of commands you can use:
                help               - Shows this dialogue
                help [command]     - Shows help for a specific command
                new                - Opens dialogue to create a new spreadsheet
                load [spreadsheet] - Loads a previously-created spreadsheet
                save [file]        - Saves the current spreadsheet to a file
                open [file]        - Opens a file
                show               - Shows the current state of the spreadsheet
                clear              - Clears the entire spreadsheet
                clear [cell]       - Clears the specified cell
                [cell]             - Shows the contents of the specified cell
                [cell] = [value]   - Sets the contents of the specified cell to the specified value
                
                To view more help for the final two commands, use "help cell\"""";
    }

    public static String help(String command) {
        return switch(command) {
            case "help" -> helpWithHelpCommand();
            case "save" -> helpWithSaveCommand();
            case "new" -> helpWithNewCommand();
            case "load" -> helpWithLoadCommand();
            case "open" -> helpWithOpenCommand();
            case "show" -> helpWithShowCommand();
            case "clear" -> helpWithClearCommand();
            case "cell" -> helpWithCellCommand();
            default -> "That command does not exist.";
        };
    }

    private static String helpWithHelpCommand() {
        return "Entering \"help\" shows a list of all commands that can be used.\n" +
                "Adding a parameter shows the help dialogue for the command enetered as the parameter (for example, \"help help\" shows this dialogue).";
    }

    private static String helpWithSaveCommand() {
        return "Entering \"save\" followed by a file name saves the current state of the spreadsheet to the indicated file.\n" +
                "Texcel's CSV format is not compatible with Google Sheets or Microsoft Excel.";
    }

    private static String helpWithNewCommand() {
        return "Entering \"new\" opens a dialogue that allows for the creation of a new spreadsheet. You will be prompted to enter a title, as well as the number of rows and columns it should have.\n" +
                "There is a maximum of 999 rows and 26 columns.";
    }

    private static String helpWithLoadCommand() {
        return "Entering \"load\" followed by a spreadsheet title loads the indicated spreadsheet.\n" +
                "The spreadsheet loaded must have been previously created using the \"new\" command.";
    }

    private static String helpWithOpenCommand() {
        return "Entering \"open\" followed by a file name opens the spreadsheet contained in the indicated file.\n" +
                "Google Sheets' and Microsoft Excel's CSV formats are not compatible with Texcel.";
    }

    private static String helpWithShowCommand() {
        return "Entering \"show\" prints the current state of the spreadsheet.";
    }

    private static String helpWithClearCommand() {
        return "Entering \"clear\" deletes all content from the spreadsheet.\n" +
                "Adding a cell name as a parameter deletes the content from the indicated cell only.";
    }

    private static String helpWithCellCommand() {
        return "Entering a cell name prints the contents of the indicated cell.\n" +
                "Entering a cell name followed by \" = \" and some content changes the contents of the indicated cell to the indicated content. Remember that text input must be in quotes.";
    }
}
