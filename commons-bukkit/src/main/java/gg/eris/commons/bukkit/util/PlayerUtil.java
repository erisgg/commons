package gg.eris.commons.bukkit.util;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class PlayerUtil {

  /**
   * Adds items to a player's inventory, dropping them if there is no space
   *
   * @param player is the player to give or drop items to
   * @param items  are the items to drop
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

  /**
   * Resets a given {@link Player}
   *
   * @param player is the player to reset
   */
  public static void resetPlayer(Player player) {
    player.getInventory().clear();
    player.setHealth(player.getMaxHealth());
    player.setSaturation(10f);
    player.setFoodLevel(20);
    player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
  }

  public static EntityPlayer getHandle(Player player) {
    return ((CraftPlayer) player).getHandle();
  }

}
