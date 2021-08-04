package gg.eris.commons.bukkit.impl.item;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listens for anvillable and craftable items on lowest priority
 */
public final class ItemListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPreCraft(PrepareItemCraftEvent event) {
    for (ItemStack item : event.getInventory().getMatrix()) {
      if (!NBTUtil.isCraftable(item)) {
        event.getInventory().setResult(null);
        return;
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  private void preventNonAnvillableItems(InventoryClickEvent event) {
    if (event.getClickedInventory() == null
        || event.getClickedInventory().getType() != InventoryType.ANVIL) {
      return;
    }

    Player player = (Player) event.getWhoClicked();

    for (ItemStack item : event.getClickedInventory().getContents()) {
      if (!NBTUtil.isAnvillable(item)) {
        event.setCancelled(true);
        TextController.send(
            player,
            TextType.ERROR,
            "That item <h>cannot</h> be used in an <h>anvil</h>."
        );
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  private void preventNonBrewableItems(InventoryClickEvent event) {
    if (event.getClickedInventory() == null
        || event.getClickedInventory().getType() != InventoryType.BREWING) {
      return;
    }

    Player player = (Player) event.getWhoClicked();

    for (ItemStack item : event.getClickedInventory().getContents()) {
      if (!NBTUtil.isBrewable(item)) {
        event.setCancelled(true);
        TextController.send(
            player,
            TextType.ERROR,
            "That item <h>cannot</h> be used in a <h>brewing stand</h>."
        );
      }
    }
  }

}
