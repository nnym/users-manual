package transfarmer.farmerlib.mixin.I18n;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(Language.class)
public abstract class MixinLanguage {
    @Shadow protected abstract String getTranslation(final String key);

    /**
     * @author transfarmer
     */
    @Overwrite
    public synchronized String translate(final String key) {
        return I18n.translate(key);
    }
}
