package gg.eris.commons.bukkit.util;

import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Validate;
import java.util.Arrays;
import java.util.Collection;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.index.qual.PolyUpperBound;

@UtilityClass
public class StackUtil {

  public static boolean isNullOrAir(ItemStack item) {
    return item == null || item.getType() == Material.AIR;
  }

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

  public static boolean decrement(ItemStack item) {
    Validate.notNull(item, "item cannot be null");
    if (item.getAmount() == 1) {
      item.setType(Material.AIR);
      return false;
    } else {
      item.setAmount(item.getAmount() - 1);
    }

    return true;
  }

  public static void dropItem(Location location, ItemStack... items) {
    dropItems(location, Arrays.asList(items));
  }

  public static void dropItems(Location location, Collection<ItemStack> items) {
    dropItems(location, items, false);
  }

  public static void dropItems(Location location, Collection<ItemStack> items, boolean natural) {
    if (natural) {
      for (ItemStack item : items) {
        location.getWorld().dropItemNaturally(location, item);
      }
    } else {
      for (ItemStack item : items) {
        location.getWorld().dropItem(location, item);
      }
    }
  }

}
