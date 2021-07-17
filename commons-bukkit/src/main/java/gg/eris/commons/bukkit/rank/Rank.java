package gg.eris.commons.bukkit.rank;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Set;
import lombok.Getter;

@Getter
public final class Rank implements Identifiable {

  private final Identifier identifier;
  private final String prefix;
  private final Set<Identifier> permissions;

  public Rank(Identifier identifier, String prefix) {
    this.identifier = identifier;
    this.prefix = prefix;
    this.permissions = Sets.newHashSet();
  }

  public boolean hasPermission(Permission permission) {
    return hasPermission(permission.getIdentifier());
  }

  public boolean hasPermission(Identifier identifier) {
    return this.permissions.contains(identifier);
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

}
