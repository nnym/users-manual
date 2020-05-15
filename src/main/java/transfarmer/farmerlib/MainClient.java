package transfarmer.farmerlib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import transfarmer.farmerlib.event.TranslationEvent;

import static net.minecraft.util.ActionResult.FAIL;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TranslationEvent.register(event -> event.setResult(FAIL));
    }
}
