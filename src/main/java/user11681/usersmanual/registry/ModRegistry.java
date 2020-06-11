package user11681.usersmanual.registry;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.util.Identifier;
import user11681.usersmanual.collections.ArrayMap;
import user11681.usersmanual.collections.ArraySet;
import user11681.usersmanual.collections.OrderedArrayMap;

public class ModRegistry<T extends RegistryEntry> implements Iterable<T> {
    protected final ArrayMap<Identifier, T> entries;

    public ModRegistry() {
        this.entries = new OrderedArrayMap<>();
    }

    public <U extends T> U register(final U entry) {
        this.entries.put(entry.getIdentifier(), entry);

        return entry;
    }

    public T get(final String identifier) {
        return this.get(new Identifier(identifier));
    }

    public T get(final Identifier identifier) {
        return this.entries.get(identifier);
    }

    public ArraySet<Identifier> identifiers() {
        return this.entries.keySet();
    }

    public ArraySet<Map.Entry<Identifier, T>> entries() {
        return this.entries.entrySet();
    }

    public ArraySet<T> values() {
        return this.entries.values();
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.entries.values().iterator();
    }
}
