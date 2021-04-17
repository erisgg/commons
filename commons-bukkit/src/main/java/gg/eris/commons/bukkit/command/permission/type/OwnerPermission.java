package gg.eris.commons.bukkit.command.permission.type;

import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Permission;
import gg.eris.commons.bukkit.command.SubCommand;
import gg.eris.commons.bukkit.command.permission.PermissionType;
import java.util.Set;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class OwnerPermission extends Permission {

  private static final Set<UUID> OWNER_UUIDS = Set.of(
      UUID.fromString("811134f3-a8ff-47ff-adc8-4e1f2f2757a3"), // Alfie
      UUID.fromString("6d76dfa1-f2a7-4555-9e24-39a81925a3ab") // Aifle
  );

  public OwnerPermission(Command command, SubCommand subCommand) {
    super(command, subCommand);
  }

  @Override
  public PermissionType getType() {
    return PermissionType.OWNER;
  }

  @Override
  public boolean hasPermission(CommandSender sender) {
    return isOwner(sender);
  }

  @Override
  public String getLabel() {
    return "Owner Account";
  }

  public static boolean isOwner(CommandSender sender) {
    if (sender instanceof ConsoleCommandSender) {
      return true;
    } else {
      if (!(sender instanceof Player)) {
        return false;
      }

      Player player = (Player) sender;

      return OWNER_UUIDS.contains(player.getUniqueId());
    }
  }
}
