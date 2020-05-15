package transfarmer.farmerlib.mixin.I18n;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import transfarmer.farmerlib.event.TranslationEvent;

@Environment(EnvType.CLIENT)
@Mixin(I18n.class)
public abstract class MixinI18n {
    @Shadow
    private static TranslationStorage storage;

    /**
     * This Mixin method adds a hook in {@link I18n#translate} to TranslateEvent
     * for modification of translation results.
     */
    @Inject(method = "translate", at = @At("RETURN"), cancellable = true)
    private static void translate(final String key, final Object[] args, final CallbackInfoReturnable<String> info) {
        final TranslationEvent event = TranslationEvent.fire(storage.translate(key, args), key, args);
        final ActionResult result = event.getResult();

        switch (result) {
            case SUCCESS:
            case CONSUME:
                info.setReturnValue(event.getValue());
                break;
            case FAIL:
                info.setReturnValue(key);
                break;
            default:
                info.setReturnValue(storage.translate(event.getKey(), event.getArgs().toArray()));
        }
    }
}
