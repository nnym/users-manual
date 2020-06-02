package user11681.usersmanual;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user11681.usersmanual.collections.ArrayMap;
import user11681.usersmanual.collections.OrderedArrayMap;

public class Main implements ModInitializer {
    public static final String MOD_ID = "usersmanual";
    public static final String MOD_NAME = "user's manual";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
        final ArrayMap<String, Integer> map = new OrderedArrayMap<>();

        LOGGER.warn(map.put("key", 1));
        LOGGER.warn(map.put("key", 5));
        LOGGER.warn(map.put("key", 7));
        LOGGER.warn(map.put("key", 2));
        LOGGER.warn(map.put("key", 4));
        LOGGER.warn(map.put("key", 9));
        LOGGER.warn(map.put("key", 6));
        LOGGER.warn(map.put("key", 8));
        LOGGER.warn(map.put("string", 1));
        LOGGER.warn(map.put("string", 5));
        LOGGER.warn(map.put("string", 7));
        LOGGER.warn(map.put("string", 2));
        LOGGER.warn(map.put("string", 4));
        LOGGER.warn(map.put("string", 9));
        LOGGER.warn(map.put("string", 6));
        LOGGER.warn(map.put("string", 8));
        LOGGER.warn(map.asString());
    }
}
