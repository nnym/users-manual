package user11681.usersmanual.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import user11681.usersmanual.util.Stringifiable;

public abstract class ArrayMap<K, V> implements Map<K, V>, Iterable<K>, Stringifiable {
    protected K[] keys;
    protected V[] values;
    protected int length;
    protected int size;

    public ArrayMap(Map<K, V> from) {
        var size = from.size();

        this.keys = ArrayUtil.create(size);
        this.values = ArrayUtil.create(size);

        this.putAll(from);
    }

    public ArrayMap(Supplier<V> valueSupplier, Iterable<K> keys) {
        this();

        for (var key : keys) {
            this.put(key, valueSupplier.get());
        }
    }

    @SafeVarargs
    public ArrayMap(Supplier<V> valueSupplier, K... keys) {
        this();

        for (var key : keys) {
            this.put(key, valueSupplier.get());
        }
    }

    public ArrayMap() {
        this(10);
    }

    public ArrayMap(int initialLength) {
        this.keys = ArrayUtil.create(initialLength);
        this.values = ArrayUtil.create(initialLength);
        this.length = initialLength;
    }

    public abstract int indexOfKey(Object target);

    public abstract int indexOfValue(Object target);

    public abstract int lastIndexOfValue(Object target);

    @Override public boolean containsKey(Object key) {
        return this.indexOfKey(key) > -1;
    }

    @Override public boolean containsValue(Object value) {
        return this.indexOfValue(value) > -1;
    }

    public void trimToSize() {
        this.resize(this.size);
    }

    public void resize(int newLength) {
        this.keys = Arrays.copyOf(this.keys, newLength);
        this.values = Arrays.copyOf(this.values, newLength);
        this.length = newLength;
    }

    @Override public int size() {
        return this.size;
    }

    public int length() {
        return this.length;
    }

    @Override public ArraySet<K> keySet() {
        return new ArraySet<>(this.size, this.keys);
    }

    @Override public ArraySet<V> values() {
        return new ArraySet<>(this.size, this.values);
    }

    public K getKey(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        return this.keys[index];
    }

    public V get(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        return this.values[index];
    }

    @Override public V get(Object key) {
        var index = this.indexOfKey(key);

        return index < 0 ? null : this.get(index);
    }

    public void removeIf(Predicate<K> predicate) {
        var iterator = this.iterator();

        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    @Override public V remove(Object target) {
        var index = this.indexOfKey(target);

        if (index < -1 || index >= this.size) {
            return null;
        }

        return this.remove(index);
    }

    public V remove(int index) {
        var size = this.size;

        if (index >= size) {
            throw new IndexOutOfBoundsException("" + index);
        }

        var values = this.values;
        var keys = this.keys;

        var removed = values[index];

        this.shift(-1, index + 1, size);

        keys[size] = null;
        values[size] = null;

        --this.size;

        return removed;
    }

    protected void shift(int shift, int start, int end) {
        System.arraycopy(this.keys, start, this.keys, start + shift, end - start);
        System.arraycopy(this.values, start, this.values, start + shift, end - start);
    }

    @Override public boolean isEmpty() {
        return this.size == 0;
    }

    @Override public String asString() {
        var builder = new StringBuilder("{");
        var keys = this.keys;
        var values = this.values;

        for (int i = 0, size = this.size; i < size; i++) {
            builder.append(String.format("{%s: %s}", keys[i], values[i]));

            if (i != size - 1) {
                builder.append(", ");
            }
        }

        return builder.append('}').toString();
    }

    @Override public void clear() {
        this.keys = ArrayUtil.create(this.length);
        this.values = ArrayUtil.create(this.length);
        this.size = 0;
    }

    @Override public Iterator<K> iterator() {
        return new ArrayMapIterator();
    }

    @Override public ArraySet<Map.Entry<K, V>> entrySet() {
        var entries = new ArraySet<Map.Entry<K, V>>();
        var keys = this.keys;
        var values = this.values;

        for (int i = 0, size = this.size; i < size; i++) {
            entries.add(new Entry<>(keys[i], values[i]));
        }

        return entries;
    }

    protected class ArrayMapIterator implements Iterator<K> {
        public int index = 0;
        public int lastReturned = -1;

        @Override public boolean hasNext() {
            return this.index < ArrayMap.this.size;
        }

        @Override public K next() {
            var index = this.index;

            if (index >= ArrayMap.this.size) {
                throw new NoSuchElementException();
            }

            if (index >= ArrayMap.this.length) {
                throw new ConcurrentModificationException();
            }

            return ArrayMap.this.keys[this.lastReturned = this.index++];
        }

        @Override public void remove() {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            }

            try {
                var returned = this.lastReturned;

                ArrayMap.this.remove(returned);

                this.index = this.lastReturned;
                this.lastReturned = -1;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);

            var size = ArrayMap.this.size;
            var index = this.index;

            if (index < size) {
                if (index >= ArrayMap.this.length) {
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

    public static class Entry<K, V> implements Map.Entry<K, V>, Stringifiable {
        protected K key;
        protected V value;

        public Entry(K key) {
            this.key = key;
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override public K getKey() {
            return this.key;
        }

        @Override public V getValue() {
            return this.value;
        }

        @Override public V setValue(V value) {
            var old = this.value;

            this.value = value;
            return old;
        }

        @Override public String asString() {
            return String.format("{%s: %s}", this.key, this.value);
        }
    }
}
