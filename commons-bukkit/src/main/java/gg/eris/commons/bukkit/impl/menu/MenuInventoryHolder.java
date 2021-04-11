package gg.eris.commons.bukkit.impl.menu;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.core.Validate;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuInventoryHolder implements InventoryHolder {

  @Getter
  private final MenuViewer viewer;
  @Getter
  private final Menu menu;

  private Inventory inventory;

  public MenuInventoryHolder(MenuViewer viewer, Menu menu) {
    this.viewer = viewer;
    this.menu = menu;
  }

  public void setInventory(Inventory inventory) {
    Validate.isTrue(this.inventory == null, "inventory is already set");
    this.inventory = inventory;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
