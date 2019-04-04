import java.util.ArrayList;

public class Triad {

    public  boolean DEBUG_MODE = false;

    ArrayList<TriadElem> triads;

    public Triad() {
        this.triads = new ArrayList<>();

    }

    public int add(String operandFirst, String operandSecond, String operatorTriad){

        this.triads.add(new TriadElem(operandFirst, operandSecond, operatorTriad));

        return this.getCurrentIndex();
    }
    public TriadElem getTriad(int num){
        return this.triads.get(num);
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
        if( this.DEBUG_MODE )
            System.out.println(result);
        else
            System.out.print("");
    }

    public void displayAll(){
        this.DEBUG_MODE = true;
        System.out.println("============================================================================");
        for(int i = 0 ; i < this.triads.size(); i++)
            this.printTriadNum(i);
        System.out.println("============================================================================");
    }

}
