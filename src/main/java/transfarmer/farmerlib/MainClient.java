package transfarmer.farmerlib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import transfarmer.farmerlib.event.TranslationEvent;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TranslationEvent.MANAGER.register(event -> event.setResult(ActionResult.FAIL));
    }
}
