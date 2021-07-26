package gg.eris.commons.bukkit.rank;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.text.TextColor;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public final class Rank implements Identifiable {

  private final Identifier identifier;
  private final int priority;
  private final TextColor color;
  private final String rawDisplay;
  private final String coloredDisplay;

  private final boolean whiteChat;

  @Getter(AccessLevel.NONE)
  private final Set<Identifier> permissions;

  // White chat by default
  public Rank(Identifier identifier, int priority, TextColor color, String display) {
    this(identifier, priority, color, display, true);
  }

  public Rank(Identifier identifier, int priority, TextColor color, String display,
      boolean whiteChat) {
    this.identifier = identifier;
    this.priority = priority;
    this.color = color;
    this.rawDisplay = CC.strip(display);
    this.coloredDisplay = display;
    this.whiteChat = whiteChat;
    this.permissions = Sets.newHashSet();
  }

  public boolean hasPermission(Permission permission) {
    return hasPermission(permission.getIdentifier());
  }

  public boolean hasPermission(Identifier identifier) {
    return this.permissions.contains(identifier);
  }

  public Set<Identifier> getPermissions() {
    return ImmutableSet.copyOf(this.permissions);
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

}
