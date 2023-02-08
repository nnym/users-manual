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
    public static <T, U extends Collection<T>> List<T> merge(U... from) {
        var to = new ArrayList<T>();

        for (var collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    public static <T, U extends Collection<T>> List<T> merge(List<U> from) {
        var to = new ArrayList<T>();

        for (var collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(T[]... from) {
        var to = new ArrayList<T>();

        for (var array : from) {
            to.addAll(arrayList(array));
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(T... from) {
        var to = new ArrayList<T>();

        for (var element : from) {
            to.add(element);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(Collection<T> collection, T... elements) {
        var to = new ArrayList<>(collection);

        for (var element : elements) {
            to.add(element);
        }

        return to;
    }

    @SafeVarargs
    public static <T> List<T> arrayList(Collection<T>... from) {
        var to = new ArrayList<T>();

        for (var collection : from) {
            to.addAll(collection);
        }

        return to;
    }

    @SafeVarargs
    public static <T> Set<T> hashSet(T... from) {
        var to = new HashSet<T>(from.length);

        for (var element : from) {
            to.add(element);
        }

        return to;
    }

    public static <K, V> Map<K, V> hashMap(K key, V value) {
        var to = new HashMap<K, V>(1);
        to.put(key, value);

        return to;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> hashMap(K[] keys, V... values) {
        return hashMap(new HashMap<>(keys.length, 1), keys, values);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> hashMap(Map<K, V> original, K[] keys, V... values) {
        for (var i = 0; i < keys.length; i++) {
            original.put(keys[i], values[i]);
        }

        return original;
    }

    @SafeVarargs
    public static <T> void addAll(Collection<T> collection, T... elements) {
        for (var element : elements) {
            collection.add(element);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list) {
        return (T[]) list.toArray();
    }
}
