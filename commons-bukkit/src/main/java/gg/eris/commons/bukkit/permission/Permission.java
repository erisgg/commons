package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import org.bukkit.entity.Player;

/**
 * Represents a server permission The namespace would typically be 'eris' and the value whatever
 * comes after, such as fly or message.others
 */
public final class Permission implements Identifiable {

  private static final IdentifierProvider DEFAULT_PROVIDER = new IdentifierProvider("eris");

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

  public static Permission ofDefault(String value) {
    return new Permission(DEFAULT_PROVIDER.id(value));
  }

  @Override
  public String toString() {
    return this.identifier.getNamespace() + "." + this.identifier.getValue();
  }
}
