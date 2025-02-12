package RememberThePorter;

public class FormulaCell extends RealCell {
    public String formula;

    public FormulaCell(String formula) {
        this.formula = formula;
    }

    @Override
    public double getDoubleValue() {
        return super.getDoubleValue();
    }

    @Override
    public String abbreviatedCellText() {
        StringBuilder abbreviated = new StringBuilder(this.formula);
        if(this.formula.startsWith("\"") && this.formula.endsWith("\"")) {
            abbreviated = new StringBuilder(this.formula.substring(1, this.formula.length() - 1));
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
        return this.formula;
    }
}
