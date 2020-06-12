package user11681.usersmanual.entity.attribute;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import user11681.usersmanual.item.ItemModifiers;

public class AttackDamageModifier extends EntityAttributeModifier {
    public AttackDamageModifier(final String name, final double value, final Operation operation) {
        super(ItemModifiers.getAttackDamageModifier(), name, value, operation);
    }

    public AttackDamageModifier(final Supplier<String> nameGetter, final double value, final Operation operation) {
        super(ItemModifiers.getAttackDamageModifier(), nameGetter, value, operation);
    }

    @Override
    public UUID getId() {
        return ItemModifiers.getAttackDamageModifier();
    }
}
