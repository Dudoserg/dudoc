public class TriadElem {
    private String operandFirst;
    private String operandSecond;
    private String operatorTriad;

    public TriadElem(String operandFirst, String operandSecond, String operatorTriad) {
        this.operandFirst = operandFirst;
        this.operandSecond = operandSecond;
        this.operatorTriad = operatorTriad;
    }

    public void setOperandFirst(String operandFirst) {
        this.operandFirst = operandFirst;
    }

    public void setOperandSecond(String operandSecond) {
        this.operandSecond = operandSecond;
    }

    public void setOperatorTriad(String operatorTriad) {
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
