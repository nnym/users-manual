package transfarmer.farmerlib.reflect;

import transfarmer.farmerlib.Main;

import java.lang.reflect.Constructor;

public class ConstructorWrapper<T> {
    protected final Constructor<T> constructor;

    public ConstructorWrapper(final Class<T> clazz, final Class<?>... parameterTypes) {
        this(getConstructor(clazz, parameterTypes));
    }

    public ConstructorWrapper(final Constructor<T> constructor) {
        this.constructor = constructor;
        this.constructor.setAccessible(true);
    }

    public static <C> Constructor<C> getConstructor(final Class<C> clazz, final Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (final NoSuchMethodException exception0) {
            try {
                return clazz.getConstructor(parameterTypes);
            } catch (final NoSuchMethodException exception1) {
                Main.LOGGER.error(exception1);
            }
        }

        return null;
    }
}
