package gg.eris.commons.bukkit.rank;

import gg.eris.commons.bukkit.text.TextColor;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;

public final class RankRegistry extends Registry<Rank> {

  private static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("rank");

  private static final RankRegistry REGISTRY = new RankRegistry();

  public final Rank DEFAULT;
  public final Rank PRO;
  public final Rank ELITE;
  public final Rank DEMIGOD;
  public final Rank CREATOR;
  public final Rank PARTNER;
  public final Rank TRIAL_MODERATOR;
  public final Rank MODERATOR;
  public final Rank ADMIN;
  public final Rank DEVELOPER;
  public final Rank OWNER;

  private RankRegistry() {
    super();
    DEFAULT = register(new Rank(
        IDENTIFIER_PROVIDER.id("default"),
        10,
        TextColor.GRAY,
        CC.GRAY + "Default",
        false
    ));
    PRO = register(new Rank(
        IDENTIFIER_PROVIDER.id("pro"),
        9,
        TextColor.BLUE,
        CC.BLUE + "Pro"
    ));
    ELITE = register(new Rank(
        IDENTIFIER_PROVIDER.id("elite"),
        8,
        TextColor.AQUA,
        CC.AQUA + "Elite"
    ));
    DEMIGOD = register(new Rank(
        IDENTIFIER_PROVIDER.id("demigod"),
        7,
        TextColor.DARK_AQUA,
        CC.DARK_AQUA + "Demigod"
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

  public static RankRegistry get() {
    return REGISTRY;
  }

}
