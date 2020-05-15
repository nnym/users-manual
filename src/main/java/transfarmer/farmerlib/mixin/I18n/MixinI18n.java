package transfarmer.farmerlib.mixin.I18n;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import transfarmer.farmerlib.event.TranslationEvent;

@Environment(EnvType.CLIENT)
@Mixin(I18n.class)
public abstract class MixinI18n {
    @Shadow
    private static TranslationStorage storage;

    /**
     * This Mixin method adds a hook in {@link I18n#translate} to TranslateEvent
     * for modification of translation results.
     *
     * @author transfarmer
     */
    @Overwrite
    public static String translate(final String key, final Object... args) {
        final TranslationEvent event = TranslationEvent.fire(storage.translate(key, args), key, args);
        final ActionResult result = event.getResult();

        switch (result) {
            case SUCCESS:
            case CONSUME:
                return event.getValue();
            case FAIL:
                return key;
            default:
                return storage.translate(event.getKey(), event.getArgs().toArray());
        }
    }
}
