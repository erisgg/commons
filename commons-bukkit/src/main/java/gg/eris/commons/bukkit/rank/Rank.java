package gg.eris.commons.bukkit.rank;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Set;
import org.bukkit.entity.Player;

public final class Rank {

  private final Identifier identifier;
  private final Set<Identifier> permissions;

  public Rank(Identifier identifier) {
    this.identifier = identifier;
    this.permissions = Sets.newHashSet();
  }

  public boolean hasPermission(Permission permission) {
    return hasPermission(permission.getPermissionIdentifier());
  }

  public boolean hasPermission(Identifier identifier) {
    return this.permissions.contains(identifier);
  }

  public static Rank newRank(String name) {
    return new Rank(RankRegistry.IDENTIFIER_PROVIDER.id(name));
  }

}
