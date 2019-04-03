import java.sql.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Interpreter {

    TDiagram tDiagram;

    private String arrayChar2String(ArrayList<Character> l){

        String result = "";
        for(int i = 0 ; i < l.size(); i++)
            result += l.get(i);
        return result;
    }


    // Устанавливаем ЗНАЧЕНИЕ переменной в КОНТЕЙНЕРЕ
    public void saveValue(ArrayList<Character> l, Container container){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        switch (container.type){
            case Node.TYPE_INTEGER :{
                // Преобразуем массив символов в строку
                String valueStr = arrayChar2String(l);
                // Сохраняем в контейнер значение константы
                container.value.data_Int = Integer.parseInt(valueStr);
                break;
            }
            case Node.TYPE_DOUBLE:{
                String valueStr = arrayChar2String(l);
                container.value.data_Double = Double.parseDouble(valueStr);
                break;
            }
            case Node.TYPE_CHAR:{
                // Берем первый элемент массива, т.к. у меня нет строк, то там всегда будет 1 символ в случае типа ЧАР
                container.value.data_Char = l.get(0);
                break;
            }
            case Node.TYPE_BOOLEAN:{
                break;
            }
        }
    }

    public void setValue_from_Tree(Tree node, Container container){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        container.value = node.n.value_copy();
    }


    public void calculate( Container first, Container second, int sign){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        switch (first.type){
            case Node.TYPE_INTEGER :{

                switch (second.type){
                    // int int
                    case Node.TYPE_INTEGER :{
                        first.type =  Node.TYPE_INTEGER;        // теперь тип Int
                        // *
                        if( sign == Scaner._STAR){
                            first.value.data_Int *= second.value.data_Int;
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.data_Int /= second.value.data_Int;
                        }
                        // %
                        else if( sign == Scaner._PERCENT){
                            first.value.data_Int %= second.value.data_Int;
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.data_Int += second.value.data_Int;
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.data_Int -= second.value.data_Int;
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){
                            first.value.data_Int = first.value.data_Int << second.value.data_Int;
                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){
                            first.value.data_Int = first.value.data_Int >> second.value.data_Int;
                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Int > second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Int < second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Int >= second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Int <= second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Int == second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Int != second.value.data_Int)
                                first.value.data_Int = 1;
                            else
                                first.value.data_Int = 0;
                        }

                        break;
                    }

                    // int double
                    case Node.TYPE_DOUBLE:{
                        first.type =  Node.TYPE_DOUBLE;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Double( first.value.data_Int * second.value.data_Double);
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Double( first.value.data_Int / second.value.data_Double);
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Double( first.value.data_Int % second.value.data_Double);
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Double( first.value.data_Int + second.value.data_Double);
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Double( first.value.data_Int - second.value.data_Double);
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Int > second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Int < second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Int >= second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Int <= second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Int == second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Int != second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }


                        break;
                    }

                    // int char
                    case Node.TYPE_CHAR:{
                        first.type =  Node.TYPE_INTEGER;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Int( first.value.data_Int * second.value.data_Char);
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Int( first.value.data_Int / second.value.data_Char);

                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Int( first.value.data_Int % second.value.data_Char);

                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Int( first.value.data_Int + second.value.data_Char);

                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Int( first.value.data_Int - second.value.data_Char);

                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Int( first.value.data_Int << second.value.data_Char);

                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Int( first.value.data_Int >> second.value.data_Char);

                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Int > second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);

                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Int < second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);;
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Int >= second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Int <= second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Int == second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Int != second.value.data_Char)
                                first.value.set_data_Int(1);
                            else
                                first.value.set_data_Int(0);
                        }
                        break;
                    }
                }

                break;
            }

            case Node.TYPE_DOUBLE:{
                switch (second.type){

                    // double int
                    case Node.TYPE_INTEGER :{
                        first.type =  Node.TYPE_DOUBLE;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Double( first.value.data_Double * second.value.data_Int);
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            double x = first.value.data_Double;
                            int xx = second.value.data_Int;
                            first.value.set_data_Double( first.value.data_Double / (double)second.value.data_Int);
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Double( first.value.data_Double % second.value.data_Int);
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Double( first.value.data_Double + second.value.data_Int);
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Double( first.value.data_Double - second.value.data_Int);
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Double > second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);

                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Double < second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Double >= second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Double <= second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Double == second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Double != second.value.data_Int)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        break;
                    }

                    // double double
                    case Node.TYPE_DOUBLE:{
                        first.type =  Node.TYPE_DOUBLE;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Double( first.value.data_Double * second.value.data_Double);
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Double( first.value.data_Double / second.value.data_Double);
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Double( first.value.data_Double % second.value.data_Double);
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Double( first.value.data_Double + second.value.data_Double);
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Double( first.value.data_Double - second.value.data_Double);
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Double > second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);

                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Double < second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Double >= second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Double <= second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Double == second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Double != second.value.data_Double)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }

                        break;
                    }

                    //double char
                    case Node.TYPE_CHAR:{
                        first.type =  Node.TYPE_DOUBLE;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Double( first.value.data_Double * second.value.data_Char);
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Double( first.value.data_Double / second.value.data_Char);
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Double( first.value.data_Double % second.value.data_Char);
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Double( first.value.data_Double + second.value.data_Char);
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Double( first.value.data_Double - second.value.data_Char);
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Double > second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);

                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Double < second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Double >= second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Double <= second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Double == second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Double != second.value.data_Char)
                                first.value.set_data_Double(1);
                            else
                                first.value.set_data_Double(0);
                        }

                        break;
                    }
                }
                break;
            }

            case Node.TYPE_CHAR:{
                switch (second.type){
                    // char int
                    case Node.TYPE_INTEGER :{
                        first.type =  Node.TYPE_INTEGER;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Int( (first.value.data_Char * second.value.data_Int) );
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Int( (first.value.data_Char / second.value.data_Int) );
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Int( (first.value.data_Char % second.value.data_Int) );
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Int( (first.value.data_Char + second.value.data_Int) );
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Int( (first.value.data_Char - second.value.data_Int) );
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Char > second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Char < second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Char >= second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Char <= second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Char == second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Char != second.value.data_Int)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }

                        break;
                    }

                    //char double
                    case Node.TYPE_DOUBLE:{
                        first.type =  Node.TYPE_DOUBLE;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Double( (first.value.data_Char * second.value.data_Double) );
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Double( (first.value.data_Char / second.value.data_Double) );
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Double( (first.value.data_Char % second.value.data_Double) );
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Double( (first.value.data_Char + second.value.data_Double) );
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Double( (first.value.data_Char - second.value.data_Double) );
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Char > second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Char < second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Char >= second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Char <= second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Char == second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Char != second.value.data_Double)
                                first.value.set_data_Double( 1 );
                            else
                                first.value.set_data_Double( 0 );
                        }

                        break;
                    }

                    // char char
                    case Node.TYPE_CHAR:{
                        first.type =  Node.TYPE_INTEGER;

                        // *
                        if( sign == Scaner._STAR){
                            first.value.set_data_Int( (first.value.data_Char * second.value.data_Char) );
                        }
                        // /
                        else if( sign == Scaner._SLASH){
                            first.value.set_data_Int( (first.value.data_Char / second.value.data_Char) );
                        }
                        // %
                        else if( sign == Scaner._PERCENT){  /////////////////////////////////////////////////////////////////////////////////////
                            first.value.set_data_Int( (first.value.data_Char % second.value.data_Char) );
                        }
                        // +
                        else if( sign == Scaner._PLUS){
                            first.value.set_data_Int( (first.value.data_Char + second.value.data_Char) );
                        }
                        // -
                        else if( sign == Scaner._MINUS){
                            first.value.set_data_Int( (first.value.data_Char - second.value.data_Char) );
                        }
                        // <<
                        else if( sign == Scaner._SHIFT_LEFT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >>
                        else if( sign == Scaner._SHIFT_RIGHT){///////////////////////////////////////////////////////////////////////////////////////////////////////////////


                        }
                        //  >
                        else if( sign == Scaner._GREAT){
                            if(first.value.data_Char > second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  <
                        else if( sign == Scaner._LESS){
                            if(first.value.data_Char < second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  >=
                        else if( sign == Scaner._GREAT_EQUALLY){
                            if(first.value.data_Char >= second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  <=
                        else if( sign == Scaner._LESS_EQUALLY){
                            if(first.value.data_Char <= second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  ==
                        else if( sign == Scaner._EQUALLY){
                            if(first.value.data_Char == second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }
                        //  !=
                        else if( sign == Scaner._NOT_EQUALLY){
                            if(first.value.data_Char != second.value.data_Char)
                                first.value.set_data_Int( 1 );
                            else
                                first.value.set_data_Int( 0 );
                        }

                        break;
                    }
                }
                break;

            }
        }

    }


    // Записываем значение в дерево
    public void saveValue_in_Tree(Container container, Tree k){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        int newType = k.n.dataType ;// теперь тип таким станет
        container.value.change_types(newType, container.type);
        container.type = newType;
        //
        k.n.value = container.value_Copy();

    }

    // Восстанавливаем стек в переменные фукнции
    public void read_param_from_stack(Tree current, Stack<Container> stack){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        while (!stack.empty()) {
            Container container = stack.pop();
            current.n.value = container.value.copy();
            current.n.dataType =container.type;
            current.n.flag_declared = 1;
            current = current.up;
        }
    }

    /// получаем node и container. Присваиваем функции значение с приведением типов
    public  void set_value_in_return(Tree node, Container container){
        if (this.tDiagram.flag_manual_interpritation != 1)
            if( this.tDiagram.flag_interpreter != 1 )
                return ;
        if( node.n.dataType != Node.TYPE_FUNC){
            System.out.println("==========EROOR #1=============");
            return;
        }
        node.n.value = new TDataValue();
        switch (node.n.returnType){
            case Node.TYPE_INTEGER :{
                // int = int
                if(container.type == Node.TYPE_INTEGER){
                    node.n.value.set_data_Int(container.value.data_Int);
                }
                // int = double
                else if(container.type == Node.TYPE_DOUBLE){
                    node.n.value.set_data_Int(container.value.data_Double);
                }
                // int = char
                else if(container.type == Node.TYPE_CHAR){
                    node.n.value.set_data_Int(container.value.data_Char);
                }
                break;
            }

            case  Node.TYPE_DOUBLE:{
                // double = int
                if(container.type == Node.TYPE_INTEGER){
                    node.n.value.set_data_Double(container.value.data_Int);
                }
                // double = double
                else if(container.type == Node.TYPE_DOUBLE){
                    node.n.value.set_data_Double(container.value.data_Double);
                }
                // double = char
                else if(container.type == Node.TYPE_CHAR){
                    node.n.value.set_data_Double(container.value.data_Char);
                }
                break;
            }

            case Node.TYPE_CHAR:{
                // char = int
                if(container.type == Node.TYPE_INTEGER){
                    node.n.value.set_data_Char(container.value.data_Int);
                }
                // char = double
                else if(container.type == Node.TYPE_DOUBLE){
                    node.n.value.set_data_Char(container.value.data_Double);
                }
                // char = char
                else if(container.type == Node.TYPE_CHAR){
                    node.n.value.set_data_Char(container.value.data_Char);
                }
                break;
            }
        }

    }

    // Конец функции, в случае ретерна, и в случае конца блока функции
    // true значит прекращаем функцию из которой вызвана эта функция
    public boolean end_function(Container containerT){
        // Если не интерпритируем, то не идем дальше
        if (this.tDiagram.flag_manual_interpritation == 1 || this.tDiagram.flag_interpreter == 1) {
            // сохраняем полученное значение в функцию
            // приводим типы
            this.set_value_in_return(this.tDiagram.pointer_current_function, containerT);


            // возвращаемся на место, где закончен вызов функции
            if (this.tDiagram.pointer_current_function != null)
                this.tDiagram.scaner.setSavePoint(this.tDiagram.pointer_current_function.n.savePoint_after_function_call);

        }
        if (this.tDiagram.flag_manual_interpritation != 1) {
            if (this.tDiagram.flag_interpreter != 1)
                return true;
        }
        return false;
    }
    public boolean end_function( ){
        // Если не интерпритируем, то не идем дальше
        if (this.tDiagram.flag_manual_interpritation == 1 || this.tDiagram.flag_interpreter == 1) {
            Container containerT = new Container();
            containerT.type = this.tDiagram.pointer_current_function.n.returnType;
            switch (this.tDiagram.pointer_current_function.n.returnType){
                case Node.TYPE_INTEGER:{
                    containerT.value.set_data_Int(0);
                    break;
                }
                case Node.TYPE_DOUBLE:{
                    containerT.value.set_data_Double(0);
                    break;
                }
                case Node.TYPE_CHAR:{
                    containerT.value.set_data_Char(0);
                    break;
                }
            }

            // сохраняем полученное значение в функцию
            // приводим типы
            this.set_value_in_return(this.tDiagram.pointer_current_function, containerT);


            // возвращаемся на место, где закончен вызов функции
            if (this.tDiagram.pointer_current_function != null)
                this.tDiagram.scaner.setSavePoint(this.tDiagram.pointer_current_function.n.savePoint_after_function_call);

        }
        if (this.tDiagram.flag_manual_interpritation != 1) {
            if (this.tDiagram.flag_interpreter != 1)
                return true;
        }
        return false;
    }

}
