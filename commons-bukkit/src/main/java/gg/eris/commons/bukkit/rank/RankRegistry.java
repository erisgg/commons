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
    DEFAULT = register(new Rank(
        IDENTIFIER_PROVIDER.id("default"),
        "",
        CC.GRAY + "Default"
    ));
    TRIAL_MODERATOR = register(new Rank(
        IDENTIFIER_PROVIDER.id("trial_moderator"),
        CC.BLUE + "Trial",
        CC.BLUE + "Trial"
    ));
    MODERATOR = register(new Rank(
        IDENTIFIER_PROVIDER.id("moderator"),
        CC.GOLD + "Moderator",
        CC.GOLD + "Moderator"
    ));
    COMMUNITY_ADMIN = register(new Rank(
        IDENTIFIER_PROVIDER.id("community_admin"),
        CC.DARK_RED + "Admin",
        CC.DARK_RED + "Admin"
    ));
    DEVELOPER = register(new Rank(
        IDENTIFIER_PROVIDER.id("developer"),
        CC.LIGHT_PURPLE + "Developer",
        CC.LIGHT_PURPLE + "Developer"
    ));
    OWNER = register(new Rank(
        IDENTIFIER_PROVIDER.id("owner"),
        CC.RED + "Owner",
        CC.RED + "Owner"
    ));
  }


  public Rank get(String name) {
    return get(IDENTIFIER_PROVIDER.id(name));
  }

}
