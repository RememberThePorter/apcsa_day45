package RememberThePorter;

public class PercentCell extends RealCell {
    public String percent;

    public PercentCell(String percent) {
        this.percent = percent;
    }

    @Override
    public double getDoubleValue() {
        return super.getDoubleValue();
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviated = new StringBuilder(this.percent);
        if(abbreviated.toString().contains(".")) {
            abbreviated = new StringBuilder(abbreviated.substring(0, abbreviated.indexOf(".")) + "%");
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
        return String.valueOf(Double.parseDouble(this.percent.substring(0, this.percent.length() - 1)) / 100);
    }
}
