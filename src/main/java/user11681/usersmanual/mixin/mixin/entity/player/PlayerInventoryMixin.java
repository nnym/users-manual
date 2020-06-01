package user11681.usersmanual.mixin.mixin.entity.player;

import java.util.List;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import user11681.usersmanual.mixin.duck.CombinedInventoryDuck;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements CombinedInventoryDuck {
    @Accessor
    public abstract List<DefaultedList<ItemStack>> getCombinedInventory();
}
