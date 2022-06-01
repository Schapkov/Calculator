package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Инструкция: Калькулятор работает в арабской или римской арифметической системе с целыми " +
                "числами от 1 до 10 \n * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        while (true) {
            System.out.println("Введите выражение из двух чисел или введите END для " +
                    "завершения работы и нажмите Enter:");
            String operation = scanner.nextLine();
            try {
                if (operation.equalsIgnoreCase("END")) break;
                else System.out.println(calc(operation));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        scanner.close();
    }

    static String calc (String input) throws Exception {
        if (input.contains("-") || input.contains("+") || input.contains("*") || input.contains("/")) {
            String temp;
            if (input.contains(" ")) {
                StringBuilder builder = new StringBuilder();
                for (char string : input.toCharArray()) {
                    if (string != ' ') {
                        builder.append(string);
                    }
                }
                temp = builder.toString();
            } else temp = input;

            String[] words = temp.split("[-+*/]");
            String num1 = words[0];
            String num2 = words[1];
            String operator = temp.substring(words[0].length(), temp.length() - words[1].length());
            int operand1 = Converter.digit(num1);
            boolean op1 = Converter.flag;
            int operand2 = Converter.digit(num2);
            boolean op2 = Converter.flag;

            if (op1 && op2) {
                return Calculate(operand1, operand2, operator);
            } else if (!op1 && !op2) {
                return Calculate(operand1, operand2, operator);
            } else {
                throw new Exception("Ошибка: Введите числа в одной арифметической системе(Римские или Арабские)");
            }
        } else throw new Exception("Ошибка: отсутствует операнд выражения");
    }

    static String Calculate (int operand1, int operand2, String operator) throws Exception {
        int sum = 0;
        if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("*")) {
            switch (operator) {
                case ("+"):
                    sum = operand1 + operand2;
                    break;
                case ("-"):
                    sum = operand1 - operand2;
                    break;
                case ("*"):
                    sum = operand1 * operand2;
                    break;
                case ("/"):
                    sum = operand1 / operand2;
                    break;
            }

            if (Converter.flag) {
                if (sum > 0) return ConvertArabToRoman.convert(sum);
                else throw new Exception("Ошибка: в римской арифметической системе нет ноля и" +
                        " отрицательных чисел");
            } else {
                return Integer.toString(sum);
            }
        } else {
            throw new Exception("Ошибка: Введен лишний операнд или оператор, калькулятор работает с двумя чиcлами");
        }
    }
}

class Converter {
    public static boolean flag = false;

    static int digit(String input) throws Exception {
        String[] roman = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] arabian = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int digit = 0;

        for (int i = 0; i < roman.length; i++) {
            if (roman[i].equalsIgnoreCase(input)) {
                digit = i + 1;
                flag = true;

            } else if (arabian[i].equals(input)) {
                digit = i + 1;
                flag = false;
                break;
            }
        }
        if (digit == 0) {
            throw new Exception("Ошибка: Калькулятор умеет работать с числами от 1 до 10 в арабской или римской" +
                    " арифметической системе");
        }
        return digit;
    }
}

class ConvertArabToRoman {
    /*
   I - 1
   V - 5
   X - 10
   L - 50
   C - 100 калькулятор перемножает макс. 10*10 = 100:
   */
    public static String convert(int input) {
        StringBuilder roman = new StringBuilder("");
        int c1 = input / 100;
        roman.append(C(c1));
        int c2 = input % 100;
        roman.append(L(c2));
        int l2 = c2 % 50;
        if (c2 < 90) {
            int x1 = l2 / 10;
            roman.append(X(x1));
        }
        int x2 = l2 % 10;
        roman.append(digit(x2));
        return roman.toString();
    }

    static String C(int in) {
        StringBuilder roman = new StringBuilder("");
        int i = 0;
        while (i < in) {
            roman.append("C");
            i++;
        }
        return roman.toString();
    }

    static String L(int in) {
        if (in == 90) return "XC";
        else if (in >= 50 && in < 90) return "L";
        else return "";
    }

    static String X(int in) {
        if (in == 4) return "XL";
        else if ((in != 0) && (in < 4)) {
            StringBuilder roman = new StringBuilder("");
            int i = 0;
            while (i < in) {
                roman.append("X");
                i++;
            }
            return roman.toString();
        }
        else return "";
    }

    static String digit(int input) {
        String[] roman = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
        return roman[input];
    }
}
