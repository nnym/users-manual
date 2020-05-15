package transfarmer.farmerlib.mixin.I18n;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import transfarmer.farmerlib.event.TranslationEvent;

@Environment(EnvType.CLIENT)
@Mixin(Language.class)
public abstract class MixinLanguage {
    @Shadow protected abstract String getTranslation(final String key);

    /**
     * @author transfarmer
     */
    @Overwrite
    public synchronized String translate(final String key) {
        final TranslationEvent event = TranslationEvent.fire(this.getTranslation(key), key);
        final ActionResult result = event.getResult();

        switch (result) {
            case SUCCESS:
            case CONSUME:
                return event.getValue();
            case FAIL:
                return key;
            default:
                return this.getTranslation(event.getKey());
        }
    }
}
