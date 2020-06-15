package user11681.usersmanual.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import sun.reflect.ConstructorAccessor;
import user11681.usersmanual.Main;
import user11681.usersmanual.collections.CollectionUtil;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class ReflectUtil {
    public static <U> U getFieldValue(final Object object, final String name) {
        return getFieldValue(object.getClass(), object, name);
    }

    public static <U> U getFieldValue(final Class<?> clazz, final String name) {
        return getFieldValue(clazz, null, name);
    }

    @Nonnull
    public static <U> U getFieldValue(final Class<?> clazz, final Object object, final String name) {
        return getFieldValue(object, getLowestField(clazz, name));
    }

    public static <T> T getFieldValue(final Field field) {
        return getFieldValue(null, field);
    }

    @Nonnull
    public static <T> T getFieldValue(final Object object, final Field field) {
        try {
            final T value;

            field.setAccessible(true);
            value = (T) field.get(object);
            field.setAccessible(false);

            return value;
        } catch (final IllegalAccessException exception) {
            Main.LOGGER.error(exception);
        }

        return null;
    }

    public static void setField(final Object object, final String name, final Object value) {
        setField(object.getClass(), object, name, value);
    }

    public static void setField(final Class<?> clazz, final String name, final Object value) {
        setField(clazz, null, name, value);
    }

    public static void setField(final Class<?> clazz, final Object object, final String name, final Object value) {
        try {
            final Field field = getLowestField(clazz, name);

            if (object == null) {
                setField(field, "modifiers", field.getModifiers() & ~Modifier.FINAL);
            }

            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (final IllegalAccessException exception) {
            Main.LOGGER.error(exception);
        }
    }

    public static Method getLowestMethod(final Object object, final String methodName,
                                         final Class<?>... parameterTypes) {
        return getLowestMethod(object.getClass(), methodName, parameterTypes);
    }

    public static Method getLowestMethod(final Class<?> clazz, final String methodName,
                                         final Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException exception) {
            return getLowestMethod(clazz.getSuperclass(), methodName, parameterTypes);
        }
    }

    public static Field getLowestField(final Object object, final String name) {
        return getLowestField(object.getClass(), name);
    }

    public static Field getLowestField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException exception) {
            return getLowestField(clazz.getSuperclass(), name);
        }
    }

    public static List<Field> getAllFields(final Class<?> clazz) {
        final List<Field> fields = CollectionUtil.arrayList(clazz.getDeclaredFields());
        final Class<?> superclass = clazz.getSuperclass();

        if (superclass != null) {
            fields.addAll(getAllFields(superclass));
        }

        return fields;
    }

    public static <T extends Enum<T>> T newEnumInstance(final T[] values, final String name, final Object... arguments) {
        final int length = arguments.length;
        final Object[] enumArguments = new Object[length + 2];

        enumArguments[0] = name;
        enumArguments[1] = values.length;

        System.arraycopy(arguments, 0, enumArguments, 2, length);

        return (T) newInstance(values.getClass().getComponentType(), enumArguments);
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
            final ConstructorAccessor accessor = getFieldValue(constructor, "constructorAccessor");

            if (accessor == null) {
                return (T) new MethodWrapper<ConstructorAccessor, Constructor<?>>(constructor, "acquireConstructorAccessor").invoke().newInstance(arguments);
            }

            return (T) accessor.newInstance(arguments);
        } catch (final IllegalArgumentException | InstantiationException | InvocationTargetException ignored) {
            return null;
        }
    }
}
