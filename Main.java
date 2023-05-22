import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static String areRoman(String operator1, String operator2) {
        int areRoman = 0;
        Map<Integer, Character> mapOfRomanDigits = new HashMap<>();
        mapOfRomanDigits.put(0, 'I');
        mapOfRomanDigits.put(1, 'V');
        mapOfRomanDigits.put(2, 'X');
        mapOfRomanDigits.put(3, 'L');
        mapOfRomanDigits.put(4, 'C');
        mapOfRomanDigits.put(5, 'D');
        mapOfRomanDigits.put(6, 'M');
        String[] operators = {operator1, operator2};
        for (String operator : operators) {
            for (Integer key : mapOfRomanDigits.keySet()) {
                if (operator.charAt(0) == mapOfRomanDigits.get(key)) {
                    areRoman += 1;
                    break;
                }
            }
        }
        if (areRoman == 0) {
            return "areInteger";
        } else if (areRoman == 2) {
            return "areRoman";
        } else {
            return "Exception";
        }
    }

    static class CalcException extends Exception {
        public CalcException(String message) {
            super(message);
        }
    }

    public static String calc(String input) {
        String[] partsOfExpression = input.split(" ");
        try {
            if (partsOfExpression.length != 3) {
                throw new CalcException("throws Exception //т.к. формат математической операции не удовлетворяет заданию");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        String checkOfOpearator1 = partsOfExpression[0];
        String checkOfOpearator2 = partsOfExpression[2];
        Integer operator1 = 0;
        Integer operator2 = 0;
        boolean areRoman = false;
        try {
            switch (areRoman(checkOfOpearator1, checkOfOpearator2)) {
                case "areInteger":
                    operator1 = Integer.parseInt(partsOfExpression[0]);
                    operator2 = Integer.parseInt(partsOfExpression[2]);
                    break;
                case "areRoman":
                    RomanNumber operator1Tmp = new RomanNumber();
                    operator1Tmp.setValue(partsOfExpression[0]);
                    operator1 = operator1Tmp.toArabSystem();
                    RomanNumber operator2Tmp = new RomanNumber();
                    operator2Tmp.setValue(partsOfExpression[2]);
                    operator2 = operator2Tmp.toArabSystem();
                    areRoman = true;
                    break;
                default:
                    throw new CalcException("throws Exception //т.к. используются одновременно разные системы счисления");

            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        String operand = partsOfExpression[1];
        String answer = "0";
        Integer answerInArabSystem = 0;
        RomanNumber answerInRomaSystem = new RomanNumber();
        try {
            switch (operand) {
                case "+":
                    answerInArabSystem = operator1 + operator2;
                    break;
                case "-":
                    answerInArabSystem = operator1 - operator2;
                    break;
                case "*":
                    answerInArabSystem = operator1 * operator2;
                    break;
                case "/":
                    answerInArabSystem = operator1 / operator2;
                    break;
                default:
                    throw new CalcException("throws Exception //т.к. формат математической операции не удовлетворяет заданию");
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try {
            if (areRoman && answerInArabSystem >= 1) {
                answerInRomaSystem = IntegerUtils.toRomanSystem(answerInArabSystem);
                answer = answerInRomaSystem.getValue();
            } else if (!(areRoman)) {
                answer = answerInArabSystem.toString();
            } else {
                throw new CalcException("throws Exception //т.к. в римской системе нет чисел меньших единицы");
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите выражение или напишите 'Выйти': ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Выйти") || input.equalsIgnoreCase("Dsqnb")) {
                System.out.println("Пока!");
                System.exit(0);
                ;
            }
            System.out.println("Ответ:");
            System.out.println(calc(input));
            System.out.println();
        }
    }

    static class RomanNumber {
        private int valueInArabSystem;
        private String valueInRomanSystem;

        public void setValue(String valueInRomanSystem) {
            this.valueInRomanSystem = valueInRomanSystem;
        }

        public String getValue() {
            return valueInRomanSystem;
        }

        public int toArabSystem() {
            valueInArabSystem = numberToArabSystem(valueInRomanSystem);
            return valueInArabSystem;
        }

        private static int numberToArabSystem(String numberInRomanSystem) {
            Map<Character, Integer> digitToArabSystem = new HashMap<>();
            digitToArabSystem.put('I', 1);
            digitToArabSystem.put('V', 5);
            digitToArabSystem.put('X', 10);
            digitToArabSystem.put('L', 50);
            digitToArabSystem.put('C', 100);
            digitToArabSystem.put('D', 500);
            digitToArabSystem.put('M', 1000);
            digitToArabSystem.put('0', 0);
            digitToArabSystem.put(' ', 0);
            int numberInArabSystem = digitToArabSystem.get(numberInRomanSystem.charAt(0));
            for (int i = 1; i < numberInRomanSystem.length(); i++) {
                int highestDigitInArabSystem = digitToArabSystem.get(numberInRomanSystem.charAt(i - 1));
                int lowestDigitInArabSystem = digitToArabSystem.get(numberInRomanSystem.charAt(i));
                if (highestDigitInArabSystem < lowestDigitInArabSystem) {
                    numberInArabSystem += lowestDigitInArabSystem - 2 * highestDigitInArabSystem;
                } else {
                    numberInArabSystem += lowestDigitInArabSystem;
                }
            }
            return numberInArabSystem;
        }
    }

    abstract class IntegerUtils {
        public static final int MAX_VALUE_IN_ARAB_SYSTEM = 1499;
        public static final String MAX_VALUE_IN_ROMAN_SYSTEM = "MCDXCIX";

        public static String digitToRomanSystem(Integer digitInArabSystem) {
            RomanNumber digitInRomanSystem = new RomanNumber();
            digitInRomanSystem.setValue("0");
            String digitInRomanSystemTmp = "";
            Map<Integer, Character> digitToRomanSystem = new HashMap<>();
            digitToRomanSystem.put(1, 'I');
            digitToRomanSystem.put(5, 'V');
            digitToRomanSystem.put(10, 'X');
            if (digitInArabSystem <= 3 && digitInArabSystem != 0) {
                while (digitInRomanSystem.toArabSystem() != digitInArabSystem) {
                    digitInRomanSystemTmp += digitToRomanSystem.get(1);
                    digitInRomanSystem.setValue(digitInRomanSystemTmp);
                }
            } else if (digitInArabSystem == 4) {
                digitInRomanSystemTmp += digitToRomanSystem.get(1);
                digitInRomanSystemTmp += digitToRomanSystem.get(5);
                digitInRomanSystem.setValue(digitInRomanSystemTmp);
            } else if (digitInArabSystem <= 8 && digitInArabSystem != 0) {
                digitInRomanSystemTmp += digitToRomanSystem.get(5);
                digitInRomanSystem.setValue(String.valueOf(digitToRomanSystem.get(5)));
                while (digitInRomanSystem.toArabSystem() != digitInArabSystem) {
                    digitInRomanSystemTmp += digitToRomanSystem.get(1);
                    digitInRomanSystem.setValue(digitInRomanSystemTmp);
                }
            } else {
                digitInRomanSystemTmp += digitToRomanSystem.get(1);
                digitInRomanSystemTmp += digitToRomanSystem.get(10);
                digitInRomanSystem.setValue(digitInRomanSystemTmp);
            }
            return digitInRomanSystem.getValue();
        }

        public static RomanNumber toRomanSystem(Integer numberInArabSystem) {
            RomanNumber numberInRomanSystem = new RomanNumber();
            String digitInRomanSystem;
            numberInRomanSystem.setValue(" ");
            Map<Integer, Character> numberToRomanSystem = new HashMap<>();
            numberToRomanSystem.put(1, 'I');
            numberToRomanSystem.put(5, 'V');
            numberToRomanSystem.put(10, 'X');
            numberToRomanSystem.put(50, 'L');
            numberToRomanSystem.put(100, 'C');
            numberToRomanSystem.put(500, 'D');
            numberToRomanSystem.put(1000, 'M');
            int count = 1;
            while (numberInArabSystem > 0) {
                int digitInArabSystem = numberInArabSystem % 10;
                if (digitInArabSystem != 0) {
                    digitInRomanSystem = digitToRomanSystem(digitInArabSystem);
                    RomanNumber digitOfDigitRoman = new RomanNumber();
                    StringBuilder digitInRomanSystemFinal = new StringBuilder(digitInRomanSystem);
                    for (int i = 0; i < digitInRomanSystem.length(); i++) {
                        digitOfDigitRoman.setValue(String.valueOf(digitInRomanSystem.charAt(i)));
                        ;
                        int index = digitOfDigitRoman.toArabSystem() * count;
                        digitInRomanSystemFinal.setCharAt(i, numberToRomanSystem.get(index));
                    }
                    numberInRomanSystem.setValue(digitInRomanSystemFinal.toString() + numberInRomanSystem.getValue());
                    numberInArabSystem /= 10;
                    count *= 10;
                } else {
                    numberInArabSystem /= 10;
                    count *= 10;
                }
            }
            return numberInRomanSystem;
        }
    }

}