package gg.eris.commons.bukkit.impl.menu;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class MenuListener implements Listener {

  private final ErisBukkitCommonsPlugin plugin;

  public MenuListener(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getClickedInventory() == null) {
      return;
    }

    Inventory clicked = event.getClickedInventory();
    InventoryHolder inventoryHolder = clicked.getHolder();
    if (!(inventoryHolder instanceof MenuInventoryHolder)) {
      return;
    }

    MenuInventoryHolder menuHolder = (MenuInventoryHolder) inventoryHolder;
    Menu menu = menuHolder.getMenu();
    event.setCancelled(true);

    int slot = event.getSlot();
    MenuItem item = menu.getItem(slot);
    if (item != null) {
      item.onClick(menuHolder.getViewer(), event);
    }

  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    // Checking it is a Commons menu
    InventoryHolder inventoryHolder = event.getInventory().getHolder();
    if (!(inventoryHolder instanceof MenuInventoryHolder)) {
      return;
    }

    // Open the parent if it is appropriate
    MenuInventoryHolder menuHolder = (MenuInventoryHolder) inventoryHolder;
    MenuViewer menuViewer = menuHolder.getViewer();
    if (menuHolder.getMenu().hasParent() && menuViewer.shouldOpenParent()) {
      Bukkit.getScheduler().runTask(this.plugin, () -> {
        Player player = menuViewer.getPlayer();
        if (player != null && player.isOnline()) {
          menuHolder.getMenu().getParent().openMenu(menuViewer);
          menuViewer.setOpenParent(true);
        }
      });
    }
  }

}
