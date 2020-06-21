package user11681.usersmanual.reflect;

import user11681.usersmanual.Main;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class FieldWrapper<F, O> {
    protected final Field field;
    protected final O object;

    public FieldWrapper(final O object, final String field) {
        this((Class<? super O>) object.getClass(), object, field);
    }

    public FieldWrapper(final Class<? super O> clazz, final O object, final String field) {
        this(object, Fields.getLowestField(clazz, field));
    }

    public FieldWrapper(final O object, final Field field) {
        this.field = field;
        this.object = object;

        this.setAccessible();
    }

    public FieldWrapper(final Class<?> clazz, final String field) {
        this(Fields.getLowestField(clazz, field));
    }

    public FieldWrapper(final Field field) {
        this.field = field;
        this.object = null;

        this.setAccessible();
    }

    @Nonnull
    public F get() {
        try {
            return (F) this.field.get(this.object);
        } catch (final IllegalAccessException exception) {
            Main.LOGGER.error(exception);
        }

        return null;
    }

    public O set(final Object value) {
        try {
            this.field.set(this.object, value);
        } catch (final IllegalAccessException exception) {
            Main.LOGGER.error(exception);
        }

        return this.object;
    }

    protected void setAccessible() {
        if (this.object == null) {
            Fields.setField(this.field, "modifiers", this.field.getModifiers() & ~Modifier.FINAL);
        }

        this.field.setAccessible(true);
    }
}
