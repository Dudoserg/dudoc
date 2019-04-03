import java.util.ArrayList;

public class Node {


    static final int TYPE_FUNC = 1;
    static final int TYPE_DOUBLE = 2;
    static final int TYPE_INTEGER = 3;
    static final int TYPE_UNKNOWN = 4;
    static final int TYPE_BLACK = 5;
    static final int TYPE_CHAR = 6;
    static final int TYPE_BOOLEAN = 7;
    private static int countNode = 0;
    public static int get_next_nameNode(){
        Node.countNode++;
        return Node.countNode;
    }
    public static int get_next_numberNode(){
        return Node.get_next_nameNode();
    }



    int nameNode;

    ArrayList<Character> id;        // Изображение

    int dataType;                   // тип(функция инт ...

    ArrayList<Character> data;      // значение

    int param;                      // количество параметров

    int returnType;                 // возвращаемый тип

    int prototype = 0;

    int flag_constant;      //

    int flag_declared;

    SavePoint savePoint_before_body_function;

    SavePoint savePoint_after_function_call;

    //////////////////////////////////////////////////////////////////////////
    TDataValue value;
    //////////////////////////////////////////////////////////////////////////

    public Node() {
        flag_constant = 0;
        flag_declared = 0;
        prototype = 0;
    }

    public TDataValue value_copy(){
        return this.value.copy();
    }

    public Node copy(){
        Node result = new Node();
        result.savePoint_after_function_call = this.savePoint_after_function_call;

        result.savePoint_before_body_function = this.savePoint_before_body_function;

        result.prototype = 0;
        if( this.value != null)
            result.value = this.value.copy();

        result.flag_declared = this.flag_declared;

        result.flag_constant = this.flag_constant;

        result.returnType = this.returnType;

        result.param = this.param;

        if( this.data != null)
            result.data = new ArrayList<Character>(this.data);

        result.dataType = this.dataType;

        if( this.id != null){
            result.id = new ArrayList<>();
            result.id.addAll(0,this.id);
        }

        result.nameNode = (Node.get_next_nameNode());

        return result;
    }


}
