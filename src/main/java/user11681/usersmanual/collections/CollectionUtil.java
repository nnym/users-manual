package user11681.usersmanual.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
public class CollectionUtil {
    @SafeVarargs
    public static <T, U extends Collection<T>> List<T> merge(final U... from) {
        final List<T> to = new ArrayList<>();

        for (final U collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    public static <T, U extends Collection<T>> List<T> merge(final List<U> from) {
        final List<T> to = new ArrayList<>();

        for (U collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(final T[]... from) {
        final List<T> to = new ArrayList<>();

        for (final T[] array : from) {
            to.addAll(arrayList(array));
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(final T... from) {
        final List<T> to = new ArrayList<>();

        for (final T element : from) {
            to.add(element);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(final Collection<T> collection, final T... elements) {
        final List<T> to = new ArrayList<>(collection);

        for (final T element : elements) {
            to.add(element);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(final Collection<T>... from) {
        final List<T> to = new ArrayList<>();

        for (final Collection<T> collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    @SafeVarargs
    public static <T> Set<T> hashSet(final T... from) {
        final Set<T> to = new HashSet<>(from.length);

        for (final T element : from) {
            to.add(element);
        }

        return to;
    }

    public static <K, V> Map<K, V> hashMap(final K key, final V value) {
        final Map<K, V> to = new HashMap<>(1);

        to.put(key, value);

        return to;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> hashMap(final K[] keys, final V... values) {
        final Map<K, V> to = new HashMap<>(keys.length, 1);

        return hashMap(to, keys, values);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> hashMap(final Map<K, V> original, final K[] keys, final V... values) {
        for (int i = 0; i < keys.length; i++) {
            original.put(keys[i], values[i]);
        }

        return original;
    }

    public static <K, V> IndexedMap<K, V> indexedLinkedHashMap(final K key, final V value) {
        final IndexedMap<K, V> to = new IndexedLinkedHashMap<>(1);

        to.put(key, value);

        return to;
    }

    @SafeVarargs
    public static <T> void addAll(final Collection<T> collection, final T... elements) {
        for (final T element : elements) {
            collection.add(element);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(final List<T> list) {
        return (T[]) list.toArray();
    }
}
