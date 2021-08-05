package gg.eris.commons.bukkit.menu.item;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class CloseMenuItem implements MenuItem {

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    viewer.getPlayer().closeInventory();
  }

}
