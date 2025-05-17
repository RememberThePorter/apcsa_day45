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
        StringBuilder abbreviatedCellText = new StringBuilder(formula);
        if(formula.startsWith("\"") && formula.endsWith("\"")) {
            abbreviatedCellText = new StringBuilder(formula.substring(1, formula.length() - 1));
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
        return formula;
    }
}
