

public class Container {
    int type;

    TDataValue value;

    public Container(int type) {
        this.type = type;

        this.initialize();
    }

    public Container() {

        this.initialize();
    }

    private void initialize(){

        this.value = new TDataValue();
    }


    // Используем для копирования контейрена, чтобы передать через стек значение
    public Container copy(){
        Container tmp = new Container();
        tmp.type = this.type;
        tmp.value = this.value.copy();
        return tmp;
    }

    // используется при присваивании одной переменной значение другой
    public TDataValue value_Copy(){

        return this.value.copy();
    }

    int get_value_IF(){
        switch (this.type){
//            case Node.TYPE_FUNC :{
//                break;
//            }
            case Node.TYPE_DOUBLE :{
                if(this.value.data_Double != 0)
                    return 1;
                else
                    return 0;
            }
            case Node.TYPE_INTEGER :{
                if(this.value.data_Int != 0)
                    return 1;
                else
                    return 0;
            }
//            case Node.TYPE_UNKNOWN :{
//                break;
//            }
//            case  Node.TYPE_BLACK :{
//                break;
//            }
            case Node.TYPE_CHAR :{
                if(this.value.data_Char != 0)
                    return 1;
                else
                    return 0;
            }
//            case Node.TYPE_BOOLEAN :{
//                break;
//            }
        }
        return 0;
    }



}
