package RememberThePorter;

import RememberThePorter.Interfaces.Cell;

public class EmptyCell implements Cell {
    @Override
    public String abbreviatedCellText() {
        return "          ";
    }

    @Override
    public String fullCellText() {
        return "";
    }
}
