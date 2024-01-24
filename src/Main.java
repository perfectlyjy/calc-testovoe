import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static String calc(String input) throws Exception {
        String[] tokens = input.split("\\s+");
        if (tokens.length != 3) {
            throw new Exception("Invalid input: должно быть три элемента (операнд1, оператор, операнд2)");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        if (!isRomanNumber(operand1) && !isRomanNumber(operand2)) {
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);
            int result = performOperation(num1, num2, operator);
            return Integer.toString(result);
        } else if (isRomanNumber(operand1) && isRomanNumber(operand2)) {
            int num1 = romanToArabic(operand1);
            int num2 = romanToArabic(operand2);
            int result = performOperation(num1, num2, operator);
            return arabicToRoman(result);
        } else {
            throw new Exception("Invalid input: операнды должны быть одного типа (или оба римские, или оба арабские)");
        }
    }

    static boolean isRomanNumber(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    static int performOperation(int num1, int num2, String operator) throws Exception {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль невозможно");
                }
                return num1 / num2;
            default:
                throw new Exception("Неверный оператор: " + operator);
        }
    }

    static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = romanMap.get(roman.charAt(i));

            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }

            prevValue = curValue;
        }

        return result;
    }

    static String arabicToRoman(int arabic) {
        if (arabic <= 0) {
            throw new IllegalArgumentException("Арабские числа должны быть положительными");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();

        for (int i = arabicValues.length - 1; i >= 0; i--) {
            while (arabic >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                arabic -= arabicValues[i];
            }
        }

        return result.toString();
    }
}
