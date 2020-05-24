package transfarmer.farmerlib.event;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Language;
import transfarmer.farmerlib.event.base.Event;
import transfarmer.farmerlib.event.base.EventInvoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.util.ActionResult.CONSUME;

/**
 * This event is called when {@link I18n#translate} and {@link Language#translate} are called.
 * It allows listeners to modify translation key, arguments and return value of the method call.
 *
 * A {@link ActionResult#SUCCESS} {@link Event#result} sets the return value to {@link TranslationEvent#value}
 * and cancels further processing.
 * A {@link ActionResult#CONSUME} {@link Event#result} sets the return value to {@link TranslationEvent#value}
 * and does not cancel further processing.
 * A {@link ActionResult#PASS} {@link Event#result} translates the new key and arguments.
 * and does not cancel further processing.
 * A {@link ActionResult#FAIL} {@link Event#result} sets the return value to {@link TranslationEvent#key}
 * and cancels further processing.
 */
public class TranslationEvent extends Event<TranslationEvent> {
    public static final EventInvoker<TranslationEvent> INVOKER = new EventInvoker<>(TranslationEvent.class);

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
     * @return the return value of the translation
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
        this.result = CONSUME;
    }

    public static TranslationEvent fire(final String value, final String key, final Object... args) {
        return INVOKER.fire(new TranslationEvent(value, key, args));
    }
}
