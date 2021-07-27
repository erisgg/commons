package gg.eris.commons.bukkit.util;

import gg.eris.commons.core.util.Text;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.index.qual.PolyUpperBound;

@UtilityClass
public class StackUtil {

  /**
   * Replaces the name and lore of an item with given placeholders
   *
   * @param item      is the item to replace variables for
   * @param variables are the variables to replace with
   * @return the same item (mutated)
   */
  public static ItemStack replaceVariables(ItemStack item, Object... variables) {
    ItemMeta meta = item.getItemMeta();
    if (meta.hasDisplayName()) {
      meta.setDisplayName(Text.replaceVariables(meta.getDisplayName(), variables));
    }

    if (meta.hasLore()) {
      meta.setLore(Text.replaceVariables(meta.getLore(), variables));
    }

    item.setItemMeta(meta);
    return item;
  }

  public static void decrement(ItemStack item) {
    if (item.getAmount() == 1) {
      item.setType(null);
    } else {
      item.setAmount(item.getAmount() - 1);
    }
  }

}
