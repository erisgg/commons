package gg.eris.commons.bukkit.rank;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;

public final class RankRegistry extends Registry<Rank> {

  private static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("rank");

  public final Rank DEFAULT;
  public final Rank TRIAL_MODERATOR;
  public final Rank MODERATOR;
  public final Rank COMMUNITY_ADMIN;
  public final Rank DEVELOPER;
  public final Rank OWNER;

  public RankRegistry() {
    super();
    DEFAULT = new Rank(
        IDENTIFIER_PROVIDER.id("default"),
        ""
    );
    TRIAL_MODERATOR = new Rank(
        IDENTIFIER_PROVIDER.id("trial_moderator"),
        CC.BLUE + "Trial"
    );
    MODERATOR = new Rank(
        IDENTIFIER_PROVIDER.id("moderator"),
        CC.GOLD + "Moderator"
    );
    COMMUNITY_ADMIN = new Rank(
        IDENTIFIER_PROVIDER.id("community_admin"),
        CC.RED + "Admin"
    );
    DEVELOPER = new Rank(
        IDENTIFIER_PROVIDER.id("developer"),
        CC.LIGHT_PURPLE + "Developer"
    );
    OWNER = new Rank(
        IDENTIFIER_PROVIDER.id("owner"),
        CC.RED + "Owner"
    );
  }

  public Rank get(String name) {
    return get(IDENTIFIER_PROVIDER.id(name));
  }

}
