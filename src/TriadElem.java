public class TriadElem {
    private String operandFirst;
    private String operandSecond;
    private String operatorTriad;

    public TriadElem(String operandFirst, String operandSecond, String operatorTriad) {
        this.operandFirst = operandFirst;
        this.operandSecond = operandSecond;
        this.operatorTriad = operatorTriad;
    }

    public String getOperandFirst() {
        return operandFirst;
    }

    public String getOperandSecond() {
        return operandSecond;
    }

    public String getOperatorTriad() {
        return operatorTriad;
    }
}
