package RememberThePorter.Interfaces;

//*******************************************************
//DO NOT MODIFY THIS FILE!!!
//*******************************************************

public interface Grid
{
    // Grid interface, must be implemented by your Spreadsheet class
    String processCommand(String command); // processes a user command, returns string to display, must be called in loop from main

    private int getRows() // returns number of rows in grid
    {
        return 0;
    }

    private int getColumns() // returns number of columns in grid
    {
        return 0;
    }

    Cell getCell(Location location); // returns cell at loc
    String getGridText(); // returns entire grid, formatted as text for display
}