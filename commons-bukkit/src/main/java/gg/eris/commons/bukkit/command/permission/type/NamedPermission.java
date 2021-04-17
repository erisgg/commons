package gg.eris.commons.bukkit.command.permission.type;

import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Permission;
import gg.eris.commons.bukkit.command.SubCommand;
import gg.eris.commons.bukkit.command.permission.PermissionType;
import org.bukkit.command.CommandSender;

public final class NamedPermission extends Permission {

  private final String permission;

  public NamedPermission(Command command, SubCommand subCommand, String permission) {
    super(command, subCommand);
    this.permission = permission;
  }

  @Override
  public PermissionType getType() {
    return PermissionType.NAMED;
  }

  @Override
  public boolean hasPermission(CommandSender sender) {
    return sender.hasPermission(getLabel()) || OwnerPermission.isOwner(sender);
  }

  @Override
  public String getLabel() {
    return "eris." + this.permission;
  }
}


