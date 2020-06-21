package user11681.usersmanual.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import sun.reflect.ConstructorAccessor;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class Constructors {
    public static <T extends Enum<T>> T addEnumInstance(final T[] values, final String name, final Object... arguments) {
        return addEnumInstance(newEnumInstance(values, name, arguments));
    }

    public static <T extends Enum<T>> T addEnumInstance(final T instance) {
        final Class<?> clazz = instance.getClass();
        final Field field = getEnumArrayField(clazz);

        try {
            final Field modifierField = Field.class.getDeclaredField("modifiers");
            final int modifiers = field.getModifiers();

            field.setAccessible(true);
            modifierField.setAccessible(true);
            modifierField.setInt(field, modifiers & ~Modifier.FINAL);

            final T[] original = (T[]) field.get(null);
            final int length = original.length;
            final T[] newValues = Arrays.copyOf(original, length + 1);

            newValues[length] = instance;
            field.set(null, newValues);

            final Field enumConstants = Class.class.getDeclaredField("enumConstants");
            final Field enumConstantDirectory = Class.class.getDeclaredField("enumConstantDirectory");

            enumConstants.setAccessible(true);
            enumConstants.set(clazz, null);
            enumConstantDirectory.setAccessible(true);
            enumConstantDirectory.set(clazz, null);

            return instance;
        } catch (final IllegalAccessException | NoSuchFieldException exception) {
            throwException(exception);
        }

        return null;
    }

    public static <T extends Enum<T>> T newEnumInstance(final T[] values, final String name, final Object... arguments) {
        final int length = arguments.length;
        final int originalLength = values.length;
        final Object[] enumArguments = new Object[length + 2];

        enumArguments[0] = name;
        enumArguments[1] = originalLength;

        System.arraycopy(arguments, 0, enumArguments, 2, length);

        return (T) newInstance(values.getClass().getComponentType(), enumArguments);
    }

    public static Field getEnumArrayField(final Class<?> enumClass) {
        for (final Field field : enumClass.getDeclaredFields()) {
            final int modifiers = field.getModifiers();

            if (field.isSynthetic() && field.getType().getComponentType() == enumClass && Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                return field;
            }
        }

        return null;
    }

    public static <T> T newInstance(final Class<T> clazz, final Object... arguments) {
        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            final T instance = newInstance(constructor, arguments);

            if (instance != null) {
                return instance;
            }
        }

        throw new IllegalArgumentException(String.format("%s constructor with parameters of types %s was not found.", clazz.getName(),
                Arrays.toString(Arrays.stream(arguments).map(Object::getClass).toArray())
        ));
    }

    public static <T> T newInstance(final Constructor<?> constructor, final Object... arguments) {
        constructor.setAccessible(true);

        try {
            final Field constructorAccessor = Constructor.class.getDeclaredField("constructorAccessor");

            constructorAccessor.setAccessible(true);

            final Object accessor = constructorAccessor.get(constructor);

            if (accessor == null) {
                final Method acquireConstructorAccessor = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");

                acquireConstructorAccessor.setAccessible(true);

                return (T) ((ConstructorAccessor) acquireConstructorAccessor.invoke(constructor)).newInstance(arguments);
            }

            return (T) (((ConstructorAccessor) accessor).newInstance(arguments));
        } catch (final IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException exception) {
            throwException(exception);
        }

        return null;
    }

    private static void throwException(final Throwable throwable) {
        throw new ReflectionException(throwable);
    }
}
