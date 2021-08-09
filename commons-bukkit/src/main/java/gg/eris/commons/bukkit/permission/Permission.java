package gg.eris.commons.bukkit.permission;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Represents a server permission The namespace would typically be 'eris' and the value whatever
 * comes after, such as fly or message.others
 */
public final class Permission implements Identifiable {

  public static final IdentifierProvider DEFAULT_IDENTIFIER_PROVIDER =
      new IdentifierProvider("eris");

  private final Identifier identifier;
  @Getter
  private final PermissionGroup group;

  private Permission(Identifier identifier, PermissionGroup group) {
    this.identifier = identifier;
    this.group = group;

    for (Rank rank : group.getRanks()) {
      rank.registerPermission(this);
    }
  }

  public boolean hasPermission(CommandSender sender) {
    if (sender.isOp() || !(sender instanceof Player)) {
      return true;
    }

    for (Rank rank : ErisBukkitCommonsPlugin.getInstance().getErisPlayerManager()
        .getPlayer(((Player) sender).getUniqueId()).getRanks()) {
      if (rank.hasPermission(this.identifier)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  @Override
  public String toString() {
    return this.identifier.toString();
  }

  public static Permission ofDefault(PermissionRegistry registry, String name,
      PermissionGroup group) {
    return of(registry, DEFAULT_IDENTIFIER_PROVIDER.id(name), group);
  }

  public static Permission of(PermissionRegistry registry, Identifier identifier,
      PermissionGroup group) {
    return registry.register(new Permission(identifier, group));
  }

}
