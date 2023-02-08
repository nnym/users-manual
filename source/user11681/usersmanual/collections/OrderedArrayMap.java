package user11681.usersmanual.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class OrderedArrayMap<K, V> extends ArrayMap<K, V> {
    public OrderedArrayMap(Map<K, V> map) {
        super(map);
    }

    public OrderedArrayMap(Supplier<V> defaultValueSupplier, Iterable<K> keys) {
        super(defaultValueSupplier, keys);
    }

    @SafeVarargs
    public OrderedArrayMap(Supplier<V> defaultValueSupplier, K... keys) {
        super(defaultValueSupplier, keys);
    }

    public OrderedArrayMap() {
        super();
    }

    public OrderedArrayMap(int initialLength) {
        super(initialLength);
    }

    @Override public void putAll(Map<? extends K, ? extends V> map) {
        var size = this.size;

        if (size + map.size() >= this.length) {
            this.resize(size * 2);
        }

        var keys = map.keySet().iterator();
        var values = map.values().iterator();

        while (keys.hasNext()) {
            var key = keys.next();

            var index = this.indexOfKey(key);

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

    @Override public V put(K key, V value) {
        var size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        var index = this.indexOfKey(key);

        V previous;

        if (index < 0) {
            index = size;
            ++this.size;
            previous = null;
            this.keys[index] = key;
        } else {
            previous = this.values[index];
        }

        this.values[index] = value;

        return previous;
    }

    @Override public int indexOfKey(Object target) {
        var keys = this.keys;
        var size = this.size;

        for (var index = 0; index < size; index++) {
            if (keys[index].equals(target)) {
                return index;
            }
        }

        return -size - 1;
    }

    @Override public int indexOfValue(Object target) {
        var values = this.values;
        var index = 0;

        for (var size = this.size; index < size; index++) {
            if (values[index].equals(target)) {
                return index;
            }
        }

        return -index - 1;
    }

    public int lastIndexOfKey(Object target) {
        var keys = this.keys;
        var size = this.size;
        var index = -size - 1;

        for (var i = 0; i < size; i++) {
            if (keys[i].equals(target)) {
                index = i;
            }
        }

        return index;
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
