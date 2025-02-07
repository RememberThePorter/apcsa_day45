package RememberThePorter;

import RememberThePorter.Interfaces.Cell;

public class TextCell implements Cell {
    public String text;

    public TextCell(String text) {
        this.text = text;
    }

    @Override
    public String abbreviatedCellText() {
        if(this.text.length() > 10) {
            return this.text.substring(0, 10);
        } else {
            return this.text;
        }
    }

    @Override
    public String fullCellText() {
        return this.text;
    }
}