# dudoc
язык dudoC
Основные возможности языка:<br/>
•	Операции + - * /<br/>
•	Условный оператор if / else<br/>
•	Оператор присваивания<br/>
•	Типы данных int, double<br/>
•	Функции с параметрами, возвращающие значение.<br/>
1)	Запустим простейший пример, следующего содержания:
int main(){<br/>
    int a = 2;<br/>
    int b = 3;<br/>
    int c = a + b;<br/>
}<br/>

В результате получим следующее дерево, переменная c вычислена правильно
 
 
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/1.png)

Использование деления
int main(){<br/>
    int a = 10;<br/>
    int b = 3;<br/>
    int c = a / b;<br/>
}<br/>
Присутствует приведение типов, так что ошибок не возникнет. В переменную «с» поместится число 3

![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/2.png)

Присваивание вещественному типу
int main(){<br/>
    double a = 10;<br/>
    int b = 3;<br/>
    double c = a / b;<br/>
}<br/>
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/3.png)

Пример с использованием условного оператора
int main(){<br/>
   int a = 2;<br/>
   int c = 13;<br/>
   if( a == 2){<br/>
        c = 1;<br/>
   }else{<br/>
        c = 0;<br/>
   }<br/>
}<br/>

![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/4.png)

Пример с использованием условного оператора, с выполнением ветки «else»
int main(){<br/>
   int a = 2;<br/>
   int c = 13;<br/>
   if( a != 2){<br/>
        c = 1;<br/>
   }else{<br/>
        c = 0;<br/>
   }<br/>
}<br/>
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/5.png)

Пример со вложенным if
int main(){<br/>
   int a = 2;<br/>
   int c = 13;<br/>
   if( a == 2){<br/>
        if( c == 13){<br/>
            c = c * 2;<br/>
        }else{<br/>
            c = c - 10;<br/>
        }<br/>
   }else{<br/>
        c = 0;<br/>
   }<br/>
}<br/>
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/6.png)

Использование функций. 
При вызове функции осуществлены все нужные проверки: проверка объявлена ли функция, совпадает ли количество переданных переменных, проверка на типы переменных.
int add(int a, int b){<br/>
    return a+b;<br/>
}<br/>
int main(){<br/>
   int a = 2;<br/>
   int result = add(a,3);<br/>
}<br/>
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/7.png)

Пример использования рекурсии
Вычислим число Фиббоначи под индексом n
int f(int n)<br/>
{<br/>
   if (n == 1)<br/>
      return 1;<br/>
   if( n == 2)<br/>
       return 1;<br/>
   if (n == 0)<br/>
      return 0;<br/>
   return f(n - 1) + f(n - 2);<br/>
}<br/>
int main()<br/>
{<br/>
   int n = 20;<br/>
   int x = f(n);<br/>
}<br/>

![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/8.png)
Получили число 6765, что является правдой. Таким образом рекурсия работает нормально.

А теперь, используя все возможности языка, попробуем вычислить синус, достаточно не тривиальная задача. 
Т.к. язык поддерживает рекурсивные вызовы функций (с параметрами, и возвращаемым значением), то мы можем реализовать циклы через функции.
Программа для вычисления синуса, выглядит следующим образом
int fuct(int a)<br/>
{<br/>
   if (a > 1)<br/>
   {<br/>
      return a * fuct(a - 1);<br/>
   }<br/>
   else<br/>
      return 1;<br/>
}<br/>
<br/>
int globali = 0;<br/>
int globaln = 0;<br/>
double globalpoverty = 0;<br/>
double globalx = 0;<br/>
<br/>
int cycle() {<br/>
   if (globali < 2 * globaln - 1) {<br/>
      globali = globali + 1;<br/>
      globalpoverty = globalpoverty * globalx;<br/>
      cycle();<br/>
   }<br/>
}<br/>

double pover(double x, int n)<br/>
{<br/>
   double poverty = 1;<br/>
   globalpoverty = 1;<br/>
   globaln = n;<br/>
   globali = 0;<br/>
   globalx = x;<br/>
<br/>
   cycle();<br/>
   poverty = globalpoverty;<br/>
<br/>
   return poverty;<br/>
}<br/>
<br/>
int sgn(int n)<br/>
{<br/>
   int k = 0;<br/>
   k = n % 2;<br/>
   if (k == 0)<br/>
   {<br/>
      return 0 - 1;<br/>
   }<br/>
   else<br/>
      return 1;<br/>
}<br/>
<br/>
int countafterpoint = 3;<br/>
<br/>
double globalsum = 0;<br/>
<br/>
int cycle2(int n, double x) {<br/>
   if (n <= countafterpoint) {<br/>
      globalsum = globalsum + (sgn(n)*pover(x, n)) / fuct(2 * n - 1);<br/>
      cycle2(n + 1, x);<br/>
   }<br/>
   return 0;<br/>
}<br/>
<br/>
double expended(double x)<br/>
{<br/>
   double summ = 0;<br/>
    countafterpoint = countafterpoint + 1;<br/>
<br/>
   cycle2(1, x);<br/>
<br/>
   summ = globalsum;<br/>
   return summ;<br/>
}<br/>
<br/>
int main()<br/>
{<br/>
  double x = 0;<br/>
  double y = 0;<br/>
  x = 314159 / 400000;<br/>
  y = expended(x);<br/>
<br/>
}<br/>
В процессе работы программы строится дерево и его графическое отображение в виде графа.
На картинке мы видим все используемые переменные, прототипы функций, которые в процессе работы программы копируются и после удаляются.
тут мы вычисляем sin(314159 / 400000) = sin(pi/4) = 0.707106… 
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/9.1.png)
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/9.2.png)

Как мы видим на рисунках №1 и рисунке №2 , в переменной «y» записано число 0,7071.. . Таким образом ожидаемый результат получен, программа исправно вычисляет синус.
Так же можно попробовать вычислить синус другого угла, в результате получим 0,99843 (рис 3), что является допустимым, данный результат проходит по точности указанной в программе.
![Image alt](https://github.com/Dudoserg/dudoc/blob/master/result_img/10.png)
