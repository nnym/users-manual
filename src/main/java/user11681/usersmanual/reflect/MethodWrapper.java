package user11681.usersmanual.reflect;

import user11681.usersmanual.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodWrapper<R, O> {
    protected final Method method;
    protected final O object;

    public MethodWrapper(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
        this(ReflectUtil.getLowestMethod(clazz, name, parameterTypes));
    }

    public MethodWrapper(final Method method) {
        this.method = method;
        this.method.setAccessible(true);
        this.object = null;
    }

    public MethodWrapper(final O object, final String name, final Class<?>... parameterTypes) {
        this.method = ReflectUtil.getLowestMethod(object.getClass(), name, parameterTypes);
        this.method.setAccessible(true);
        this.object = object;
    }

    @SuppressWarnings("unchecked")
    public R invoke(final O object, final Object... args) {
        try {
            return (R) this.method.invoke(object, args);
        } catch (final IllegalAccessException | InvocationTargetException exception) {
            Main.LOGGER.error("An error occurred in an attempt to invoke a wrapped method", exception);
        }

        return null;
    }

    public R invoke(final Object... args) {
        return this.invoke(this.object, args);
    }
}
