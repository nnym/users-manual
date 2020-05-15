package transfarmer.farmerlib.event;

import net.minecraft.util.ActionResult;

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
}
