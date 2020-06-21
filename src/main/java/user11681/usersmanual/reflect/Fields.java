package user11681.usersmanual.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import user11681.usersmanual.Main;
import user11681.usersmanual.collections.CollectionUtil;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class Fields {
    public static void addToArray(final String name, final Object owner, final Object newElement) {
        addToArray(owner.getClass(), owner, name, newElement);
    }

    public static void addToArray(final Class<?> clazz, final String name, final Object newElement) {
        addToArray(clazz, null, name, newElement);
    }

    public static void addToArray(final Class<?> clazz, final Object owner, final String name, final Object newElement) {
        addToArray(getLowestField(clazz, name), owner, newElement);
    }

    public static void addToArray(final Field field, final Object newElement) {
        addToArray(field, null, newElement);
    }

    public static void addToArray(final Field field, final Object owner, Object newElement) {
        try {
            final Field modifiers = Field.class.getDeclaredField("modifiers");

            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.setAccessible(true);

            final Object[] original = (Object[]) field.get(owner);
            final int length = original.length;
            final Object[] newArray = Arrays.copyOf(original, length + 1);

            newArray[length] = newElement;

            field.set(owner, newArray);
        } catch (final IllegalAccessException | NoSuchFieldException exception) {
            throwException(exception);
        }
    }

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

    public static List<Field> getAllFields(final Class<?> clazz) {
        final List<Field> fields = CollectionUtil.arrayList(clazz.getDeclaredFields());
        final Class<?> superclass = clazz.getSuperclass();

        if (superclass != null) {
            fields.addAll(getAllFields(superclass));
        }

        return fields;
    }

    public static Field makeAccessible(final Field field) {
        final Field modifiers = getDeclaredField(Field.class, "modifiers");

        field.setAccessible(true);
        modifiers.setAccessible(true);
        setInt(modifiers, field, field.getModifiers() & ~Modifier.FINAL);

        return field;
    }

    public static Field getLowestField(final Object object, final String name) {
        return getLowestField(object.getClass(), name);
    }

    public static Field getLowestField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException exception) {
            final Class<?> superclass = clazz.getSuperclass();

            if (superclass == null) {
                throwException(exception);
            }

            return getLowestField(superclass, name);
        }
    }

    public static Field getDeclaredField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException exception) {
            throwException(exception);
        }

        return null;
    }

    public static void setInt(final Field field, final Object object, final int value) {
        try {
            field.setInt(object, value);
        } catch (final IllegalAccessException exception) {
            throwException(exception);
        }
    }

    public static void set(final Field field, final Object object, final Object value) {
        try {
            field.set(object, value);
        } catch (final IllegalAccessException exception) {
            throwException(exception);
        }
    }

    private static void throwException(final Throwable throwable) {
        throw new ReflectionException(throwable);
    }
}
