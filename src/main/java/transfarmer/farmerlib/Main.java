package transfarmer.farmerlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "farmerlib";
    public static final String MOD_NAME = "farmerlib";
    public static final String VERSION = "0.2.0";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
    }
}
