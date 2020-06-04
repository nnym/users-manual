package user11681.usersmanual.registry;

import net.minecraft.util.Identifier;

public abstract class AbstractRegistryEntry implements RegistryEntry {
    protected final Identifier identifier;

    public AbstractRegistryEntry(final Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }
}
