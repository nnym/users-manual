package user11681.usersmanual.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

// @VisibleForTesting
public class SortedArrayMap<K extends Comparable<K>, V extends Comparable<V>> extends ArrayMap<K, V> {
    public SortedArrayMap(Map<K, V> from) {
        super(from);
    }

    public SortedArrayMap(Supplier<V> defaultValueSupplier, Iterable<K> keys) {
        super(defaultValueSupplier, keys);
    }

    @SafeVarargs
    public SortedArrayMap(Supplier<V> defaultValueSupplier, K... keys) {
        super(defaultValueSupplier, keys);
    }

    public SortedArrayMap() {
        super();
    }

    public SortedArrayMap(int initialLength) {
        super(initialLength);
    }

    @Override public void putAll(Map<? extends K, ? extends V> map) {
        var keys = map.keySet().iterator();
        var values = map.values().iterator();

        while (keys.hasNext()) {
            this.put(keys.next(), values.next());
        }
    }

    @Override public V put(K key, V value) {
        var size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        var index = this.indexOfKey(key);

        if (index < 0) {
            index = -index - 1;

            if (index < size) {
                this.shift(1, index, size);
            }
        } else if (index < size) {
            var previous = this.values[index];

            this.values[index] = value;

            return previous;
        }

        this.keys[index] = key;
        this.values[index] = value;
        ++this.size;

        return null;
    }

    protected <T> int binarySearch(T[] array, Comparable<Object> target) {
        var start = 0;
        var end = this.size - 1;

        while (start <= end) {
            var middle = (start + end) / 2;
            var compare = target.compareTo(array[middle]);

            if (compare < 0) {
                end = middle - 1;
            } else if (compare > 0) {
                start = middle + 1;
            } else {
                return middle;
            }
        }

        return -start - 1;
    }

    protected <T> int binarySearchFirst(T[] array, Comparable<Object> target) {
        var start = 0;
        var end = this.size - 1;

        while (start <= end) {
            var middle = (start + end) / 2;
            var previous = middle - 1;
            var middleElement = array[middle];

            if (target.compareTo(middleElement) <= 0) {
                end = previous;
            } else if (middle != 0 && target.compareTo(array[previous]) <= 0 && !middleElement.equals(target)) {
                start = middle + 1;
            } else {
                return middle;
            }
        }

        return -start - 1;
    }

/*
    protected <T extends Comparable<T>> int binarySearchLast(final T[] array, final T target) {
        final int end = this.size - 1;
        int first = 0;
        int last = end;

        while (first <= last) {
            final int middle = (first + last) / 2;
            final int next = middle + 1;
            final T middleElement = array[middle];

            if (target.compareTo(middleElement) < 0) {
                last = middle - 1;
            } else if (middle != end && target.compareTo(array[next]) >= 0 && !middleElement.equals(target)) {
                first = next;
            } else {
                return middle;
            }
        }

        return -first - 1;
    }
*/

    protected void sort() {
        var size = this.size;
        var keys = this.keys;
        var values = this.values;
        K[] tempKeys = ArrayUtil.comparable(size);
        V[] tempValues = ArrayUtil.comparable(size);

        for (var width = 1; width < size; width *= 2) {
            for (var i = 0; i < size; i += 2 * width) {
                var right = Math.min(i + width, size);
                var end = Math.min(i + 2 * width, size);
                var j = i;
                var k = right;

                for (var l = i; l < end; l++) {
                    if (j < right && (k >= end || keys[j].compareTo(keys[k]) <= 0)) {
                        tempKeys[l] = keys[j];
                        tempValues[l] = values[j++];
                    } else {
                        tempKeys[l] = keys[k];
                        tempValues[l] = values[k++];
                    }
                }
            }

            System.arraycopy(tempKeys, 0, keys, 0, size);
            System.arraycopy(tempValues, 0, values, 0, size);
        }
    }

    @Override public int indexOfKey(Object target) {
        //noinspection unchecked
        return target instanceof Comparable ? this.binarySearch(this.keys, (Comparable<Object>) target) : -1;
    }

    @Override public int indexOfValue(Object target) {
        var values = this.values;
        var size = this.size;

        for (var i = 0; i < size; i++) {
            if (values[i].equals(target)) {
                return i;
            }
        }

        return -size - 1;
    }

    @Override public int lastIndexOfValue(Object target) {
        var values = this.values;
        var size = this.size;
        var index = -size - 1;

        for (var i = 0; i < size; i++) {
            if (values[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }
}
