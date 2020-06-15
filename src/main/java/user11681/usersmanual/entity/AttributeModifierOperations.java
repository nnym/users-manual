package user11681.usersmanual.entity;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import user11681.usersmanual.reflect.ReflectUtil;

public class AttributeModifierOperations {
    public static final EntityAttributeModifier.Operation ADD_PERCENTAGE = ReflectUtil.newEnumInstance(EntityAttributeModifier.Operation.values(), "ADD_PERCENTAGE", EntityAttributeModifier.Operation.values().length);
}
