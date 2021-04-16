package gg.eris.commons.bukkit.impl.command.permission.type;

import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.SubCommand;
import gg.eris.commons.bukkit.command.Permission;
import gg.eris.commons.bukkit.impl.command.permission.PermissionType;
import org.bukkit.command.CommandSender;

public final class NonePermission extends Permission {

  public NonePermission(Command command, SubCommand subCommand) {
    super(command, subCommand);
  }

  @Override
  public PermissionType getType() {
    return PermissionType.NONE;
  }

  @Override
  public boolean hasPermission(CommandSender sender) {
    return true;
  }

  @Override
  public String getLabel() {
    throw new UnsupportedOperationException("None permission does not have label");
  }
}
