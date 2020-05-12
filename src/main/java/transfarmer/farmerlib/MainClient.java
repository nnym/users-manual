package transfarmer.farmerlib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;

import static net.fabricmc.api.EnvType.CLIENT;

@Environment(CLIENT)
public class MainClient implements ClientModInitializer {
    @Override
    @Environment(CLIENT)
    public void onInitializeClient() {
    }
}
