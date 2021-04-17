package gg.eris.commons.bukkit.impl.command;

import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class InternalCommand extends Command {

  private final gg.eris.commons.bukkit.command.Command command;

  public InternalCommand(gg.eris.commons.bukkit.command.Command command) {
    super(command.getName(), command.getDescription(), null, new ArrayList<>(command.getAliases()));
    this.command = command;
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    command.handle(sender, label, args);
    return true;
  }
}
