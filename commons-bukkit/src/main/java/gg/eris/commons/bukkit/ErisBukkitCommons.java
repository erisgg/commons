package gg.eris.commons.bukkit;

import cloud.commandframework.CommandManager;
import org.bukkit.command.CommandSender;

public interface ErisBukkitCommons {

  CommandManager<CommandSender> getCommandManager();

}
