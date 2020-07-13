package user11681.usersmanual;

import java.util.Collection;
import java.util.Collections;
import user11681.anvil.entrypoint.CommonListenerInitializer;

public class Events implements CommonListenerInitializer {
    @Override
    public Collection<Class<?>> get() {
        return Collections.singleton(this.getClass());
    }
}
