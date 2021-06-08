package gg.eris.commons.bukkit.player;

import java.util.UUID;

public interface ErisPlayerManager {

  <T extends ErisPlayer> T getPlayer(UUID uuid);

}
