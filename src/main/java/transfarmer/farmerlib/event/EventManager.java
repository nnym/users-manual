package transfarmer.farmerlib.event;

import java.util.function.Consumer;

public class EventManager<E extends Event<?>> {
    protected final EventList<E> listeners;
    protected final Class<E> listenerClass;

    protected EventManager(final Class<E> listenerClass) {
        this.listeners = new EventList<>();
        this.listenerClass = listenerClass;
    }

    protected EventList<E> getListeners() {
        return this.listeners;
    }

    public void register(final Consumer<E> listener) {
        this.register(listener, EventPriority.FIVE);
    }

    public void register(final Consumer<E> listener, final int priority) {
        this.register(listener, priority, false);
    }

    public void register(final Consumer<E> listener, final int priority, final boolean persist) {
        listeners.add(this.listenerClass, listener, priority, persist);
    }
}
