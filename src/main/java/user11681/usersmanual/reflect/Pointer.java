package user11681.usersmanual.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.annotation.Nonnull;

public class Pointer<T> {
    private final Object owner;
    private final Field field;

    private Pointer(final Class<?> clazz, final Object owner, final String name) {
        final Field field1 = getField(clazz, name);

        this.field = field1;
        this.owner = owner;

        field1.setAccessible(true);

        try {
            final Field modifiers = Field.class.getDeclaredField("modifiers");

            modifiers.setAccessible(true);
            modifiers.setInt(field1, field1.getModifiers() & ~Modifier.FINAL);
        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            throwException(exception);
        }
    }

    public static <T> Pointer<T> of(@Nonnull final Object owner, final String name) {
        return new Pointer<>(owner.getClass(), owner, name);
    }

    public static <T> Pointer<T> of(final Class<?> clazz, final String name) {
        return new Pointer<>(clazz, null, name);
    }

    public final T get() {
        try {
            //noinspection unchecked
            return (T) this.field.get(this.owner);
        } catch (final IllegalAccessException exception) {
            throwException(exception);
        }

        return null;
    }

    public final void set(final T value) {
        try {
            this.field.set(this.owner, value);
        } catch (final IllegalAccessException exception) {
            throwException(exception);
        }
    }

    private static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException exception) {
            final Class<?> superclass = clazz.getSuperclass();

            if (superclass == null) {
                throwException(exception);
            }

            return getField(superclass, name);
        }
    }

    private static void throwException(final Throwable throwable) {
        throw new ReflectionException(throwable);
    }
}
