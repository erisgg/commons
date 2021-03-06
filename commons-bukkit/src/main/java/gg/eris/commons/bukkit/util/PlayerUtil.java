package gg.eris.commons.bukkit.util;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.impl.menu.MenuInventoryHolder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
    StackUtil.dropItem(player.getLocation(), items);
  }

  /**
   * Resets a given {@link Player}
   *
   * @param player is the player to reset
   */
  public static void resetPlayer(Player player) {
    player.getInventory().clear();
    player.getInventory().setArmorContents(new ItemStack[4]);
    player.setMaxHealth(20);
    player.setHealth(20);
    player.setSaturation(10f);
    player.setFoodLevel(20);
    player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
    player.setExp(0.0f);
    player.setTotalExperience(0);
    player.setLevel(0);

    if (player.getOpenInventory().getTopInventory() != null
        && player.getOpenInventory().getTopInventory().getHolder() instanceof MenuInventoryHolder) {
      ((MenuInventoryHolder) player.getOpenInventory().getTopInventory().getHolder())
          .getViewer().setOpenParent(false);
    }
    player.closeInventory();
  }

  public static void setSafeGameMode(Player player, GameMode gameMode) {
    new BukkitRunnable() {
      int cancelCount = 5;

      @Override
      public void run() {
        if (player.getGameMode() != gameMode) {
          player.setGameMode(gameMode);
        } else {
          if (this.cancelCount-- < 0) {
            cancel();
          }
        }
      }
    }.runTaskTimer(ErisBukkitCommonsPlugin.getInstance(), 0, 1L);
  }

  public static void playSound(Player player, Sound sound) {
    player.playSound(player.getLocation(), sound, 1f, 1f);
  }

  public static Collection<ItemStack> getItems(Player player) {
    List<ItemStack> items = Lists.newArrayList();
    items.addAll(Arrays.asList(player.getInventory().getContents()));
    items.addAll(Arrays.asList(player.getInventory().getArmorContents()));
    items.removeIf(StackUtil::isNullOrAir);
    return items;
  }

  public static void sendToServer(Player player, String server) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Connect");
    out.writeUTF(server);
    player
        .sendPluginMessage(ErisBukkitCommonsPlugin.getInstance(), "BungeeCord", out.toByteArray());
  }

  public static void updateInventory(Player player) {
    updateInventory(player, 0);
  }

  public static void updateInventory(Player player, int delay) {
    if (delay == 0) {
      Bukkit.getScheduler().runTask(ErisBukkitCommonsPlugin.getInstance(),
          player::updateInventory);
    } else {
      Bukkit.getScheduler().runTaskLater(ErisBukkitCommonsPlugin.getInstance(),
          player::updateInventory, delay);
    }
  }

  public static EntityPlayer getHandle(Player player) {
    return ((CraftPlayer) player).getHandle();
  }

}
