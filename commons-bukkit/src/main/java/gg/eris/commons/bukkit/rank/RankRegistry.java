package gg.eris.commons.bukkit.rank;

import gg.eris.commons.bukkit.text.TextColor;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;

public final class RankRegistry extends Registry<Rank> {

  private static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("rank");

  public final Rank DEFAULT;
  public final Rank CREATOR;
  public final Rank PARTNER;
  public final Rank TRIAL_MODERATOR;
  public final Rank MODERATOR;
  public final Rank ADMIN;
  public final Rank DEVELOPER;
  public final Rank OWNER;

  public RankRegistry() {
    super();
    DEFAULT = register(new Rank(
        IDENTIFIER_PROVIDER.id("default"),
        7,
        TextColor.GRAY,
        CC.GRAY + "Default",
        false
    ));
    CREATOR = register(new Rank(
        IDENTIFIER_PROVIDER.id("creator"),
        6,
        TextColor.DARK_PURPLE,
        CC.DARK_PURPLE + "Creator"
    ));
    PARTNER = register(new Rank(
        IDENTIFIER_PROVIDER.id("partner"),
        5,
        TextColor.GREEN,
        CC.GREEN + "Partner"
    ));
    TRIAL_MODERATOR = register(new Rank(
        IDENTIFIER_PROVIDER.id("trial_moderator"),
        4,
        TextColor.BLUE,
        CC.BLUE + "Trial"
    ));
    MODERATOR = register(new Rank(
        IDENTIFIER_PROVIDER.id("moderator"),
        3,
        TextColor.GOLD,
        CC.GOLD + "Moderator"
    ));
    ADMIN = register(new Rank(
        IDENTIFIER_PROVIDER.id("admin"),
        2,
        TextColor.DARK_RED,
        CC.DARK_RED + "Admin"
    ));
    DEVELOPER = register(new Rank(
        IDENTIFIER_PROVIDER.id("developer"),
        1,
        TextColor.LIGHT_PURPLE,
        CC.LIGHT_PURPLE + "Developer"
    ));
    OWNER = register(new Rank(
        IDENTIFIER_PROVIDER.id("owner"),
        0,
        TextColor.RED,
        CC.RED + "Owner"
    ));
  }


  public Rank get(String name) {
    return get(IDENTIFIER_PROVIDER.id(name));
  }

}
