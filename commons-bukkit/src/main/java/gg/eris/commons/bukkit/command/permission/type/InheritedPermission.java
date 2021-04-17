package gg.eris.commons.bukkit.command.permission.type;

import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Permission;
import gg.eris.commons.bukkit.command.SubCommand;
import gg.eris.commons.bukkit.command.permission.PermissionType;
import org.bukkit.command.CommandSender;

public final class InheritedPermission extends Permission {

  public InheritedPermission(Command command, SubCommand subCommand) {
    super(command, subCommand);
  }

  @Override
  public PermissionType getType() {
    return PermissionType.INHERITED;
  }

  @Override
  public boolean hasPermission(CommandSender sender) {
    return this.command.getPermission().hasPermission(sender);
  }

  @Override
  public String getLabel() {
    return this.command.getPermission().getLabel();
  }
}
