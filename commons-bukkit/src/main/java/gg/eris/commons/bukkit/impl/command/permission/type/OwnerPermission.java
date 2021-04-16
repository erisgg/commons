package gg.eris.commons.bukkit.impl.command.permission.type;

import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.SubCommand;
import gg.eris.commons.bukkit.command.Permission;
import gg.eris.commons.bukkit.impl.command.permission.PermissionType;
import org.bukkit.command.CommandSender;

public final class OwnerPermission extends Permission {

  public static final String PERMISSION = "eris.owner";

  public OwnerPermission(Command command, SubCommand subCommand) {
    super(command, subCommand);
  }

  @Override
  public PermissionType getType() {
    return PermissionType.OWNER;
  }

  @Override
  public boolean hasPermission(CommandSender sender) {
    return sender.hasPermission(PERMISSION);
  }

  @Override
  public String getLabel() {
    return PERMISSION;
  }
}
