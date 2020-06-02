package user11681.usersmanual.collections;

import com.google.common.annotations.VisibleForTesting;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

@VisibleForTesting
public class SortedArrayMap<K extends Comparable<K>, V extends Comparable<V>> extends ArrayMap<K, V> {
    public SortedArrayMap(final Map<K, V> from) {
        super(from);
    }

    public SortedArrayMap(final Supplier<V> defaultValueSupplier, final Iterable<K> keys) {
        super(defaultValueSupplier, keys);
    }

    @SafeVarargs
    public SortedArrayMap(final Supplier<V> defaultValueSupplier, final K... keys) {
        super(defaultValueSupplier, keys);
    }

    public SortedArrayMap() {
        super();
    }

    public SortedArrayMap(final int initialLength) {
        super(initialLength);
    }

    public void putAll(final Map<? extends K, ? extends V> map) {
        final Iterator<? extends K> keys = map.keySet().iterator();
        final Iterator<? extends V> values = map.values().iterator();

        while (keys.hasNext()) {
            this.put(keys.next(), values.next());
        }
    }

    @Override
    public V put(final K key, final V value) {
        final int size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        int index = this.indexOfKey(key);

        if (index < 0) {
            index = -index - 1;

            if (index < size) {
                this.shift(1, index, size);
            }
        } else if (index < size) {
            final V previous = this.values[index];

            this.values[index] = value;

            return previous;
        }

        this.keys[index] = key;
        this.values[index] = value;
        ++this.size;

        return null;
    }

    protected <T> int binarySearch(final T[] array, final Comparable<Object> target) {
        int start = 0;
        int end = this.size - 1;

        while (start <= end) {
            final int middle = (start + end) / 2;
            final int compare = target.compareTo(array[middle]);

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

    protected <T> int binarySearchFirst(final T[] array, final Comparable<Object> target) {
        int start = 0;
        int end = this.size - 1;

        while (start <= end) {
            final int middle = (start + end) / 2;
            final int previous = middle - 1;
            final T middleElement = array[middle];

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
        final int size = this.size;
        final K[] keys = this.keys;
        final V[] values = this.values;
        final K[] tempKeys = ArrayUtil.comparable(size);
        final V[] tempValues = ArrayUtil.comparable(size);

        for (int width = 1; width < size; width *= 2) {
            for (int i = 0; i < size; i += 2 * width) {
                final int right = Math.min(i + width, size);
                final int end = Math.min(i + 2 * width, size);
                int j = i;
                int k = right;

                for (int l = i; l < end; l++) {
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

    @Override
    public int indexOfKey(final Object target) {
        //noinspection unchecked
        return target instanceof Comparable ? this.binarySearch(this.keys, (Comparable<Object>) target) : -1;
    }

    @Override
    public int indexOfValue(final Object target) {
        final V[] values = this.values;
        final int size = this.size;

        for (int i = 0; i < size; i++) {
            if (values[i].equals(target)) {
                return i;
            }
        }

        return -size - 1;
    }

    @Override
    public int lastIndexOfValue(final Object target) {
        final V[] values = this.values;
        final int size = this.size;
        int index = -size - 1;

        for (int i = 0; i < size; i++) {
            if (values[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }
}
