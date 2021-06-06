package gg.eris.commons.bukkit.rank;

import gg.eris.commons.core.registry.Registry;

public abstract class RankRegistry extends Registry<Rank> {

  protected static final String NAMESPACE = "rank";

  public RankRegistry() {
    super(NAMESPACE);
  }

}
