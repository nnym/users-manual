package user11681.usersmanual.collections;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;

public class OrderedArrayMap<K, V> extends ArrayMap<K, V> {
    public OrderedArrayMap() {
        super();
    }

    public OrderedArrayMap(final int initialLength) {
        super(initialLength);
    }

    public OrderedArrayMap(final Map<K, V> map) {
        super(map);
    }

    @SafeVarargs
    public OrderedArrayMap(final V defaultValue, final K... keys) {
        super(defaultValue, keys);
    }

    public OrderedArrayMap(final V defaultValue, final Iterable<K> keys) {
        super(defaultValue, keys);
    }

    @Override
    public void putAll(@Nonnull final Map<? extends K, ? extends V> map) {
        final int size = this.size;

        if (size + map.size() >= this.length) {
            this.resize(size * 2);
        }

        final Iterator<? extends K> keys = map.keySet().iterator();
        final Iterator<? extends V> values = map.values().iterator();

        while (keys.hasNext()) {
            final K key = keys.next();

            int index = this.indexOfKey(key);

            if (index < 0) {
                index = -index - 1;
            }

            ++this.size;

            if (index < size) {
                this.shift(1, index, size);
            }

            this.keys[index] = key;
            this.values[index] = values.next();
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
        }

        ++this.size;

        if (index < size) {
            this.shift(1, index, size);
        }

        V previous = this.values[index];

        this.keys[index] = key;
        this.values[index] = value;

        return previous;
    }

    @Override
    public int indexOfKey(final Object target) {
        final K[] keys = this.keys;
        int index;

        for (index = 0, length = this.size; index < length; index++) {
            final K key = keys[index];

            if (key.equals(target)) {
                return index;
            }
        }

        return -index - 1;
    }

    @Override
    public int indexOfValue(final Object target) {
        final V[] values = this.values;
        int index;

        for (index = 0, length = this.size; index < length; index++) {
            if (values[index].equals(target)) {
                return index;
            }
        }

        return -index - 1;
    }

    public int lastIndexOfKey(final Object target) {
        final K[] keys = this.keys;
        int index = -1;

        for (int i = 0, length = this.size; i < length; i++) {
            if (keys[i].equals(target)) {
                index = i;
            }
        }

        return index == -1 ? -this.size - 1 : index;
    }

    @Override
    public int lastIndexOfValue(final Object target) {
        final V[] values = this.values;
        int index = -1;

        for (int i = 0, length = this.size; i < length; i++) {
            if (values[i].equals(target)) {
                index = i;
            }
        }

        return index == -1 ? -this.size -1 : index;
    }
}
