package user11681.usersmanual.world.gen.feature;

import net.minecraft.block.Blocks;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import user11681.usersmanual.reflect.Constructors;

public class ModTargets {
    public static final OreFeatureConfig.Target END_STONE = Constructors.addEnumInstance(OreFeatureConfig.Target.values(), "END_STONE", "end_stone", new BlockPredicate(Blocks.END_STONE));
}
