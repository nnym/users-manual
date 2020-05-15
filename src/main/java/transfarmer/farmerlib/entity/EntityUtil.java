package transfarmer.farmerlib.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class EntityUtil {
    public static double getVelocity(final Entity entity) {
        final Vec3d velocity = entity.getVelocity();

        return Math.sqrt(velocity.x * velocity.x
                + velocity.y * velocity.y
                + velocity.z * velocity.z
        );
    }

    public static Entity getEntity(final UUID id) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            //noinspection MethodCallSideOnly
            for (final ServerWorld world : MinecraftClient.getInstance().getServer().getWorlds()) {
                return world.getEntity(id);
            }
        }

        return null;
    }
}
