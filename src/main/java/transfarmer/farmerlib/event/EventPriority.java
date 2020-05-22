package transfarmer.farmerlib.event;

import transfarmer.farmerlib.collections.CollectionUtil;

import java.util.List;

public class EventPriority {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;

    public static List<Integer> getPriorities() {
        return CollectionUtil.arrayList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);
    }
}
