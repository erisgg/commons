package gg.eris.commons.bukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Taken from https://github.com/ezeiger92/QuestWorld2/blob/6ccff31b5b6572ad7012701c32898ca7ffb3f785/src/main/java/com/questworld/util/PlayerTools.java#L56
 */
@UtilityClass
public class CraftUtil {

  public static int getMaxCraftAmount(CraftingInventory inv) {
    if (inv.getResult() == null) {
      Bukkit.broadcastMessage("result is null");
      return 0;
    }

    int resultCount = inv.getResult().getAmount();
    int materialCount = Integer.MAX_VALUE;

    for (ItemStack is : inv.getMatrix()) {
      if (!StackUtil.isNullOrAir(is) && is.getAmount() < materialCount) {
        materialCount = is.getAmount();
      }
    }

    return resultCount * materialCount;
  }

  public static int fits(ItemStack stack, Inventory inv) {
    ItemStack[] contents = inv.getContents();
    int result = 0;

    int maxSize = NBTUtil.isUnstackable(stack) ? 1 : stack.getMaxStackSize();

    for (ItemStack is : contents) {
      if (StackUtil.isNullOrAir(is)) {
        result += maxSize;
      } else if (is.isSimilar(stack)) {
        result += Math.max(maxSize - is.getAmount(), 0);
      }
    }

    return result;
  }

}
