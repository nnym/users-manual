package user11681.usersmanual.item;

import net.minecraft.item.Item;

import java.util.UUID;

public final class ItemModifiers extends Item {
    public static final UUID ATTACK_DAMAGE_MODIFIER_UUID = Item.ATTACK_DAMAGE_MODIFIER_UUID;
    public static final UUID ATTACK_SPEED_MODIFIER_UUID = Item.ATTACK_SPEED_MODIFIER_UUID;

    private ItemModifiers() {
        //noinspection ConstantConditions
        super(null);
    }
}
