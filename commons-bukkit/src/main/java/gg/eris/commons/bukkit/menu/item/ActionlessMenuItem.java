package gg.eris.commons.bukkit.menu.item;

import gg.eris.commons.bukkit.menu.MenuViewer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class ActionlessMenuItem extends StaticItemMenuItem {

  public ActionlessMenuItem(ItemStack item) {
    super(item);
  }

  @Override
  public void onClick(MenuViewer menuViewer, InventoryClickEvent event) {
  }

}
