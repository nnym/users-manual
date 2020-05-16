package transfarmer.farmerlib.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import transfarmer.farmerlib.collection.CollectionUtil;

import java.util.Arrays;
import java.util.List;

public class ItemUtil {
    public static boolean hasItem(final Item item, final PlayerEntity player) {
        for (final ItemStack itemStack : player.inventory.main) {
            if (itemStack.getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getEquippedItemStack(final PlayerEntity player, final Item... validItems) {
        final ItemStack mainhandStack = player.getMainHandStack();
        final List<Item> itemList = Arrays.asList(validItems);

        if (itemList.contains(mainhandStack.getItem())) {
            return mainhandStack;
        }

        final ItemStack offhandStack = player.getOffHandStack();

        if (itemList.contains(offhandStack.getItem())) {
            return offhandStack;
        }

        return null;
    }

    public static ItemStack getEquippedItemStack(final PlayerInventory inventory, final Class<?> cls) {
        for (final ItemStack itemStack : Arrays.asList(inventory.getMainHandStack(), inventory.offHand.get(0))) {
            if (cls.isInstance(itemStack.getItem())) {
                return itemStack;
            }
        }

        return null;
    }

    public static ItemStack getRequiredItemStack(final PlayerEntity player, final EquipmentSlot slot,
                                                 final Class<?>... classes) {
        final ItemStack itemStack = player.getMainHandStack();
        final Item item = itemStack.getItem();

        for (final Class<?> clazz : classes) {
            if (clazz.isInstance(item)) {
                return itemStack;
            }
        }

        return null;
    }

    public static boolean hasItem(final PlayerEntity player, final Item item, final int min, final int max) {
        final PlayerInventory inventory = player.inventory;
        final DefaultedList<ItemStack> merged = CollectionUtil.merge(inventory.main, inventory.offHand);

        for (int i = min; i < max; i++) {
            final ItemStack itemStack = merged.get(i);

            if (itemStack.getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public static int getSlotFor(final PlayerInventory inventory, final ItemStack itemStack) {
        for (int i = 0; i < inventory.main.size(); ++i) {
            if (!inventory.main.get(i).isEmpty() && ItemStack.areEqualIgnoreDamage(itemStack, inventory.main.get(i))) {
                return i;
            }
        }

        return -1;
    }

    public static List<Item> getHandItems(final PlayerEntity player) {
        return ImmutableList.of(player.getMainHandStack().getItem(), player.getOffHandStack().getItem());
    }

    public boolean isItemEquipped(final PlayerEntity player, final Item item) {
        return getHandItems(player).contains(item);
    }
}