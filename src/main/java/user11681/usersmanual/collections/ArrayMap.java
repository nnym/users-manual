package user11681.usersmanual.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import user11681.usersmanual.util.Stringified;

public abstract class ArrayMap<K, V> implements Map<K, V>, Iterable<K>, Stringified {
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

    public ArrayMap(final Map<K, V> from) {
        final int size = from.size();

        this.keys = ArrayUtil.create(size);
        this.values = ArrayUtil.create(size);

        this.putAll(from);
    }

    @SafeVarargs
    public ArrayMap(final V defaultValue, final K... keys) {
        this();

        for (final K key : keys) {
            this.put(key, defaultValue);
        }
    }

    public ArrayMap(final V defaultValue, final Iterable<K> keys) {
        this();

        for (final K key : keys) {
            this.put(key, defaultValue);
        }
    }

    public abstract int indexOfKey(Object target);

    public abstract int indexOfValue(Object target);

    public abstract int lastIndexOfValue(Object target);

    @Override
    public boolean containsKey(Object key) {
        return this.indexOfKey(key) > -1;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.indexOfValue(value) > -1;
    }

    public void trimToSize() {
        this.resize(this.size);
    }

    public void resize(final int newLength) {
        this.keys = Arrays.copyOf(this.keys, newLength);
        this.values = Arrays.copyOf(this.values, newLength);
        this.length = newLength;
    }

    public int size() {
        return this.size;
    }

    public int length() {
        return this.length;
    }

    @Override
    @Nonnull
    public ArraySet<K> keySet() {
        return new ArraySet<>(this.keys);
    }

    @Override
    @Nonnull
    public ArraySet<V> values() {
        return new ArraySet<>(this.values);
    }

    public K getKey(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        return this.keys[index];
    }

    public V get(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        return this.values[index];
    }

    public V get(final Object key) {
        final int index = this.indexOfKey(key);

        return index < 0 ? null : this.get(index);
    }

    public V remove(final Object target) {
        final int index = this.indexOfKey(target);

        if (index < -1 || index >= this.size) {
            return null;
        }

        return this.remove(index);
    }

    public V remove(final int index) {
        final int size = this.size;

        if (index >= size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        final V[] values = this.values;
        final K[] keys = this.keys;

        final V removed = values[index];

        this.shift(-1, index + 1, size);

        keys[size] = null;
        values[size] = null;

        --this.size;

        return removed;
    }

    protected void shift(final int shift, final int start, final int end) {
        System.arraycopy(this.keys, start, this.keys, start + shift, end - start);
        System.arraycopy(this.values, start, this.values, start + shift, end - start);
    }

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

        return builder.append('}').toString();
    }

    public void clear() {
        this.keys = ArrayUtil.create(this.length);
        this.values = ArrayUtil.create(this.length);
        this.size = 0;
    }

    @Nonnull
    @Override
    public Iterator<K> iterator() {
        return new ArrayMapIterator();
    }

    @Override
    @Nonnull
    public ArraySet<Map.Entry<K, V>> entrySet() {
        final ArraySet<Map.Entry<K, V>> entries = new ArraySet<>();
        final K[] keys = this.keys;
        final V[] values = this.values;

        for (int i = 0, size = this.size; i < size; i++) {
            entries.add(new Entry<>(keys[i], values[i]));
        }

        return entries;
    }

    protected class ArrayMapIterator implements Iterator<K> {
        public int index = 0;
        public int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return this.index < ArrayMap.this.size;
        }

        @Override
        public K next() {
            final int index = this.index;

            if (index >= ArrayMap.this.size) {
                throw new NoSuchElementException();
            }

            if (index >= ArrayMap.this.length) {
                throw new ConcurrentModificationException();
            }

            return ArrayMap.this.keys[lastReturned = this.index++];
        }

        @Override
        public void remove() {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            }

            try {
                ArrayMap.this.remove(this.lastReturned);
                this.index = this.lastReturned;
                this.lastReturned = -1;
            } catch (final IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(final Consumer<? super K> action) {
            Objects.requireNonNull(action);

            final int size = ArrayMap.this.size;
            int index = this.index;

            if (index < size) {
                if (index >= length) {
                    throw new ConcurrentModificationException();
                }

                while (index != size) {
                    action.accept(ArrayMap.this.getKey(index++));
                }

                this.index = index;
                this.lastReturned = index - 1;
            }
        }
    }

    public static class Entry<K, V> implements Map.Entry<K, V>, Stringified {
        protected K key;
        protected V value;

        public Entry(final K key) {
            this.key = key;
        }

        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(final V value) {
            final V old = this.value;

            this.value = value;
            return old;
        }

        @Override
        public String asString() {
            return String.format("{%s: %s}", this.key, this.value);
        }
    }
}
