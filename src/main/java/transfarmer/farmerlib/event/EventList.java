package transfarmer.farmerlib.event;

import transfarmer.farmerlib.event.Event.Listener;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static transfarmer.farmerlib.event.EventPriority.FIVE;

public class EventList<E extends Event<?>> implements Iterable<Listener<E>> {
    protected final List<Listener<E>> delegate;

    public EventList() {
        this.delegate = new ArrayList<>();
    }

    public void add(final Class<E> clazz, final Consumer<E> consumer) {
        this.add(clazz, consumer, FIVE);
    }

    public void add(final Class<E> clazz, final Consumer<E> consumer, final int priority) {
        this.add(clazz, consumer, priority, false);
    }

    public void add(final Class<E> clazz, final Consumer<E> consumer, final int priority, final boolean persist) {
        final Listener<E> listener = new Listener<>(clazz, consumer, priority);
        int index = 0;

        for (int i = 0, listenersSize = this.delegate.size(); i < listenersSize; i++) {
            final Listener<E> other = this.delegate.get(i);

            if (other.equals(listener)) {
                final int comparison = listener.compareTo(other);

                if (comparison > 0) {
                    index = i + 1;
                } else
                    index = i;

                if (comparison < 0) {
                    break;
                }
            }
        }

        this.delegate.add(index, listener);
    }

    @Override
    @Nonnull
    public Iterator<Listener<E>> iterator() {
        return this.delegate.iterator();
    }
}
