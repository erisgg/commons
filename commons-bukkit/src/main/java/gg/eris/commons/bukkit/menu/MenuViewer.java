package gg.eris.commons.bukkit.menu;

import java.lang.ref.WeakReference;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

/**
 * The {@link MenuViewer} class is not intended to be tied to a specific menu. It is designed to
 * be extended and passed around through menus to preserve data easily
 */
public class MenuViewer {

  private final WeakReference<Player> player;
  @Getter
  @Setter
  private Menu viewing;

  @Setter
  private boolean openParent;

  public MenuViewer(Player player) {
    this.player = new WeakReference<>(player);
    this.openParent = true;
  }

  public Player getPlayer() {
    return player.get();
  }

  public boolean shouldOpenParent() {
    return this.openParent;
  }

}
