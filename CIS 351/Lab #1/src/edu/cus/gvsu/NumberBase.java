package edu.cus.gvsu;

import java.util.HashMap;

public class NumberBase {

    private static HashMap<Character, Integer> constants_to_int = new HashMap<Character,Integer>() {{
        put(new Character('0'), 0);
        put(new Character('1'), 1);
        put(new Character('2'), 2);
        put(new Character('3'), 3);
        put(new Character('4'), 4);
        put(new Character('5'), 5);
        put(new Character('6'), 6);
        put(new Character('7'), 7);
        put(new Character('8'), 8);
        put(new Character('9'), 9);
        put(new Character('a'), 10);
        put(new Character('b'), 11);
        put(new Character('c'), 12);
        put(new Character('d'), 13);
        put(new Character('e'), 14);
        put(new Character('f'), 15);
        put(new Character('g'), 16);
        put(new Character('h'), 17);
        put(new Character('i'), 18);
        put(new Character('j'), 19);
        put(new Character('k'), 20);
        put(new Character('l'), 21);
        put(new Character('m'), 22);
        put(new Character('n'), 23);
        put(new Character('o'), 24);
        put(new Character('p'), 25);
        put(new Character('q'), 26);
        put(new Character('r'), 27);
        put(new Character('s'), 28);
        put(new Character('t'), 29);
        put(new Character('u'), 30);
        put(new Character('v'), 31);
        put(new Character('w'), 32);
        put(new Character('x'), 33);
        put(new Character('y'), 34);
        put(new Character('z'), 35);
    }};

    private static HashMap<Integer,Character> constants_from_int = new HashMap<Integer,Character>() {{
        put(0 , new Character('0'));
        put(1 , new Character('1'));
        put(2 , new Character('2'));
        put(3 , new Character('3'));
        put(4 , new Character('4'));
        put(5 , new Character('5'));
        put(6 , new Character('6'));
        put(7 , new Character('7'));
        put(8 , new Character('8'));
        put(9 , new Character('9'));
        put(10, new Character('a'));
        put(11, new Character('b'));
        put(12, new Character('c'));
        put(13, new Character('d'));
        put(14, new Character('e'));
        put(15, new Character('f'));
        put(16, new Character('g'));
        put(17, new Character('h'));
        put(18, new Character('i'));
        put(19, new Character('j'));
        put(20, new Character('k'));
        put(21, new Character('l'));
        put(22, new Character('m'));
        put(23, new Character('n'));
        put(24, new Character('o'));
        put(25, new Character('p'));
        put(26, new Character('q'));
        put(27, new Character('r'));
        put(28, new Character('s'));
        put(29, new Character('t'));
        put(30, new Character('u'));
        put(31, new Character('v'));
        put(32, new Character('w'));
        put(33, new Character('x'));
        put(34, new Character('y'));
        put(35, new Character('z'));
    }};

    // Convert from base N to base10
    private static int toBase10(String input, int base_in) {
        int sum = 0;
        int i = 0;
        int n = input.length();

        // TODO: Fill out this method.
        while (n > 0) {
            n--;

            if (!constants_to_int.containsKey(input.charAt(i))) {
                throw new IllegalArgumentException("Please don't use that character... PLEASE.");
            }

            if (constants_to_int.get(input.charAt(i)) > 1 && base_in == 2) {
                throw new IllegalArgumentException("That number is not convertible from base 2.");
            }

            int charVal = constants_to_int.get(input.charAt(i));

            if (charVal > base_in) {
                throw new IllegalArgumentException("Base 10 only accepts digits 0-9. PLEASE STOP.");
            }

            sum += (charVal * Math.pow(base_in, n));
            i++;
        }

        return sum;
    }

    // Convert from base10 to base N
    private static String toBaseN(int input, int base_out) {
        int n = input;
        StringBuilder sum = new StringBuilder();

        if (input != 0) {
            while (n != 0) {

                int d = n / base_out;

                int r = n % base_out;

                if (r >= 10) {
                    sum.append(constants_from_int.get(r));
                } else {
                    sum.append(r);
                }

                n = d;
            }
        } else {
            sum.append(input);
        }

        return sum.reverse().toString();
    }

    public static String convert(String input, int base_in, int base_out) {
        // Convert to Base 10 first

        int base_10 = toBase10(input,base_in);

        // Convert to the Output Base
        String result = toBaseN(base_10,base_out);

        return result;
    }

    public static void main(String args[]) {

        // Some examples of converting.
        System.out.println(convert("0",10,2));
//        System.out.println(convert("1",2,10));
//        System.out.println(convert("10",2,10));
//        System.out.println(convert("f",16,10));
//        System.out.println(convert("m",32,10));
//
//        System.out.println(convert("1",10,2));
    }
}
