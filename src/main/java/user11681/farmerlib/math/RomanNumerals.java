package user11681.farmerlib.math;

import it.unimi.dsi.fastutil.chars.Char2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2CharArrayMap;
import java.util.Map;

public class RomanNumerals {
    protected static final Map<Character, Integer> TO_DECIMAL = new Char2IntLinkedOpenHashMap();
    protected static final Map<Integer, Character> TO_ROMAN = new Int2CharArrayMap();

    static {
        TO_DECIMAL.put('M', 1000);
        TO_DECIMAL.put('D', 500);
        TO_DECIMAL.put('C', 100);
        TO_DECIMAL.put('L', 50);
        TO_DECIMAL.put('X', 10);
        TO_DECIMAL.put('V', 5);
        TO_DECIMAL.put('I', 1);

        TO_ROMAN.put(1000, 'M');
        TO_ROMAN.put(500, 'D');
        TO_ROMAN.put(100, 'C');
        TO_ROMAN.put(50, 'L');
        TO_ROMAN.put(10, 'X');
        TO_ROMAN.put(5, 'V');
        TO_ROMAN.put(1, 'I');
    }

    public static String fromDecimal(long decimal) {
        if (decimal == 0) {
            return "nulla";
        }

        final StringBuilder roman = new StringBuilder();

        for (final int value : TO_ROMAN.keySet()) {
            while (decimal >= value) {
                roman.append(TO_ROMAN.get(value));
                decimal -= value;
            }
        }

        return roman.toString();
    }

    public static long toDecimal(final String roman) {
        final char[] characters = roman.toCharArray();
        long decimal = 0;

        for (int i = 0, length = characters.length; i < length; i++) {
            final char character = characters[i];
            final int current = TO_DECIMAL.get(character);

            if (i < length - 1 && TO_DECIMAL.get(characters[i + 1]) > current) {
                decimal -= current;
            } else {
                decimal += current;
            }
        }

        return decimal;
    }
}
