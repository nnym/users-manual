package user11681.usersmanual.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import user11681.usersmanual.reflect.ConstructorUtil;
import user11681.usersmanual.reflect.FieldUtil;

public class AttributeModifierOperations {
    public static final EntityAttributeModifier.Operation PERCENTAGE_ADDITION = register("ADD_PERCENTAGE", EntityAttributeModifier.Operation.values().length);

    public static EntityAttributeModifier.Operation register(final String name, final int id) {
        final EntityAttributeModifier.Operation operation = ConstructorUtil.addEnumInstance(EntityAttributeModifier.Operation.values(), name, id);
        final Class<EntityAttributeModifier.Operation> clazz = EntityAttributeModifier.Operation.class;

        for (final Field field : clazz.getDeclaredFields()) {
            final int modifiers = field.getModifiers();

            if (field.getType().getComponentType() == clazz && Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && !field.isSynthetic()) {
                FieldUtil.addToArray(field, operation);
            }
        }

        return operation;
    }
}
