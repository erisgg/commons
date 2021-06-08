package gg.eris.commons.bukkit.impl.player;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerClassProvider;

public final class DefaultErisPlayerClassProvider implements ErisPlayerClassProvider<ErisPlayer> {

  @Override
  public Class<ErisPlayer> getErisPlayerClass() {
    return ErisPlayer.class;
  }

}
