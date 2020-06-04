package user11681.usersmanual.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.util.Identifier;

public class ModRegistry<T extends RegistryEntry> implements Iterable<T> {
    protected final Map<Identifier, T> entries;

    public ModRegistry() {
        this.entries = new HashMap<>();
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

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.entries.values().iterator();
    }
}
