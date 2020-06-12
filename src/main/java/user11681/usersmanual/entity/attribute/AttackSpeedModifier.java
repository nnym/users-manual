package user11681.usersmanual.entity.attribute;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import user11681.usersmanual.item.ItemModifiers;

public class AttackSpeedModifier extends EntityAttributeModifier {
    public AttackSpeedModifier(final String name, final double value, final Operation operation) {
        super(ItemModifiers.getAttackSpeedModifier(), name, value, operation);
    }

    public AttackSpeedModifier(final Supplier<String> nameGetter, final double value, final Operation operation) {
        super(ItemModifiers.getAttackSpeedModifier(), nameGetter, value, operation);
    }

    @Override
    public UUID getId() {
        return ItemModifiers.getAttackSpeedModifier();
    }
}
