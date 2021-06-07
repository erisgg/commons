package gg.eris.commons.bukkit.permission;

import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Represents a server permission The namespace would typically be 'eris' and the value whatever
 * comes after, such as fly or message.others
 */
public final class Permission implements Identifiable {

  private static final IdentifierProvider DEFAULT_IDENTIFIER_PROVIDER =
      new IdentifierProvider("eris");

  private final Identifier identifier;

  private Permission(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  public boolean hasPermission(CommandSender sender) {
    return sender.hasPermission(this.toString());
  }

  public static Permission ofDefault(PermissionRegistry registry, String name) {
    return of(registry, DEFAULT_IDENTIFIER_PROVIDER.id(name));
  }

  public static Permission of(PermissionRegistry registry, Identifier identifier) {
    return registry.register(new Permission(identifier));
  }

  @Override
  public String toString() {
    return this.identifier.getNamespace() + "." + this.identifier.getValue();
  }

}
