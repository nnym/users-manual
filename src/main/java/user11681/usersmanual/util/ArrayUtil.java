package user11681.usersmanual.util;

import java.util.Arrays;

public class ArrayUtil {
    @SafeVarargs
    public static <T, U extends T> T[] append(final T[] array, final U... items) {
        final int length = array.length;
        final T[] newArray = Arrays.copyOf(array, length + items.length);

        for (int i = length, itemLength = items.length; i < itemLength; i++) {
            newArray[i + length] = items[i];
        }

        return newArray;
    }
}
