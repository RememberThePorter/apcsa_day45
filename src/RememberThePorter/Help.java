package RememberThePorter;

public class Help {
    public static String help() {
        return """
                Here's a list of commands you can use:
                [cell]             - Shows the contents of the specified cell
                [cell] = [value]   - Sets the contents of the specified cell to the specified value
                clear              - Clears the entire spreadsheet
                clear [cell]       - Clears the specified cell
                help               - Shows this dialogue
                help [command]     - Shows help for a specific command
                list               - Lists the names of all active spreadsheets
                load [spreadsheet] - Loads a previously-created spreadsheet
                new                - Opens dialogue to create a new spreadsheet
                open [file]        - Opens a file
                rename [new name]  - Renames the current spreadsheet to the specified name
                save               - Saves the current spreadsheet to the last used file location
                save [file]        - Saves the current spreadsheet to a file
                show               - Shows the current state of the spreadsheet
                
                To view more help for the first two commands, use "help cell\"""";
    }

    public static String help(String command) {
        return switch(command) {
            case "cell" -> helpWithCellCommand();
            case "clear" -> helpWithClearCommand();
            case "help" -> helpWithHelpCommand();
            case "list" -> helpWithListCommand();
            case "load" -> helpWithLoadCommand();
            case "new" -> helpWithNewCommand();
            case "open" -> helpWithOpenCommand();
            case "rename" -> helpWithRenameCommand();
            case "save" -> helpWithSaveCommand();
            case "show" -> helpWithShowCommand();
            default -> "That command does not exist.";
        };
    }

    private static String helpWithCellCommand() {
        return "Entering a cell name prints the contents of the indicated cell.\n" +
                "Entering a cell name followed by \" = \" and some content changes the contents of the indicated cell to the indicated content.";
    }

    private static String helpWithClearCommand() {
        return "Entering \"clear\" deletes all content from the spreadsheet.\n" +
                "Adding a cell name as a parameter deletes the content from the indicated cell only.";
    }

    private static String helpWithHelpCommand() {
        return "Entering \"help\" shows a list of all commands that can be used.\n" +
                "Adding a parameter shows the help dialogue for the command entered as the parameter (for example, \"help help\" shows this dialogue).";
    }

    private static String helpWithListCommand() {
        return "Entering \"list\" lists the names of all spreadsheets created or opened in the current session.";
    }

    private static String helpWithLoadCommand() {
        return "Entering \"load\" followed by a spreadsheet title loads the indicated spreadsheet.\n" +
                "The spreadsheet loaded must have been previously created using the \"new\" command.";
    }

    private static String helpWithNewCommand() {
        return "Entering \"new\" opens a dialogue that allows for the creation of a new spreadsheet. You will be prompted to enter a title, as well as the number of rows and columns it should have.\n" +
                "There is a maximum of 999 rows and 26 columns.";
    }

    private static String helpWithOpenCommand() {
        return "Entering \"open\" followed by a file name opens the spreadsheet contained in the indicated file.\n" +
                "If any cell contains quotation marks or commas, the open command will not work." +
                "Google Sheets' and Microsoft Excel's CSV formats are mostly compatible with Texcel.";
    }

    private static String helpWithRenameCommand() {
        return "Entering \"rename\" followed by a string of text renames the current spreadsheet to the indicated name.";
    }

    private static String helpWithSaveCommand() {
        return """
                Entering "save" saves the current state of the active spreadsheet to the location that was last used to save that spreadsheet.
                This only works if the active spreadsheet has previously been saved to a file during this session.
                
                Entering "save" followed by a file name saves the current state of the active spreadsheet to the indicated file.
                
                If any cell contains quotation marks anywhere other than the beginning or end, the save command will not work.
                Spreadsheets will always save in the .csv format.
                Texcel's CSV format is mostly compatible with Google Sheets and Microsoft Excel.""";
    }

    private static String helpWithShowCommand() {
        return "Entering \"show\" prints the current state of the spreadsheet.";
    }
}
