package user11681.usersmanual.item;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;

public final class ItemModifiers extends Item {
    private ItemModifiers() {
        //noinspection ConstantConditions
        super(null);
    }

    public static EntityAttributeModifier createAttackSpeedModifier(final double value) {
        return createAttackSpeedModifier(value, EntityAttributeModifier.Operation.ADDITION);
    }

    public static EntityAttributeModifier createAttackSpeedModifier(final double value, final EntityAttributeModifier.Operation operation) {
        return createAttackSpeedModifier("Weapon modifier", value, operation);
    }

    public static EntityAttributeModifier createAttackSpeedModifier(final String name, final double value, final EntityAttributeModifier.Operation operation) {
        return new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, name, value, operation);
    }

    public static EntityAttributeModifier createAttackDamageModifier(final double value) {
        return createAttackDamageModifier(value, EntityAttributeModifier.Operation.ADDITION);
    }

    public static EntityAttributeModifier createAttackDamageModifier(final double value, final EntityAttributeModifier.Operation operation) {
        return createAttackDamageModifier("Weapon modifier", value, operation);
    }

    public static EntityAttributeModifier createAttackDamageModifier(final String name, final double value, final EntityAttributeModifier.Operation operation) {
        return new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, name, value, operation);
    }
}
