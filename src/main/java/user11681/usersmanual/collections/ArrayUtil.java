package user11681.usersmanual.collections;

public class ArrayUtil {
    @SuppressWarnings("unchecked")
    public static <T> T[] comparable(final int length) {
        return (T[]) new Comparable[length];
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] create(final int length) {
        return (T[]) new Object[length];
    }
}
