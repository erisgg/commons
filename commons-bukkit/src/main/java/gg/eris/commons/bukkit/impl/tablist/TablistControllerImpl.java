package gg.eris.commons.bukkit.impl.tablist;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.text.TextController;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class TablistControllerImpl implements TablistController {

  private final ErisPlayerManager erisPlayerManager;
  private String header;
  private String footer;
  private Function<ErisPlayer, String> displayNameFunction;

  public TablistControllerImpl(ErisPlayerManager erisPlayerManager) {
    this.erisPlayerManager = erisPlayerManager;
  }

  @Override
  public void setDisplayNameFunction(Function<ErisPlayer, String> displayNameFunction) {
    this.displayNameFunction = displayNameFunction;
  }

  @Override
  public void setHeader(String header) {
    this.header = header;
    updateAll();
  }

  @Override
  public void setFooter(String footer) {
    this.footer = footer;
    updateAll();
  }

  private void updateAll() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      update(player);
    }
  }

  public void update(Player player) {
    TablistUtil.sendHeaderFooter(
        player,
        TextController.parse(this.header),
        TextController.parse(this.footer)
    );

    player.setPlayerListName(displayNameFunction.apply(this.erisPlayerManager.getPlayer(player)));
  }

}
