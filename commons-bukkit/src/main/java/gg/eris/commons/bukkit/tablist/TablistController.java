package gg.eris.commons.bukkit.tablist;

import gg.eris.commons.bukkit.player.ErisPlayer;
import java.util.function.Function;
import org.bukkit.entity.Player;

public interface TablistController {

  void setDisplayNameFunction(Function<ErisPlayer, String> displayNameFunction);

  void setHeader(String header);

  void setFooter(String footer);

}
