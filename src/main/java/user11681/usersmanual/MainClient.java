package user11681.usersmanual;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class MainClient {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();
}
