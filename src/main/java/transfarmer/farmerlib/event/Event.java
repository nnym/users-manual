package transfarmer.farmerlib.event;

import net.minecraft.util.ActionResult;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static net.minecraft.util.ActionResult.PASS;

/**
 * the base class used for events.
 */
public abstract class Event<E extends Event<?>> {
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

    protected static class Listener<E> implements Comparable<Listener<E>> {
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
}
