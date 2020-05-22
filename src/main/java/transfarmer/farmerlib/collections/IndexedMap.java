package transfarmer.farmerlib.collections;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

@Deprecated
public interface IndexedMap<K, V> extends Map<K, V>, Iterable<K> {
    @Nonnull
    List<K> keyList();

    @Nonnull
    List<V> valueList();

    K getKey(int index);

    V getValue(int index);

    int indexOfKey(K key);
}
