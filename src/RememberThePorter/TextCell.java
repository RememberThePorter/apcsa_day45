package RememberThePorter;

import RememberThePorter.Interfaces.Cell;

public class TextCell implements Cell {
    public String text;

    public TextCell(String text) {
        this.text = text;
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviatedCellText = new StringBuilder(text);
        if(text.startsWith("\"\"\"") && text.endsWith("\"\"\"")) {
            abbreviatedCellText = new StringBuilder(text.substring(2, text.length() - 2));
        }
        if(abbreviatedCellText.length() > 10) {
            abbreviatedCellText = new StringBuilder(abbreviatedCellText.substring(0, 10));
        } else if(abbreviatedCellText.length() < 10) {
            abbreviatedCellText.append(" ".repeat(Math.max(0, 10 - abbreviatedCellText.length())));
        }
        return abbreviatedCellText.toString();
    }

    @Override
    public String fullCellText() {
        return text;
    }
}