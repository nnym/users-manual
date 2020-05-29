package user11681.usersmanual.registry;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.util.Identifier;

public class ModRegistry<T extends RegistryEntry> implements Iterable<T> {
    protected final Map<Identifier, T> entries;

    public ModRegistry() {
        this.entries = new Reference2ReferenceOpenHashMap<>();
    }

    public T register(final T entry) {
        this.entries.put(entry.getIdentifier(), entry);

        return entry;
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
