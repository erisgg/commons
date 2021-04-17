package gg.eris.commons.bukkit.command;

import gg.eris.commons.bukkit.command.permission.PermissionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@Getter
@RequiredArgsConstructor
public abstract class Permission {

  protected final Command command;
  protected final SubCommand subCommand;

  public abstract PermissionType getType();

  public abstract boolean hasPermission(CommandSender sender);

  public abstract String getLabel();
}
