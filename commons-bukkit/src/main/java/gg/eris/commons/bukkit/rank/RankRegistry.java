package gg.eris.commons.bukkit.rank;

import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;

public abstract class RankRegistry extends Registry<Rank> {

  protected static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("eris_rank");

  public RankRegistry() {
    super(IDENTIFIER_PROVIDER.id("rank_registry"));
  }

}
