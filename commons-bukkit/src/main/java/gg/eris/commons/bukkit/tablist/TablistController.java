package gg.eris.commons.bukkit.tablist;

import gg.eris.commons.bukkit.player.ErisPlayer;
import java.util.Comparator;
import java.util.function.BiFunction;
import org.bukkit.entity.Player;

public interface TablistController {

  BiFunction<ErisPlayer, ErisPlayer, String> getDisplayNameFunction();

  Comparator<ErisPlayer> getOrderingComparator();

  void setDisplayNameFunction(BiFunction<ErisPlayer, ErisPlayer, String> displayNameFunction);

  void setOrderingComparator(Comparator<ErisPlayer> orderingComparator);

  void setHeader(String header);

  void setFooter(String footer);

  void updateDisplayName(Player player);

  void updateAllDisplayNames();

}
