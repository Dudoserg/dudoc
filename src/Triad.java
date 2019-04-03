import java.util.ArrayList;

public class Triad {

    ArrayList<TriadElem> triads;

    public Triad() {
        this.triads = new ArrayList<>();

    }

    public int add(String operandFirst, String operandSecond, String operatorTriad){

        this.triads.add(new TriadElem(operandFirst, operandSecond, operatorTriad));

        return this.getCurrentIndex();
    }
    public int getCurrentIndex(){
        return triads.size() - 1;
    }


    public void printTriadNum(int num){
        String result = "";
        TriadElem triadElem = this.triads.get(num);
        result += num + ") ";
        result += triadElem.getOperatorTriad() + " ";
        result += triadElem.getOperandFirst() + " ";
        result += triadElem.getOperandSecond() + " ";
        System.out.println(result);
    }

}
