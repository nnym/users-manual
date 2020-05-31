package user11681.usersmanual.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import user11681.usersmanual.Main;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class ReflectUtil {
    public static <U> U getFieldValue(final Object object, final String fieldName) {
        return getFieldValue(object.getClass(), object, fieldName);
    }

    @Nonnull
    public static <U> U getFieldValue(final Class<?> clazz, final Object object, final String fieldName) {
        return getFieldValue(object, getLowestField(clazz, fieldName));
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

    public static void setField(final Object object, final String fieldName, final Object value) {
        setField(object.getClass(), object, fieldName, value);
    }

    public static void setField(final Class<?> clazz, final String fieldName, final Object value) {
        setField(clazz, null, fieldName, value);
    }

    public static void setField(final Class<?> clazz, final Object object, final String fieldName, final Object value) {
        try {
            final Field field = getLowestField(clazz, fieldName);

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

    public static Field getLowestField(final Object object, final String fieldName) {
        return getLowestField(object.getClass(), fieldName);
    }

    public static Field getLowestField(final Class<?> clazz, final String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException exception) {
            return getLowestField(clazz.getSuperclass(), fieldName);
        }
    }

    public static List<Field> getAllFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        final Class<?> superclass = clazz.getSuperclass();

        if (superclass != null) {
            fields.addAll(getAllFields(superclass));
        }

        return fields;
    }
}
