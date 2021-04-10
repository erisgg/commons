package gg.eris.commons.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class PlayerUtil {

  /**
   * Adds items to a player's inventory, dropping them if there is no space
   *
   * @param player is the player to give or drop items to
   * @param items are the items to drop
   */
  public static void giveOrDropItems(Player player, ItemStack... items) {
    player.getInventory().addItem(items)
        .forEach((index, item) -> player.getWorld().dropItem(player.getLocation(), item));
  }

  /**
   * Drops items at a player's position
   *
   * @param player is the player to drop items for
   * @param items  are the items to drop
   */
  public static void dropItems(Player player, ItemStack... items) {
    for (ItemStack item : items) {
      player.getWorld().dropItem(player.getLocation(), item);
    }
  }

}
