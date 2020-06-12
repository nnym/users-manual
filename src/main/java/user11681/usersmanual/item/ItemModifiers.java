package user11681.usersmanual.item;

import java.util.UUID;
import net.minecraft.item.Item;

public final class ItemModifiers extends Item {
    public static UUID getAttackDamageModifier() {
        return Item.ATTACK_DAMAGE_MODIFIER_ID;
    }

    public static UUID getAttackSpeedModifier() {
        return Item.ATTACK_SPEED_MODIFIER_ID;
    }

    private ItemModifiers() {
        //noinspection ConstantConditions
        super(null);
    }
}
