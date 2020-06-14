package user11681.usersmanual.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.item.Item;

public final class ItemModifiers extends Item {
    public static final List<UUID> RESERVED_IDENTIFIERS = new ArrayList<>();

    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = Item.ATTACK_DAMAGE_MODIFIER_ID;
    public static final UUID ATTACK_SPEED_MODIFIER_ID = Item.ATTACK_SPEED_MODIFIER_ID;

    static {
        RESERVED_IDENTIFIERS.add(ItemModifiers.ATTACK_DAMAGE_MODIFIER_ID);
        RESERVED_IDENTIFIERS.add(ItemModifiers.ATTACK_SPEED_MODIFIER_ID);
    }

    private ItemModifiers(final Settings settings) {
        super(settings);
    }
}
