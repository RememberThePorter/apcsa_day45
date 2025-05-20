package RememberThePorter;

public class PercentCell extends RealCell {
    public String percent;

    public PercentCell(String percent) {
        this.percent = percent;
    }

    @Override
    public double getDoubleValue() {
        return Double.parseDouble(percent.substring(0, percent.length() - 1)) / 100;
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviatedCellText = new StringBuilder(percent);
        if(abbreviatedCellText.toString().contains(".")) {
            abbreviatedCellText = new StringBuilder(abbreviatedCellText.substring(0, abbreviatedCellText.indexOf(".")) + "%");
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
        return percent;
    }
}
