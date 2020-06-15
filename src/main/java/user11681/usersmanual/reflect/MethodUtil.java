package user11681.usersmanual.reflect;

import java.lang.reflect.Method;

public class MethodUtil {
    public static Method getLowestMethod(final Object object, final String methodName,
                                         final Class<?>... parameterTypes) {
        return getLowestMethod(object.getClass(), methodName, parameterTypes);
    }

    public static Method getLowestMethod(final Class<?> clazz, final String methodName,
                                         final Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException exception) {
            final Class<?> superclass = clazz.getSuperclass();

            if (superclass == null) {
                throwException(exception);
            }

            return getLowestMethod(superclass, methodName, parameterTypes);
        }
    }

    protected static void throwException(final Exception exception) {
        throw new ReflectionException(exception);
    }
}
