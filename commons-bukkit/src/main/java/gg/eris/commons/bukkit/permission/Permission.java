package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Represents a server permission
 * The namespace would typically be 'eris' and the value whatever comes after,
 * such as fly or message.others
 */
public final class Permission {

  private static final IdentifierProvider DEFAULT_PROVIDER = new IdentifierProvider("eris");

  @Getter
  private final Identifier permissionIdentifier;

  public Permission(Identifier permissionIdentifier) {
    this.permissionIdentifier = permissionIdentifier;
  }

  public boolean hasPermission(Player player) {
    return player.hasPermission(this.toString());
  }

  public static Permission ofDefault(String value) {
    return new Permission(DEFAULT_PROVIDER.id(value));
  }

  @Override
  public String toString() {
    return this.permissionIdentifier.getNamespace() + "." + this.permissionIdentifier.getValue();
  }
}
