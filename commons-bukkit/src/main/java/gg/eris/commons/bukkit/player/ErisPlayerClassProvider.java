package gg.eris.commons.bukkit.player;

@FunctionalInterface
public interface ErisPlayerClassProvider<T extends ErisPlayer> {

  Class<T> getErisPlayerClass();

}
