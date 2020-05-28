package user11681.farmerlib.collections;

public class ArrayUtil {
    @SuppressWarnings("unchecked")
    public static <T> T[] comparable(final int length) {
        return (T[]) new Comparable[length];
    }
}
