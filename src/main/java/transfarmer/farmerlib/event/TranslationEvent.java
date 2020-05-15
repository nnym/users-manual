package transfarmer.farmerlib.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.util.ActionResult.FAIL;
import static net.minecraft.util.ActionResult.SUCCESS;

/**
 * This event is called when {@link I18n#translate} is called.
 * It allows listeners to modify translation key, arguments and return value of the method call.
 *
 * A {@link ActionResult#FAIL} result cancels the translation, returning the key.
 */
@Environment(EnvType.CLIENT)
public class TranslationEvent extends Event {
    protected static final EventList<TranslationEvent> LISTENERS = createList();

    protected String value;
    protected String key;
    protected List<Object> args;

    public TranslationEvent(final String value, final String key, final Object... args) {
        super();

        this.value = value;
        this.key = key;
        this.args = new ArrayList<>(Arrays.asList(args));
    }

    /**
     * @return the return value
     */
    public String getValue() {
        return this.value;
    }

    public String getKey() {
        return this.key;
    }

    public List<Object> getArgs() {
        return this.args;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public static void register(final Consumer<TranslationEvent> listener) {
        LISTENERS.add(TranslationEvent.class, listener);
    }

    public static void register(final Consumer<TranslationEvent> listener, final int priority) {
        LISTENERS.add(TranslationEvent.class, listener, priority);
    }

    public static TranslationEvent fire(final String value, final String key, final Object... args) {
        final TranslationEvent event = new TranslationEvent(value, key, args);

        for (final Listener<TranslationEvent> listener : LISTENERS) {
            if (event.getResult() == FAIL || event.getResult() == SUCCESS) {
                return event;
            }

            listener.accept(event);
        }

        return event;
    }
}
