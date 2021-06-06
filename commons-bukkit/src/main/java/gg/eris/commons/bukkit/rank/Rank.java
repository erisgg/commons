package gg.eris.commons.bukkit.rank;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import java.util.Set;

public final class Rank implements Identifiable {

  private static final IdentifierProvider IDENTIFIER_PROVIDER = new IdentifierProvider("rank");

  private final Identifier identifier;
  private final Set<Identifier> permissions;

  private Rank(Identifier identifier) {
    this.identifier = identifier;
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

  public static Rank newRank(RankRegistry registry, String name) {
    return registry.register(new Rank(IDENTIFIER_PROVIDER.id(name)));
  }

}
