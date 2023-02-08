package user11681.usersmanual.collections;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayUtil {
    public static <T> T[] comparable(final int length) {
        return (T[]) new Comparable[length];
    }

    public static <T> T[] create(final int length) {
        return (T[]) new Object[length];
    }

    @SafeVarargs
    public static <T, U extends T> T[] append(final T[] array, final U... items) {
        final int length = array.length;
        final int appendixLength = items.length;
        final T[] newArray = Arrays.copyOf(array, length + appendixLength);

        System.arraycopy(items, 0, array, length, appendixLength);

        return newArray;
    }
}
