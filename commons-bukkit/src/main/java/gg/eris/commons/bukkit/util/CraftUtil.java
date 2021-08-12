package gg.eris.commons.bukkit.util;

import lombok.experimental.UtilityClass;
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
      return 0;
    }

    int resultCount = inv.getResult().getAmount();
    int materialCount = Integer.MAX_VALUE;

    for (ItemStack is : inv.getMatrix()) {
      if (is != null && is.getAmount() < materialCount) {
        materialCount = is.getAmount();
      }
    }

    return resultCount * materialCount;
  }

  public static int fits(ItemStack stack, Inventory inv) {
    ItemStack[] contents = inv.getContents();
    int result = 0;

    for (ItemStack is : contents) {
      if (is == null) {
        result += stack.getMaxStackSize();
      } else if (is.isSimilar(stack)) {
        result += Math.max(stack.getMaxStackSize() - is.getAmount(), 0);
      }
    }

    return result;
  }

}
