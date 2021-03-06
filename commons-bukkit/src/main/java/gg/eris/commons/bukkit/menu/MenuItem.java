package gg.eris.commons.bukkit.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public interface MenuItem {

  /**
   * Returns the item
   *
   * @param menuViewer is the menu viewer
   * @param menu       is the menu the item was clicked in
   * @return the display item
   */
  ItemStack getItem(MenuViewer menuViewer, Menu menu);

  /**
   * Handles a click action on a menu item
   *
   * @param menuViewer is the menu viewer
   * @param event      is the click event
   */
  void onClick(MenuViewer menuViewer, InventoryClickEvent event);

  /**
   * Handles a drag event on a menu item
   *
   * @param menuViewer is the menu viewer
   * @param event      is the drag event
   */
  default void onDrag(MenuViewer menuViewer, InventoryDragEvent event) {

  }

}
