package transfarmer.farmerlib.collections;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@VisibleForTesting
public class ParallelArrayList<K extends Comparable<K>, V extends Comparable<V>> implements ParallelList<K, V> {
    protected K[] keys;
    protected V[] values;
    protected int length;
    protected int size;

    public ParallelArrayList() {
        this(10);
    }

    public ParallelArrayList(final int initialLength) {
        this.keys = ArrayUtil.comparable(initialLength);
        this.values = ArrayUtil.comparable(initialLength);
        this.length = initialLength;
    }

    protected void resize(final int newLength) {
        this.keys = Arrays.copyOf(this.keys, newLength);
        this.values = Arrays.copyOf(this.values, newLength);
        this.length = newLength;
    }

    @Nonnull
    @Override
    public List<K> keyList() {
        return Arrays.asList(this.keys);
    }

    @Nonnull
    @Override
    public List<V> valueList() {
        return Arrays.asList(this.values);
    }

    @Override
    public K getKey(final int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        return this.keys[index];
    }

    public V getValue(final K key) {
        return this.getValue(this.indexOfFirstKey(key));
    }

    @Override
    public V getValue(final int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        return this.values[index];
    }

    @Override
    public void addAll(final Map<K, V> map) {
        final Iterator<K> keys = map.keySet().iterator();
        final Iterator<V> values = map.values().iterator();

        while (keys.hasNext()) {
            this.add(keys.next(), values.next());
        }
    }

    @Override
    public boolean add(final K key, final V value) {
        final int size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        int index = this.indexOfFirstKey(key);

        if (index < 0) {
            index = -index - 1;
        }

        this.size++;

        if (index < size) {
            this.shift(1, index, size);
        }

        this.keys[index] = key;
        this.values[index] = value;

        return true;
    }

    protected <T extends Comparable<T>> int binarySearchFirst(final T[] array, final T target) {
        int first = 0;
        int last = this.size - 1;

        while (first <= last) {
            final int middle = (first + last) / 2;
            final int previous = middle - 1;
            final T middleElement = array[middle];

            if (target.compareTo(middleElement) <= 0) {
                last = previous;
            } else if (middle != 0 && target.compareTo(array[previous]) <= 0 && !middleElement.equals(target)) {
                first = middle + 1;
            } else {
                return middle;
            }
        }

        return -first - 1;
    }

    public <T extends Comparable<T>> int binarySearchLast(final T[] array, final T target) {
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
//
//    protected <T extends Comparable<T>> int binarySearchLast(final T[] array, final T target) {
//        final int size = this.size;
//
//        int first = 0;
//        int last = size - 1;
//
//        while (first <= last) {
//            final int middle = (first + last) / 2;
//            final int comparison = array[middle].compareTo(target);
//
//            if (comparison < 0) {
//                first = middle + 1;
//            } else if (comparison > 0) {
//                last = middle - 1;
//            } else {
//
//            }
//        }
//    }

    protected void shift(final int shift, final int start, final int end) {
        final K[] keys = this.keys;
        final V[] values = this.values;

        if (shift > 0) {
            for (int i = end; i > start; --i) {
                keys[i] = keys[i - shift];
                values[i] = values[i - shift];
            }
        } else if (shift < 0) {
            for (int i = start; i < end; i++) {
                keys[i] = keys[i - shift];
                values[i] = values[i - shift];
            }
        }
    }

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
                        tempValues[l] = values[j];
                        j++;
                    } else {
                        tempKeys[l] = keys[k];
                        tempValues[l] = values[k];
                        k++;
                    }
                }
            }

            System.arraycopy(tempKeys, 0, keys, 0, size);
            System.arraycopy(tempValues, 0, values, 0, size);
        }
    }

    @Override
    public int indexOfFirstKey(final K target) {
        return this.binarySearchFirst(this.keys, target);
    }

    @Override
    public int indexOfFirstValue(final V target) {
        return this.binarySearchFirst(this.values, target);
    }

    @Override
    public int indexOfLastKey(final K target) {
        return this.binarySearchLast(this.keys, target);
    }

    @Override
    public int indexOfLastValue(final V target) {
        return this.binarySearchLast(this.values, target);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(final K key) {
        return this.indexOfFirstKey(key) > 0;
    }

    @Override
    public boolean containsValue(final V value) {
        return this.indexOfFirstValue(value) > 0;
    }

    @Override
    public V remove(final K target) {
        return this.remove(this.indexOfFirstKey(target));
    }

    @Override
    public V remove(final int index) {
        if (index < -1 || index >= this.size) {
            return null;
        }

        final V[] values = this.values;
        final K[] keys = this.keys;

        final V removed = values[index];

        this.shift(-1, index, --this.size);

        keys[this.size] = null;
        values[this.size] = null;

        return removed;
    }

    @Override
    public void trimToSize() {
        this.resize(this.size);
    }

    @Override
    public void clear() {
        final K[] keys = this.keys;
        final V[] values = this.values;

        for (int i = 0, size = this.size; i < size; i++) {
            keys[i] = null;
            values[i] = null;
        }

        this.size = 0;
    }

    @Nonnull
    @Override
    public Iterator<K> iterator() {
        return new ArrayMapIterator();
    }

    @Override
    public String asString() {
        final StringBuilder builder = new StringBuilder("{");

        final K[] keys = this.keys;
        final V[] values = this.values;

        for (int i = 0, size = this.size; i < size; i++) {
            builder.append(String.format("{%s: %s}", keys[i], values[i]));

            if (i != size - 1) {
                builder.append(", ");
            }
        }

        builder.append("}");

        return builder.toString();
    }

    protected class ArrayMapIterator implements Iterator<K> {
        public int cursor = 0;
        public int returned = -1;

        @Override
        public boolean hasNext() {
            return this.cursor < size;
        }

        @Override
        public K next() {
            return keys[this.cursor++];
        }

        @Override
        public void remove() {
            if (this.returned < 0) {
                throw new IllegalStateException();
            }

            try {
                ParallelArrayList.this.remove(this.returned);
                this.cursor = this.returned;
                this.returned = -1;
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
