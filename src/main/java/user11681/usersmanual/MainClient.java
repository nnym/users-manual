package user11681.usersmanual;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import user11681.usersmanual.image.ImageUtil;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        CLIENT.execute(() -> ImageUtil.toByteBuffer(ImageUtil.readTexture(Screen.GUI_ICONS_LOCATION).getRaster()));
    }
}
