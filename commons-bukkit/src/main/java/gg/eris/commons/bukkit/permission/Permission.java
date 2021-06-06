package gg.eris.commons.bukkit.permission;

import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import org.bukkit.entity.Player;

/**
 * Represents a server permission The namespace would typically be 'eris' and the value whatever
 * comes after, such as fly or message.others
 */
public final class Permission implements Identifiable {

  private final Identifier identifier;

  public Permission(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  public boolean hasPermission(Player player) {
    return player.hasPermission(this.toString());
  }

  public static Permission ofDefault(PermissionRegistry registry, String name) {
    return registry.register(new Permission(registry.generateIdentifier(name)));
  }

  @Override
  public String toString() {
    return this.identifier.getNamespace() + "." + this.identifier.getValue();
  }

}
