package user11681.usersmanual.reflect;

import java.lang.reflect.Modifier;

public class Modifiers {
    public static boolean isPublicStaticConstant(final int modifiers) {
        return Modifier.isPublic(modifiers) && isStaticConstant(modifiers);
    }

    public static boolean isStaticConstant(final int modifiers) {
        return Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }
}
