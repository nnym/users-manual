package user11681.usersmanual.collections;

import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

@VisibleForTesting
public abstract class ArrayMap<K, V> implements ParallelList<K, V> {
    protected K[] keys;
    protected V[] values;
    protected int length;
    protected int size;

    public ArrayMap() {
        this(10);
    }

    public ArrayMap(final int initialLength) {
        this.keys = ArrayUtil.create(initialLength);
        this.values = ArrayUtil.create(initialLength);
        this.length = initialLength;
    }

    public ArrayMap(final ParallelList<K, V> from) {
        this.keys = ArrayUtil.create(from.length());
        this.values = ArrayUtil.create(from.length());
    }

    @Override
    public void trimToSize() {
        this.resize(this.size);
    }

    @Override
    public void resize(final int newLength) {
        this.keys = Arrays.copyOf(this.keys, newLength);
        this.values = Arrays.copyOf(this.values, newLength);
        this.length = newLength;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int length() {
        return this.length;
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

    @Override
    public V getValue(final int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        return this.values[index];
    }

    public V getValue(final K key) {
        return this.getValue(this.indexOfFirstKey(key));
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
    public V remove(final K target) {
        return this.remove(this.indexOfFirstKey(target));
    }

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

    @Override
    public boolean isEmpty() {
        return this.size == 0;
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
                ArrayMap.this.remove(this.returned);
                this.cursor = this.returned;
                this.returned = -1;
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
