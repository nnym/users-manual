package user11681.usersmanual.registry;

import net.minecraft.util.Identifier;

public interface RegistryEntry {
    Identifier getIdentifier();

    default String stringIdentifier() {
        return this.getIdentifier().toString();
    }
}
