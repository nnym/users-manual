package user11681.usersmanual.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

@Deprecated
public class IndexedLinkedHashMap<K, V> extends HashMap<K, V> implements IndexedMap<K, V> {
    public IndexedLinkedHashMap() {
        super();
    }

    public IndexedLinkedHashMap(final int initialCapacity) {
        super(initialCapacity);
    }

    @Nonnull
    @Override
    public List<K> keyList() {
        return new ArrayList<>(this.keySet());
    }

    @Override
    @Nonnull
    public List<V> valueList() {
        return new ArrayList<>(this.values());
    }

    @Override
    public K getKey(final int index) {
        return this.keyList().get(index);
    }

    @Override
    public V getValue(final int index) {
        return this.valueList().get(index);
    }

    @Override
    public int indexOfKey(final K key) {
        return this.keyList().indexOf(key);
    }

    @Override
    @Nonnull
    public Iterator<K> iterator() {
        return this.keyList().iterator();
    }
}
