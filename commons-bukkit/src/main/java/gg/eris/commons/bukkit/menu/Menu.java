package gg.eris.commons.bukkit.menu;

import gg.eris.commons.bukkit.impl.menu.MenuInventoryHolder;
import gg.eris.commons.bukkit.menu.item.ActionlessMenuItem;
import gg.eris.commons.core.util.Validate;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Menu {

  private final JavaPlugin owningPlugin;
  private final String identifier;
  private final int rows;
  private final int itemCount;

  private final Int2ObjectArrayMap<MenuItem> items;

  @Getter
  @Setter
  private Menu parent;

  private MenuItem fillItem;

  public Menu(JavaPlugin owningPlugin, String identifier, int rows) {
    this.owningPlugin = owningPlugin;
    this.identifier = identifier;
    this.rows = rows;
    this.itemCount = rows * 9;
    this.items = new Int2ObjectArrayMap<>();
  }

  /**
   * Returns the menu title for a given {@link MenuViewer}
   *
   * @param viewer is the viewer to get the title for
   * @return the menu title
   */
  public abstract String getTitle(MenuViewer viewer);

  public final void addItem(int slot, MenuItem item) {
    Validate.isTrue(item != null, "item cannot be null");
    Validate.isTrue(slot >= 0 && slot < this.itemCount);
    this.items.put(slot, item);
  }

  public final MenuItem getItem(int slot) {
    return this.items.get(slot);
  }

  public final Collection<MenuItem> getItems() {
    return this.items.values();
  }

  public final void openMenu(Player player) {
    openMenu(new MenuViewer(player));
  }

  public final void openMenu(MenuViewer menuViewer) {
    MenuInventoryHolder menuInventoryHolder = new MenuInventoryHolder(menuViewer, this);
    Inventory inventory = Bukkit.createInventory(menuInventoryHolder, 9 * this.rows,
        getTitle(menuViewer));
    menuInventoryHolder.setInventory(inventory);

    ItemStack[] items = new ItemStack[this.itemCount];
    if (fillItem != null) {
      for (int i = 0; i < this.itemCount; i++) {
        MenuItem item = this.items.get(i);
        if (item != null) {
          items[i] = item.getItem(menuViewer, this);
        } else {
          items[i] = fillItem.getItem(menuViewer, this);
        }
      }
    } else {
      for (int key : this.items.keySet()) {
        MenuItem item = this.items.get(key);
        if (item != null) {
          items[key] = item.getItem(menuViewer, this);
        }
      }
    }

    inventory.setContents(items);

    menuViewer.getPlayer().openInventory(inventory);
    menuViewer.setViewing(this);
  }

  public final void setFillItem(ItemStack item) {
    setFillItem(new ActionlessMenuItem(item));
  }

  public final void setFillItem(MenuItem item) {
    this.fillItem = item;
  }

  public final boolean hasParent() {
    return this.parent != null;
  }

}
