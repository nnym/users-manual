package user11681.usersmanual.collections;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayUtil {
    public static <T> T[] comparable(int length) {
        return (T[]) new Comparable[length];
    }

    public static <T> T[] create(int length) {
        return (T[]) new Object[length];
    }

    @SafeVarargs
    public static <T, U extends T> T[] append(T[] array, U... items) {
        var length = array.length;
        var appendixLength = items.length;
        var newArray = Arrays.copyOf(array, length + appendixLength);

        System.arraycopy(items, 0, array, length, appendixLength);

        return newArray;
    }
}
