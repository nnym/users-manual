package user11681.usersmanual.mixin.duck.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

public interface CombinedInventoryDuck {
    List<DefaultedList<ItemStack>> getCombinedInventory();
}
