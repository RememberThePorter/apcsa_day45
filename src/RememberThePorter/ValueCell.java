package RememberThePorter;

public class ValueCell extends RealCell {
    public String value;

    public ValueCell(String value) {
        this.value = value;
    }

    @Override
    public double getDoubleValue() {
        return super.getDoubleValue();
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviated = new StringBuilder(String.valueOf(Double.parseDouble(fullCellText())));
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