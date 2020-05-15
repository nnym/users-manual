package transfarmer.farmerlib.string;

import transfarmer.farmerlib.reflect.MethodWrapper;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String macroCaseToCamelCase(final String string) {
        final StringBuilder builder = new StringBuilder();
        final char[] chars = string.toLowerCase().toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char character = chars[i];

            if (character == '_') {
                character = (char) (chars[++i] - 32);
            }

            builder.append(character);
        }

        return builder.toString();
    }

    public static int countFormats(final String string) {
        return getFormatStrings(string).size();
    }

    public static List<String> getSpecifiers(final String string) {
        final List<String> specifiers = new ArrayList<>();

        for (final Object formatString : getFormatStrings(string)) {
            specifiers.add(formatString.toString());
        }

        return specifiers;
    }

    public static long count(String string, final String regex) {
        return match(string, regex).results().count();
    }

    public static boolean contains(final String string, final String regex) {
        return Pattern.compile(regex).matcher(string).find();
    }

    public static Matcher match(final String string, final String regex) {
        return Pattern.compile(regex).matcher(string);
    }

    private static List<?> getFormatStrings(final String string) {
        return new MethodWrapper<List<?>, Formatter>(new Formatter(), "parse", String.class).invoke(string);
    }
}
