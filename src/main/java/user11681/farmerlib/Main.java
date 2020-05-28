package user11681.farmerlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "farmerlib";
    public static final String MOD_NAME = "farmerlib";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
//        final ParallelList<Integer, Integer> list = new ParallelArrayList<>();
//
//        list.add(9, 0);
//        list.add(8, 0);
//        list.add(7, 5);
//        list.add(7, 5);
//        list.add(7, 0);
//        list.add(7, 0);
//        list.add(7, 5);
//        list.add(4, 0);
//        list.add(2, 0);
//        list.add(2, 0);
//        list.add(1, 1);
//        list.add(1, 1);
//        list.add(1, 0);
//        list.remove(12);
//        list.remove(0);
//
//        LOGGER.warn(list.asString());
//        LOGGER.warn(Arrays.toString(list.keyList().toArray()));
//        LOGGER.warn(Arrays.toString(list.valueList().toArray()));
    }
}
