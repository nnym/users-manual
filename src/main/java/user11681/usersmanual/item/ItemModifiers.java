package user11681.usersmanual.item;

import net.minecraft.item.Item;

import java.util.UUID;

public final class ItemModifiers extends Item {
    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = Item.ATTACK_DAMAGE_MODIFIER_ID;
    public static final UUID ATTACK_SPEED_MODIFIER_ID = Item.ATTACK_SPEED_MODIFIER_ID;

    private ItemModifiers() {
        //noinspection ConstantConditions
        super(null);
    }
}
