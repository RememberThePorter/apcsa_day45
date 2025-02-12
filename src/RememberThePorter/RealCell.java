package RememberThePorter;

import RememberThePorter.Interfaces.Cell;

public class RealCell implements Cell {
    public double getDoubleValue() {
        return 0.0;
    }

    @Override
    public String abbreviatedCellText() {
        return "";
    }

    @Override
    public String fullCellText() {
        return "";
    }
}
