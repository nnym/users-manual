package user11681.farmerlib.collections;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

@VisibleForTesting
public interface ParallelList<K, V> extends Iterable<K>, MeaningfulString {
    @Nonnull
    List<K> keyList();

    @Nonnull
    List<V> valueList();

    K getKey(int index);

    V getValue(int index);

    void addAll(Map<K, V> map);

    boolean add(K key, V value);

    int indexOfFirstKey(K target);

    int indexOfFirstValue(V target);

    int indexOfLastKey(K target);

    int indexOfLastValue(V target);

    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);

    V remove(K target);

    V remove(int index);

    void trimToSize();

    void clear();
}
