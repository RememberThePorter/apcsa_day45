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
        StringBuilder abbreviated = new StringBuilder(String.valueOf(getDoubleValue()));
        if(abbreviated.length() > 10) {
            abbreviated = new StringBuilder(abbreviated.substring(0, 10));
        } else if(abbreviated.length() < 10) {
            abbreviated.append(" ".repeat(Math.max(0, 10 - abbreviated.length())));
        }
        return abbreviated.toString();
    }

    @Override
    public String fullCellText() {
        return this.value;
    }
}