import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class TDiagram {
    Scaner scaner ;

    Semantic semantic;

    Interpreter interpreter;

    Tree pointer_current_function;


    int flag_interpreter = 1;

    int flag_manual_interpritation = 0;

    int flag_createPicture = 0;

    Stack stack = new Stack();

    boolean SHOW_NAME_NOT_TERMINAL = false;

    public TDiagram() throws IOException, InterruptedException, Exception {

        scaner = new Scaner();
        if( this.programma()){
            System.out.println("all is ok");
            this.flag_createPicture = 1;
            semantic.createPicture();
        }
        else ;
    }

    public boolean is_interpritation(){
        return ( this.flag_manual_interpritation == 1 || this.flag_interpreter == 1);
    }

    public boolean programma() throws IOException, InterruptedException, Exception {
        ArrayList<Character> l = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        semantic = new Semantic();
        semantic.scaner = this.scaner;
        semantic.tDiagram = this;
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        interpreter = new Interpreter();
        interpreter.tDiagram = this;
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        int t ;
        SavePoint savePoint1 ;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);        // Считали ТИП

        while (t == scaner._INT || t == scaner._DOUBLE || t == scaner._CONST){
            // Если int double
            if( t == scaner._INT || t == scaner._DOUBLE){
                t = scaner.next(l); // Считали ИДЕНТИФИКАТОР

                if( t != scaner._ID && t != scaner._MAIN){
                    scaner.printError("1Ожидался идентификатор / ключевое слово main",l);
                    return false;
                }

                t = scaner.next(l); // Считали либо '=' или';' (если объявление переменных) либо '(' (если функция)

                if(t == scaner._ASSIGN ||  t == scaner._SEMICOLON || t == scaner._COMMA){ // = ;
                    //ОБЪЯВЛЕНИЕ ПРЕМЕННЫХ
                   scaner.setSavePoint(savePoint1);
                    if( !this.declaration_of_variable())
                        return false;
                }
                else if(t == scaner._PARENTHESIS_OPEN ){
                    //ФУНКЦИЯ
                   scaner.setSavePoint(savePoint1);
                    if( !this.func())
                        return false;
                }
                else{
                    scaner.printError("2Ожидался один из символов : = ; (",l);
                    return false;
                }


            }
            else{           // Если const
                if( t == scaner._CONST){
                   scaner.setSavePoint(savePoint1);
                    if ( !declaration_of_constant())
                        return false;
                }
                else {
                    scaner.printError("3Ожидалось ключевое слово int || const",l);
                    return false;
                }
            }
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
       scaner.setSavePoint(savePoint1);
        t = scaner.next(l);
        if( t == scaner._END)
            return true;
        else{
            scaner.printError("#Ожидось объявление функции или констант или переменных",l);
            return false;
        }

    }


    // Объявление констант
    public boolean declaration_of_constant() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("declaration_of_constant");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();
        Container containerG = new Container();

        savePoint1 = scaner.getSavePoint();///////////////////////////////////////////////////////////////////////////////////////////
        t = scaner.next(l);
        if( t != scaner._CONST){
            scaner.printError("4Ожидалось ключевое слово 'const'",l);
            return false;
        }

        t = scaner.next(l);
        if( t != scaner._INT && t != scaner._DOUBLE){
            scaner.printError("5Ожидалось ключевое слово 'int'/'double'",l);
            return false;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem1
        semantic.sem1(l, containerT);
        System.out.print("");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem1

        do{
            t = scaner.next(l);
            if( t != scaner._ID){
                scaner.printError("6Ожидался идентификато",l);
                return false;
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem2
            Tree k = semantic.sem2Const(l, containerT);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem2

            t = scaner.next(l);
            if(t != scaner._ASSIGN ){
                scaner.printError("34Ожидался символ '='   ",l);
                return false;
            }

            if( !this.expression(containerG))
                return false;

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem3
            semantic.sem3(containerT, containerG, l);
            semantic.semParamDeclared(k,containerG,l);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem3

            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
//            if( type == scaner._ASSIGN){
//                // =
//                type = scaner.next(l);
//                if( type == scaner._TYPE_INT_8 || type == scaner._TYPE_INT_10 || type == scaner._TYPE_INT_16){
//                    // Константа целая
//                }else if(type == scaner._TYPE_CHAR){
//                    // константа символьная
//                } else {
//                    scaner.printError("7Ожидалась константа символьная/числовая",l);
//                    return false;
//                }
//            }
        }while (t == scaner._COMMA );
       scaner.setSavePoint(savePoint1);
        t = scaner.next(l);
        if( t != scaner._SEMICOLON){
            scaner.printError("8ожидался символ ';'",l);
            return false;
        }
        return true;
    }


    /// / Объявление переменных
    public boolean declaration_of_variable() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("declaration_of_variable");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();
        Container containerG = new Container();

        t = scaner.next(l);
        if( t != scaner._INT && t != scaner._DOUBLE){
            scaner.printError("9Ожидалось ключевое слово 'int'/'double'",l);
            return false;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem1
        semantic.sem1(l, containerT);
        System.out.print("");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem1
        do{
            t = scaner.next(l);
            if( t != scaner._ID){
                scaner.printError("10Ожидался идентификато",l);
                return false;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem2
            ///////////////////////////////////////     в точке 1 запомнить адрес переменной a в семантической таблице

            Tree k = semantic.sem2(l,containerT);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem2
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
            //this.scaner.setUk(uk1);   scaner.setSavePoint(savePoint1);
            if( t == scaner._ASSIGN){
                // =
                // в точке 2 получить тип и значение выражения V
                if( !this.expression(containerG))
                    return false;
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem3

                semantic.sem3(containerT, containerG, l);
                semantic.semParamDeclared(k, containerG, l);

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    sem3

                // Интерпретатор 3 (в точке 3 запомнить вычисленное значение для переменной a)
                interpreter.saveValue_in_Tree(containerG, k);

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                savePoint1 = scaner.getSavePoint();
                t = scaner.next(l);
            }
            else{
                /// Тут пошли по пустой ветке, все нормас
            }

        }while (t == scaner._COMMA );
       scaner.setSavePoint(savePoint1);
        t = scaner.next(l);
        if( t != scaner._SEMICOLON){
            scaner.printError("11ожидался символ ';'",l);
            return false;
        }
        return true;
    }


    // функция
    public boolean func() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("func");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();

        t = scaner.next(l);
        if( t != scaner._INT && t != scaner._DOUBLE){
            scaner.printError("12ожидался тип int/double",l);
            return false;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem1
        semantic.sem1(l,containerT);

        t = scaner.next(l);
        int typeFunction = t;
        if( t != scaner._ID && t != scaner._MAIN) {
            scaner.printError("13Ожидался идентификатор", l);
            return false;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem21
        // объявление функции
        Tree k = semantic.sem21(l,containerT);

        t = scaner.next(l);
        // != '('
        if(t != scaner._PARENTHESIS_OPEN){
            scaner.printError("14Ожидался символ '(' ", l);
            return false;
        }

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        if( t != scaner._PARENTHESIS_CLOSE) { // != ')'
            // восстанавливаем метку до считываниея ')'
           scaner.setSavePoint(savePoint1);

            // Список параметров
            if( !this.lis_of_parametr(k))
                return false;

            t = scaner.next(l);
        }
        if( t != scaner._PARENTHESIS_CLOSE){
            scaner.printError("15Ожидался символ ')'", l);
            return false;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////inter
        // если это не мейн, то мы не будем интерпретировать тело функции
        if( typeFunction != Scaner._MAIN)
            flag_interpreter = 0;
        // Сохраняем указатель на начало тела функции
        k.n.savePoint_before_body_function = scaner.getSavePoint();

        // Составной оператор
        if( !this.compound_operator_WITHOUT_CREATE_BLACK_VERTEX())///////////////////////////////////////////// У функции свой собственный составной оператор
            return false;                                                                                             // который не создает две черные вершины
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// inter
        // возобнавляем интерпритацию
        this.flag_interpreter = 1;

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem18
        semantic.sem18(k);


        return true;
    }


    // Список параметров
    public boolean lis_of_parametr(Tree k){
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("lis_of_parametr");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();
        do{
            t = scaner.next(l);
            if( t != scaner._DOUBLE && t != scaner._INT){
                scaner.printError("16ожидался тип int/double",l);
                return false;
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem1
            semantic.sem1(l, containerT);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem1
            t = scaner.next(l);
            if( t != scaner._ID){
                scaner.printError("17ожидался идентификатор",l);
                return false;
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem2
            semantic.sem2(l, containerT);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem2
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem17
            semantic.sem17(k);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////// sem17
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }while (t == scaner._COMMA);
        // Вернем позицию перед ликсемой != запятой
       scaner.setSavePoint(savePoint1);
        return true;
    }


    // составной оператор
    public boolean compound_operator_WITHOUT_CREATE_BLACK_VERTEX( ) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("compound_operator");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        t = scaner.next(l);
        if( t != scaner._BRACE_OPEN){
            scaner.printError("18Ожидался символ '{'",l);
            return false;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);        // Считали
        //scaner.setUk(uk1);    scaner.setSavePoint(savePoint1);
        // Если int double то это объявление переменных
        // Если const то это объявление констант
        // Если идентификатор = то это оператор
        // Если { то это оператор
        // Если идентификатор то это оператор
        // Если if то это оператор
        // Если ; то это оператор
        while (t == scaner._INT || t == scaner._DOUBLE || t == scaner._CONST || t == scaner._ID || t == scaner._BRACE_OPEN ||
                t == scaner._IF || t == scaner._SEMICOLON){

            if(t == scaner._INT || t == scaner._DOUBLE  ){
                // Это объявление переменных
                scaner.setSavePoint(savePoint1);
                if( !this.declaration_of_variable())
                    return false;

            }
            else if(t == scaner._CONST){
                // Константа
                scaner.setSavePoint(savePoint1);
                if( !this.declaration_of_constant())
                    return false;
            }
            else if(t == scaner._ID || t == scaner._BRACE_OPEN ||  t == scaner._IF || t == scaner._SEMICOLON){
                // Оператор
                scaner.setSavePoint(savePoint1);
                if( !this.operator())
                    return false;
            }
            else{
                // eps Все нормас
            }
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);///////////////////////////////////////////////
            scaner.setSavePoint(savePoint1);///////////////////////////////////////////////////////////////////////////
        }
        scaner.setSavePoint(savePoint1);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        t = scaner.next(l);
        if(t != scaner._BRACE_CLOSE){
            scaner.printError("19ожидался символ '}'",l);
            return false;
        }



        if( this.pointer_current_function != null){
            if(interpreter.end_function() == true)
                return true;
        }
        else{
            // Это main
            return true;
        }


        return false;
    }


    // составной оператор
    public boolean compound_operator() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("compound_operator");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        t = scaner.next(l);
        if( t != scaner._BRACE_OPEN){
            scaner.printError("18Ожидался символ '{'",l);
            return false;
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem4
        Tree k = semantic.sem4();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem4
        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);        // Считали
        //scaner.setUk(uk1);    scaner.setSavePoint(savePoint1);
        // Если int double то это объявление переменных
        // Если const то это объявление констант
        // Если идентификатор = то это оператор
        // Если { то это оператор
        // Если идентификатор то это оператор
        // Если if то это оператор
        // Если ; то это оператор
        while (t == scaner._INT || t == scaner._DOUBLE || t == scaner._CONST || t == scaner._ID || t == scaner._BRACE_OPEN ||
                t == scaner._IF || t == scaner._SEMICOLON){

            if(t == scaner._INT || t == scaner._DOUBLE  ){
                // Это объявление переменных
               scaner.setSavePoint(savePoint1);
                if( !this.declaration_of_variable())
                    return false;

            }
            else if(t == scaner._CONST){
                // Константа
               scaner.setSavePoint(savePoint1);
                if( !this.declaration_of_constant())
                    return false;
            }
            else if(t == scaner._ID || t == scaner._BRACE_OPEN ||  t == scaner._IF || t == scaner._SEMICOLON){
                // Оператор
               scaner.setSavePoint(savePoint1);
                if( !this.operator())
                    return false;
            }
            else{
                // eps Все нормас
            }
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);///////////////////////////////////////////////
           scaner.setSavePoint(savePoint1);///////////////////////////////////////////////////////////////////////////
        }
       scaner.setSavePoint(savePoint1);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        t = scaner.next(l);
        if(t != scaner._BRACE_CLOSE){
            scaner.printError("19ожидался символ '}'",l);
            return false;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem18
        semantic.sem18(k);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem18
        return true;
    }


    public boolean func_return() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("func_return");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        // Считываем "return"
        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);

        // Считываем то, что после "return"
        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);

        scaner.setSavePoint(savePoint1);
        Container containerT = new Container();
        if( !this.expression(containerT))
            return true;

        t = scaner.next(l);
        if( t != scaner._SEMICOLON){
            scaner.printError("33ожидался символ ';'",l);
            return false;
        }
        if(interpreter.end_function(containerT) == true)
            return true;
        return false;
    }

    // Оператор
    public boolean operator() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("operator");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);

        // Проверка на return
        if( arrayChar2String(l).equals("return") && t == scaner._ID ){
            scaner.setSavePoint(savePoint1);
            if( !this.func_return())
                return false;
        }
        //
        else if( t == scaner._ID){
            //scaner.setUk(uk1); scaner.setSavePoint(savePoint1);
            t = scaner.next(l);
            if( t == scaner._ASSIGN){
                // Присваивание
               scaner.setSavePoint(savePoint1);
                if( !this.assignment())
                    return false;
            }
            else if(t == scaner._PARENTHESIS_OPEN){
                // Вызов функции
               scaner.setSavePoint(savePoint1);

                if( !this.func_call(new Container()))   //////////////////////////////////??????????????????????????????????
                    return false;
                ////////////////////////////////
                //////////////проверка на ; ////
                ////////////////////////////////
                t = scaner.next(l);
                if( t != scaner._SEMICOLON){
                    scaner.printError("33ожидался символ ';'",l);
                    return false;
                }
            }else {
                /////////////////////////////////////////////////////////////////////////////////// ошибка с ele
                scaner.printError("37 Ожидался символ = или (",l);
                return false;
            }
        }
        else if(t == scaner._BRACE_OPEN){
            scaner.setSavePoint(savePoint1);
            // Составной оператор
            if ( !this.compound_operator())
                return false;
        }
        else if( t == scaner._IF){
            scaner.setSavePoint(savePoint1);
            // IF
            if( !this.func_if())
                return false;
        }
        else if( t == scaner._SEMICOLON){
            // ;

        }
        else {
            scaner.printError("20Ошибка",l);
            return false;
        }
        return true;
    }


    // присваивание
    public boolean assignment() throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("assignment");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();
        Container containerG = new Container();

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        if( t != scaner._ID){
            scaner.printError("21Ожидался идентификатор",l);
            return false;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem5
        Tree k = semantic.sem5Assign(l, containerT);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem5
        t = scaner.next(l);
        if( t != scaner._ASSIGN){
            scaner.printError("22Ожидался символ '='",l);
            return false;
        }

        if( !this.expression(containerG))  // выражение
            return false;

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem3
        // Проверяем приводимость типов
        semantic.sem3(containerT,containerG, l);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// interprer сохраняем вычисленное выражени в дерево
        interpreter.saveValue_in_Tree(containerG,k);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// semParamDeclared
        // Устанавливаем флаг, что переменная определена
        semantic.semParamDeclared(k,containerG, l);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// semParamDeclared

        t = scaner.next(l);
        if( t != scaner._SEMICOLON){
            scaner.printError("23Ожидался символ ';'",l);
            return false;
        }
        return true;
    }
    // вызов функции
    public boolean func_call(Container container) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("function_call");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        Container containerG = new Container();

//        type = scaner.next(l);
//        if( type != scaner._INT && type != scaner._DOUBLE){
//            scaner.printError("24Ожидался тип int / double", l);
//            return false;
//        }


        t = scaner.next(l);
        if( t != scaner._ID){
            scaner.printError("25ожидался идентификатор", l);
            return false;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem5
        Tree k;
        // Проверяем объявлена ли функция, ищем ее узел
            //нужно найти именно образец
        k = semantic.find_prototype_function(l, container);

        t = scaner.next(l);
        if( t != scaner._PARENTHESIS_OPEN){
            scaner.printError("26Ожидался символ '('", l);
            return false;
        }


//        type = scaner.next(l);
//        if( !this.lis_of_parametr())
//            return false;
        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        scaner.setSavePoint(savePoint1);

        int countParam = 0;
        if( t != scaner._PARENTHESIS_CLOSE){
            // Значит там выражения через запятую т.е. параметры функции
            do{
                if(  !this.expression(containerG))
                    return false;
                countParam++;
                //////////////////////////////////////////////////////////////////////////////////////////////////////// semCheckType
                // Проверяем количество параметров функции и совпадение их типов
                boolean flag_tmp = semantic.semCheckType(countParam, k, containerG, l);
                if( !flag_tmp) return false;

                // Кладем в стек очередную считанную переменную / выражение     // Если только мы интерпретируем
                if( this.is_interpritation())
                    this.stack.push(containerG.copy());


                //////////////////////////////////////////////////////////////////////////////////////////////////////// semCheckType
                savePoint1 = scaner.getSavePoint();
                t = scaner.next(l);

            }while (t == scaner._COMMA );
           scaner.setSavePoint(savePoint1);
        }
        else {
            // Иначе это тупо закрывающаяся скобка, т.е. вызывается функция без параметров
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////// semCheckType
        boolean flag_tmp = semantic.semCheckCountParam(countParam, k , l);
        if( !flag_tmp ) return false;
        //////////////////////////////////////////////////////////////////////////////////////////////////////// semCheckType
        t = scaner.next(l);
        if( t != scaner._PARENTHESIS_CLOSE){
            scaner.printError("11ожидался символ ')'",l);
            return false;
        }

        // Если не интерпритируем, то тут нужно выходить
        if (this.flag_manual_interpritation != 1)
            if( this.flag_interpreter != 1 )
                return true;

        // запоминаем точку, для возвращения из фукнции
        k.n.savePoint_after_function_call = scaner.getSavePoint();





        // Коприуем дерево функции с локальными операторами. Помеащем его левым потомком
        // 2) при вызове копируется поддерево функции вместе с поддеревом формальных параметров;

        semantic.createPicture();

        semantic.copy_function(k);

        Tree newFunction = k.left;

        Tree last_left_elem =  semantic.last_children_function(newFunction);

        Tree old_cur = semantic.cur;

        semantic.cur = last_left_elem;

        semantic.createPicture();

        // 3) текущий указатель текста и текущий указатель семантического дерева устанавливается на позиции, соответствующие функции;
        scaner.setSavePoint(newFunction.n.savePoint_before_body_function);


        // восстанавливаем переменные из стека В ПОДДЕРЕВО СКОПИРОВАНОЙ ФУКНЦИИ
        interpreter.read_param_from_stack(last_left_elem, this.stack);
        semantic.createPicture();

        // сохраняем глобальный указатель на вызванную функцию
        Tree local_pointer_current_function = this.pointer_current_function;
        this.pointer_current_function = newFunction;

        this.compound_operator_WITHOUT_CREATE_BLACK_VERTEX();

        // восстанавливаем текущий элемент
        semantic.cur = old_cur;

        semantic.createPicture();




        //считываем значение с функции

        container.value = this.pointer_current_function.n.value.copy();
        container.type = this.pointer_current_function.n.returnType;


        // восстанавливаем глобальный указатель на вызыванную функцию
        this.pointer_current_function = local_pointer_current_function;


        // удаляем функцию из дерева
        semantic.deleteNode(newFunction);

        semantic.createPicture();
        int a = 1;
        int b = a;
        int c = b;
        int d = c;

        return true;
        /////////////////////////
    }
    // if иф
    public boolean func_if() throws IOException, InterruptedException, Exception {
        ///////////////////////////////////////////////////////////////////// inter
        // Точка 7: сохраняем текущее значение флага интерпретации
        int local_flag_interpreter = this.flag_interpreter;
        
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("function_call");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;
        Container containerT = new Container();
        Container containerG = new Container();

        t = scaner.next(l);
        if( t != scaner._IF  ){
            scaner.printError("28Ожидалось ключевое слово if", l);
            return false;
        }

        t = scaner.next(l);
        if( t != scaner._PARENTHESIS_OPEN  ){
            scaner.printError("29Ожидался символ '('", l);
            return false;
        }

        if( !this.expression(containerG))
            return false;

        ///////////////////////////////////////////////////////////////////// inter
        // Точка 8: вычисляем новое значение флага интерпретации
        if( flag_interpreter != 0 && containerG.get_value_IF() != 0){
           flag_interpreter = 1;
        }
        else {
            flag_interpreter = 0;
        }

        t = scaner.next(l);
        if( t != scaner._PARENTHESIS_CLOSE  ){
            scaner.printError("30Ожидался символ ')'", l);
            return false;
        }

        if( !this.operator())
            return false;


        ///////////////////////////////////////////////////////////////////// inter
        // Точка 9: инвертируем значение флага интерпретации,
        // если исходное значение флага интерпретации равнялось 1
        if ( local_flag_interpreter != 0 ){
            flag_interpreter = 1 - flag_interpreter;
        }

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        if( t == scaner._ELSE) {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if( !this.operator())/////////////////////////////////////////////////////////////////////////////////
                return false;
            //System.out.println();
        }else{
           scaner.setSavePoint(savePoint1);
        }

        ///////////////////////////////////////////////////////////////////// inter
        // Точка 10: восстанавливаем исходное значение флага интерпретации
        flag_interpreter = local_flag_interpreter;


        return true;
    }


    // выражение
    public boolean expression(Container container) throws IOException, InterruptedException, Exception{
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("expression");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        // type = scaner.next(l);
        if( !this.A2(container))
            return false;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        while (t == scaner._NOT_EQUALLY || t == scaner._EQUALLY){
            Container containerG = new Container();
            if( !A2(containerG))
                return false;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// sem6
            int newType = semantic.sem6(container, containerG, t, l);
            container.value.change_types(newType, container.type);
            container.type = newType;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// interpreter calculate
            interpreter.calculate(container, containerG, t);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
       scaner.setSavePoint(savePoint1);

        return true;
    }

    public boolean A2(Container container) throws IOException, InterruptedException, Exception {

//

        if(SHOW_NAME_NOT_TERMINAL) System.out.println("A2");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        //type = scaner.next(l);
        if( !this.A3(container))
            return false;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        while (t == scaner._GREAT|| t == scaner._LESS || t == scaner._GREAT_EQUALLY || t == scaner._LESS_EQUALLY){
            Container containerG = new Container();
            if ( !this.A3(containerG))
                return false;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// sem6
            int newType = semantic.sem6(container, containerG, t, l);
            container.value.change_types(newType, container.type);
            container.type = newType;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// interpreter calculate
            interpreter.calculate(container, containerG, t);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
              savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
       scaner.setSavePoint(savePoint1);
        return true;
    }

    public boolean A3(Container container) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("A3");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        //type = scaner.next(l);
        if ( !this.A4(container))
            return false;

          savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        while (t == scaner._SHIFT_LEFT|| t == scaner._SHIFT_RIGHT ){
            Container containerG = new Container();
            if( !this.A4(containerG))
                return false;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// sem6
            int newType = semantic.sem6(container, containerG, t, l);
            container.value.change_types(newType, container.type);
            container.type = newType;

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// interpreter calculate
            interpreter.calculate(container, containerG, t);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
       scaner.setSavePoint(savePoint1);
        return true;
    }
    public boolean A4( Container container) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("A4");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        int uk1;
        SavePoint savePoint1 ;

        //type = scaner.next(l);
        if( !this.A5(container))
            return false;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        while (t == scaner._PLUS|| t == scaner._MINUS ){
            Container containerG = new Container();
            if( !this.A5(containerG))
                return false;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// sem6
            int newType = semantic.sem6(container, containerG, t, l);
            container.value.change_types(newType, container.type);
            container.type = newType;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// interpreter calculate
            interpreter.calculate(container, containerG, t);
            ///////////////////////////////////////////////////////////////////////////////////////////////////fu//////////
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
       scaner.setSavePoint(savePoint1);
        return true;
    }

    public boolean A5(Container container) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("A5");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        //type = scaner.next(l);
        if( !this.A6(container))
            return false;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);
        while (t == scaner._STAR || t == scaner._SLASH || t == scaner._PERCENT ){
            Container containerG = new Container();
            if( !this.A6(containerG))
                return false;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// sem6
            int newType = semantic.sem6(container, containerG, t, l);
            container.value.change_types(newType, container.type);
            container.type = newType;
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////// interpreter calculate
            interpreter.calculate(container, containerG, t);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            savePoint1 = scaner.getSavePoint();
            t = scaner.next(l);
        }
        scaner.setSavePoint(savePoint1);

        return true;
    }

    public boolean A6( Container container) throws IOException, InterruptedException, Exception {
        if(SHOW_NAME_NOT_TERMINAL) System.out.println("A6");
        ArrayList<Character> l = new ArrayList<>();
        int t ;
        SavePoint savePoint1 ;

        savePoint1 = scaner.getSavePoint();
        t = scaner.next(l);

        if( t == scaner._TYPE_CHAR  || t == scaner._TYPE_INT_8 || t == scaner._TYPE_INT_10 || t == scaner._TYPE_INT_16 ){
            // константа
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////// sem55
            semantic.sem55(t,container);
            interpreter.saveValue(l,container);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            return true;
        }
        else if( t == scaner._PARENTHESIS_OPEN ){

            if( !this.expression(container))
                return false;
            t = scaner.next(l);
            if( t != scaner._PARENTHESIS_CLOSE){
                scaner.printError("31ожидался символ ')' ", l);
                return false;
            }
            return true;
        }
        else if (t == scaner._ID){
            // Либо вызов функции либо просто идентификатор
            SavePoint savePoint2 = scaner.getSavePoint();
            t = scaner.next(l);

            if( t == scaner._PARENTHESIS_OPEN){
                // вызов функции
               scaner.setSavePoint(savePoint1);
                if( !this.func_call(container))
                    return false;

            }
            else {
               scaner.setSavePoint(savePoint1);
               t = scaner.next(l);
                // идентификатор
                ///////////////////////////////////////////////////////////////////////////////////////////////////////// sem 5
                Tree node = semantic.sem5(l, container);
                interpreter.setValue_from_Tree(node, container);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                return true;
            }
        }
        else {
            scaner.printError("32Ожидался идентификатор / константа / символ '(' ",l);
            return false;
        }
        return true;
    }

    private String arrayChar2String(ArrayList<Character> l){

        String result = "";
        for(int i = 0 ; i < l.size(); i++)
            result += l.get(i);
        return result;
    }
}
