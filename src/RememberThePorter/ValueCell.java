package RememberThePorter;

public class ValueCell extends RealCell {
    public String value;

    public ValueCell(String value) {
        this.value = value;
    }

    @Override
    public double getDoubleValue() {
        return Double.parseDouble(fullCellText());
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviatedCellText = new StringBuilder(String.valueOf(getDoubleValue()));
        if(abbreviatedCellText.length() > 10) {
            abbreviatedCellText = new StringBuilder(abbreviatedCellText.substring(0, 10));
        } else if(abbreviatedCellText.length() < 10) {
            abbreviatedCellText.append(" ".repeat(Math.max(0, 10 - abbreviatedCellText.length())));
        }
        return abbreviatedCellText.toString();
    }

    @Override
    public String fullCellText() {
        return this.value;
    }
}