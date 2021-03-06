package gg.eris.commons.bukkit.util;

import gg.eris.commons.core.util.Text;
import java.util.Arrays;
import java.util.Collection;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    if (!StackUtil.isNullOrAir(item)) {
      if (item.getAmount() == 1) {
        item.setType(Material.AIR);
        return false;
      } else {
        item.setAmount(item.getAmount() - 1);
      }
    }

    return true;
  }

  public static boolean damage(ItemStack item) {
    if (StackUtil.isNullOrAir(item) || item.getType().getMaxDurability() == 0) {
      return true;
    }

    short max = item.getType().getMaxDurability();
    short durability = item.getDurability();

    if (durability == max) {
      return false;
    } else {
      item.setDurability((short) (durability + 1));
    }

    return true;
  }

  public static void dropItem(Block block, ItemStack... items) {
    dropItem(block.getLocation(), items);
  }

  public static void dropItem(Location location, ItemStack... items) {
    dropItems(location, Arrays.asList(items));
  }

  public static void dropItem(Block block, boolean natural, ItemStack... items) {
    dropItems(block.getLocation(), Arrays.asList(items), natural);
  }

  public static void dropItem(Location location, boolean natural, ItemStack... items) {
    dropItems(location, Arrays.asList(items), natural);
  }

  public static void dropItems(Block block, Collection<ItemStack> items) {
    dropItems(block.getLocation(), items, false);
  }

  public static void dropItems(Location location, Collection<ItemStack> items) {
    dropItems(location, items, false);
  }

  public static void dropItems(Block block, Collection<ItemStack> items, boolean natural) {
    dropItems(block.getLocation(), items, natural);
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
