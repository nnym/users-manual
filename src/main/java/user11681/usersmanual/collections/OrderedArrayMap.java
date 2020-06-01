package user11681.usersmanual.collections;

import com.google.common.annotations.VisibleForTesting;

@VisibleForTesting
public class OrderedArrayMap<K, V> extends ArrayMap<K, V> {
    @Override
    public boolean add(final K key, final V value) {
        final int size = this.size;

        if (size == this.length) {
            this.resize(2 * size);
        }

        this.keys[size] = key;
        this.values[size] = value;

        return true;
    }

    @Override
    public int indexOfFirstKey(final K target) {
        final K[] keys = this.keys;

        for (int index = 0, length = keys.length; index < length; index++) {
            final K key = keys[index];

            if (key.equals(target)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int indexOfFirstValue(final V target) {
        final V[] values = this.values;

        for (int i = 0, length = values.length; i < length; i++) {
            if (values[i].equals(target)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int indexOfLastKey(final K target) {
        final K[] keys = this.keys;

        int index = -1;

        for (int i = 0, length = keys.length; i < length; i++) {
            if (keys[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public int indexOfLastValue(final V target) {
        final V[] values = this.values;

        int index = -1;

        for (int i = 0, length = values.length; i < length; i++) {
            if (values[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public boolean containsKey(final K key) {
        return this.indexOfFirstKey(key) > -1;
    }

    @Override
    public boolean containsValue(final V value) {
        return this.indexOfFirstValue(value) > -1;
    }
}
