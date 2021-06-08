package gg.eris.commons.bukkit.player;

public interface ErisPlayerClassProvider<T extends ErisPlayer> {

  Class<T> getErisPlayerClass();

}
