import java.util.ArrayList;
import java.util.Scanner;

public class Tree {

    static final int TYPE_FUNC = 1;
    static final int TYPE_DOUBLE = 2;
    static final int TYPE_INTEGER = 3;
    static final int TYPE_UNKNOWN = 4;
    static final int TYPE_BLACK = 5;
    static final int TYPE_CHAR = 6;
    

    static Scaner scaner;

    public Node n;
    public Tree up,left,right;

    public Tree(Tree left, Tree right, Tree up, Node n) {
        this.n = n;
        this.up = up;
        this.left = left;
        this.right = right;
    }


    public Tree(){
        n = new Node();
        up = null;
    }


    public void setLeft(Node data){
        Tree tmp = new Tree(null, null, this, data);
        left = tmp;
    }


    public void setRight(Node data){
        Tree tmp = new Tree(null, null, this, data);
        right = tmp;
    }

    public Tree findUp(ArrayList<Character> id){
        // поиск данных в дереве, начиная от текущей вершины this
        // до его корня вверх по связям
        return this.findUp(this, id);
    }

    public Tree findUp(Tree from, ArrayList<Character> id){
        // поиск данных в дереве от заданной вершины From
        // до его корня вверх по связям
        Tree current = from;
        // Пока не корень и строки( изображения ликсем) не равны, идем в родителя
        while (current != null ){
            // Если очередная вершина не равна
            if( current.n.dataType == Node.TYPE_BLACK){
                current = current.up;
            }
            else{
                if (current.n.id.equals(id) == true)
                     return current;
                    // Иначе выходим, мы нашли нужную вершину
                else
                    current = current.up;
            }

        }
        return null;
    }

    public Tree findRightLeft(ArrayList<Character> id){
        // поиск прямых потомков текущей вершины this
        return this.findRightLeft(this, id);
    }

    public Tree findRightLeft(Tree from,ArrayList<Character> id){
        Tree current = from.right;
        while ( current != null){
            if(  current.n.id.equals(id) == false)
                current = current.left;
            else
                break;
        }
        return current;
    }

    public void print(){
        System.out.println("Вершина с данными %s ----." + n.id );
        
        if (left != null)
            System.out.println(" слева данные %s" + left.n.id );

        if (right != null)
            System.out.println(" справа данные %s" + right.n.id );

        System.out.println("\n");

        if (left != null)
            left.print();

        if (right != null)
            right.print();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Tree findUpOneLevel(Tree from, ArrayList<Character> id){
        // Поиск элемента id вверх по дереву от текущей вершины From.
        // Поиск осуществляется на одном уровне вложенности по левым связям
        Tree current = from; // текущая вершина поиска
        while(  current != null   ){
            // Дошли до корня, просто проверяем и выходим
            if( current.up == null){
//                if( current.n.id.equals(id))
//                    return current; // нaшли совпадающий идентификатор
                break;
            }
            if(  current.up.right != current){
                if( current.n.dataType != Node.TYPE_BLACK)
                    if( current.n.id.equals(id))
                        return current; // нaшли совпадающий идентификатор
                current = current.up; // поднимаемся наверх по связям

            }
            else
                break;

        }
//        while(  current != null && current.n.dataType != Node.TYPE_BLACK  ){
//            if( current.up == null){
//                // Дошли до корня, просто проверяем и выходим
//                if( current.n.id.equals(id))
//                    return current; // нaшли совпадающий идентификатор
//                break;
//            }
//            if(  current.up.right != current){
//                if( current.n.id.equals(id))
//                    return current; // нaшли совпадающий идентификатор
//
//                current = current.up; // поднимаемся наверх по связям
//
//
//            }
//            else
//                break;
//
//        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Tree getParamNUm(Tree fromFunc, int num){
        Tree current = fromFunc.right;
        for(int i = 0 ; i < num; i++){
            current = current.left;
        }
        return current;
    }

    public Tree findFunction(ArrayList<Character> id){
        // поиск данных в дереве, начиная от текущей вершины this
        // до его корня вверх по связям
        return this.findFunction(this, id);
    }

    public Tree findFunction(Tree from, ArrayList<Character> lex){
        // поиск данных в дереве от заданной вершины From
        // до его корня вверх по связям
        Tree current = from;
        // Пока не корень и строки( изображения ликсем) не равны, идем в родителя
        while (current != null ){
            // Если очередная вершина не равна
            if( current.n.dataType == Node.TYPE_BLACK){
                current = current.up;
            }
            else{
                if (current.n.id.equals(lex) == true && current.n.dataType == Node.TYPE_FUNC)
                    return current;
                    // Иначе выходим, мы нашли нужную вершину
                else
                    current = current.up;
            }

        }
        return null;
    }
}
