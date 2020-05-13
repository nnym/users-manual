package transfarmer.farmerlib.reflect;

import transfarmer.farmerlib.Main;
import transfarmer.farmerlib.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodWrapper<R, O> {
    protected final Method method;

    protected O object;

    public MethodWrapper(final O object, final String name, final Class<?>... parameterTypes) {
        this(object.getClass(), name, parameterTypes);

        this.object = object;
    }

    public MethodWrapper(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
        this(ReflectUtil.getLowestMethod(clazz, name, parameterTypes));
    }

    public MethodWrapper(final Method method) {
        this.method = method;
        this.method.setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    public R invoke(final O object, final Object... args) {
        try {
            return (R) this.method.invoke(object, args);
        } catch (final IllegalAccessException | InvocationTargetException exception) {
            Main.LOGGER.error(exception);
        }

        return null;
    }

    public R invoke(final Object... args) {
        return this.invoke(this.object, args);
    }
}
