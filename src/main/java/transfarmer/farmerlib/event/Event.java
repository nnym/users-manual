package transfarmer.farmerlib.event;

import net.minecraft.util.ActionResult;
import transfarmer.farmerlib.collection.CollectionUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.util.ActionResult.PASS;
import static transfarmer.farmerlib.event.Event.Priority.FIVE;

/**
 * the base class used for events.
 */
public abstract class Event {
    protected ActionResult result;

    public Event() {
        this.result = PASS;
    }

    public ActionResult getResult() {
        return this.result;
    }

    public void setResult(final ActionResult result) {
        this.result = result;
    }

    protected static <E extends Event> EventList<E> createList() {
        return new EventList<>();
    }

    protected static class EventList<E extends Event> implements Iterable<Listener<E>> {
        protected final List<Listener<E>> delegate;

        public EventList() {
            this.delegate = new ArrayList<>();
        }

        public void add(final Class<E> clazz, final Consumer<E> value) {
            this.add(clazz, value, FIVE);
        }

        public void add(final Class<E> clazz, final Consumer<E> value, final int priority) {
            final Listener<E> listener = new Listener<>(clazz, value, priority);
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

    protected static class Listener<E extends Event> implements Comparable<Listener<E>> {

        protected final Class<E> clazz;
        protected final Consumer<E> consumer;
        protected final int priority;

        protected Listener(final Class<E> eventClass, final Consumer<E> consumer, final int priority) {
            this.clazz = eventClass;
            this.consumer = consumer;
            this.priority = priority;
        }

        protected Class<E> getEventClass() {
            return this.clazz;
        }

        protected Consumer<E> getConsumer() {
            return this.consumer;
        }

        protected int getPriority() {
            return this.priority;
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof Listener && ((Listener<?>) other).getEventClass() == this.getEventClass();
        }

        @Override
        public int compareTo(@Nonnull final Listener<E> other) {
            return Integer.compare(this.priority, other.priority);
        }

        public void accept(final E event) {
            this.consumer.accept(event);
        }
    }

    public static class Priority {
        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
        public static final int FIVE = 5;
        public static final int SIX = 6;
        public static final int SEVEN = 7;
        public static final int EIGHT = 8;
        public static final int NINE = 9;
        public static final int TEN = 10;

        public static List<Integer> getPriorities() {
            return CollectionUtil.arrayList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);
        }
    }
}
