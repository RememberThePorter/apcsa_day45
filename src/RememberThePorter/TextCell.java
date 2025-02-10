package RememberThePorter;

import RememberThePorter.Interfaces.Cell;

public class TextCell implements Cell {
    public String text;

    public TextCell(String text) {
        this.text = text;
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviated = new StringBuilder(this.text);
        if(this.text.startsWith("\"") && this.text.endsWith("\"")) {
            abbreviated = new StringBuilder(this.text.substring(1, this.text.length() - 1));
        }
        if(this.text.startsWith("\"") && this.text.endsWith("\"")) {
            abbreviated = new StringBuilder(this.text.substring(1, this.text.length() - 1));
        }
        if(abbreviated.length() > 10) {
            abbreviated = new StringBuilder(abbreviated.substring(0, 10));
        } else if(abbreviated.length() < 10) {
            abbreviated.append(" ".repeat(Math.max(0, 10 - abbreviated.length())));
        }
        return abbreviated.toString();
    }

    @Override
    public String fullCellText() {
        return this.text;
    }
}