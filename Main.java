
import java.io.*;
import java.util.*;

public class Main
{
	
	public static void main(String[] args) {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
            String sIn;
            try {
                System.out.println("Введте выражение для расчета. Поддерживаются Арабские и Римские цифры, операции +,-,*,/. Например 3+4 или X-VI и нажмите Enter:");
                sIn = d.readLine().trim();
                sIn = opn(sIn);
                System.out.println(calculate(sIn));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
	
    private static String opn(String sIn) throws Exception {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);
                if (isOp(cIn)) {
                    sbOut.append(" ");
                    sbStack.append(cIn);
                } else if (isRomNum(String.valueOf(cIn)) || isArNum(String.valueOf(cIn))) {//проверка арифметического оператора
                    // Если символ не оператор - добавляем в выходную последовательность
                    sbOut.append(cIn);
                } else {
                    throw new Exception("Неверная арифметическая операция! Поддерживаются только выражения вида например 3+4 или X-VI  и следующие операторы +,-,*,/.");
                }
        }
        
         if (sbStack.length() == 0){
            throw new Exception("Отсутствует арифметический оператор +,-,*,/.");
         } else if (sbStack.length() > 1){
            throw new Exception("Введены лишние арифметические операторы +,-,*,/.");
         }
         
            sbOut.append(" ").append(sbStack);

        return  sbOut.toString();
    }

    private static boolean isOp(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
                return true;
        }
        return false;
    }
    
	
	 private static String calculate(String sIn) throws Exception {
        String sTmp, numFin;
        Integer num1, num2, arabrom = 0, dA = 0, dB = 0;
        String[] subStr;
        String del = " "; // Разделитель
        //char rIn;
        subStr = sIn.split(del);
        
        //Проверка на одинаковость операндов
        if (isRomNum(subStr[0]) && isArNum(subStr[1])) {
            throw new Exception("Ошибка! Оба операнда в выражении должны быть или только Римские или только Арабские! Например: X+VI или 6-9.");
        } else if (isArNum(subStr[0]) && isRomNum(subStr[1])) {
            throw new Exception("Ошибка! Оба операнда в выражении должны быть или только Римские или только Арабские! Например: X+VI или 6-9.");
        }
        
        //Изменение римских операндов на арабские
        if (isRomNum(subStr[0])) {
            subStr[0] = RomanInArab(subStr[0]);
            subStr[1] = RomanInArab(subStr[1]);
            arabrom = 1;
        }
       
       //Проверка операнда на условие "больше 10"
        num1 = Integer.parseInt(subStr[0]);
        num2 = Integer.parseInt(subStr[1]);
        if (num1 > 10 || num2 > 10) {
            throw new Exception("Введенное число больше 10!");
        }
        sIn = String.join(" ", subStr);//"Склейка массива"
        
        Deque<Integer> stack = new ArrayDeque<Integer>();
        Deque<String> stackStr = new ArrayDeque<String>();
        StringTokenizer st = new StringTokenizer(sIn);
       
        while(st.hasMoreTokens()) {
                sTmp = st.nextToken().trim();
                if (isOp(sTmp.charAt(0))) {
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (sTmp.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            dA /= dB;
                            break;
                        case '*':
                            dA *= dB;
                            break;
                    }
                        if (arabrom == 1) {
                          stackStr.push(Roman(dA)); 
                        } else {
                          stackStr.push(dA.toString());
                        }
                } else {
                    dA = Integer.parseInt(sTmp);
                    stack.push(dA);
                }
        }
        
        return stackStr.pop();
    }
    
public static String Roman(Integer input){//арабское в римское
 
     String s = "";
     
     while (input >= 100) {
     s += "C";
     input -= 100;
     }
     while (input >= 90) {
     s += "XC";
     input -= 90;
     }
     while (input >= 50) {
     s += "L";
     input -= 50;
     }
     while (input >= 40) {
     s += "XL";
     input -= 40;
     }
     while (input >= 10) {
     s += "X";
     input -= 10;
     }
     while (input >= 9) {
     s += "IX";
     input -= 9;
     }
     while (input >= 5) {
     s += "V";
     input -= 5;
     }
     while (input >= 4) {
     s += "IV";
     input -= 4;
     }
     while (input >= 1) {
     s += "I";
     input -= 1;
     } 
     return s;
}//конец static String Roman

public static String RomanInArab(String romValue){
      switch (romValue) {
            case "I":
                return "1";
            case "II":
                return "2";
            case "III":
                return "3";
            case "IV":
                return "4";
            case "V":
                return "5";
            case "VI":
                return "6";
            case "VII":
                return "7";
            case "VIII":
                return "8";
            case "IX":
                return "9";
            case "X":
                return "10";
      }
     return romValue;
}

    private static boolean isArNum(String c) {
        switch (c) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
                return true;
        }
        return false;
    }
    
    private static boolean isRomNum(String c) {
        switch (c) {
            case "I":
            case "II":
            case "III":
            case "IV":
            case "V":
            case "VI":
            case "VII":
            case "VIII":
            case "IX":
            case "X":
                return true;
        }
        return false;
    }
	
	
}
