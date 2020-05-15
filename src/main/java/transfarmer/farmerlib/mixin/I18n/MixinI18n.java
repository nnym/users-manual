package transfarmer.farmerlib.mixin.I18n;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import transfarmer.farmerlib.event.TranslationEvent;

import static net.minecraft.util.ActionResult.FAIL;

@Environment(EnvType.CLIENT)
@Mixin(I18n.class)
public class MixinI18n {
    @Shadow
    private static TranslationStorage storage;

    /**
     * This Mixin method adds a hook to TranslateEvent for modification of translation results.
     *
     * @author transfarmer
     */
    @Overwrite
    public static String translate(final String key, final Object... args) {
        final TranslationEvent event = TranslationEvent.fire(storage.translate(key, args), key, args);

        return event.getResult() == FAIL ? key : storage.translate(event.getKey(), event.getArgs().toArray());
    }
}
