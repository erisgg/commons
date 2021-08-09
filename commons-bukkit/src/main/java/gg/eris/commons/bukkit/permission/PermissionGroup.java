package gg.eris.commons.bukkit.permission;

import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.util.Validate;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

public final class PermissionGroup {

  public static final PermissionGroup ALL = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().ADMIN,
      RankRegistry.get().MODERATOR,
      RankRegistry.get().TRIAL_MODERATOR,
      RankRegistry.get().PARTNER,
      RankRegistry.get().CREATOR,
      RankRegistry.get().DEMIGOD,
      RankRegistry.get().ELITE,
      RankRegistry.get().PRO,
      RankRegistry.get().DEFAULT
  );

  public static final PermissionGroup STAFF = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().ADMIN,
      RankRegistry.get().MODERATOR,
      RankRegistry.get().TRIAL_MODERATOR
  );

  public static final PermissionGroup HIGHER_STAFF = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().ADMIN
  );

  public static final PermissionGroup CONTENT_CREATORS = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().ADMIN,
      RankRegistry.get().PARTNER,
      RankRegistry.get().CREATOR
  );

  public static final PermissionGroup OWNER_DEVELOPER = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER
  );

  public static final PermissionGroup OWNER = new PermissionGroup(
      RankRegistry.get().OWNER
  );

  public static final PermissionGroup DONOR = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().PRO,
      RankRegistry.get().ELITE,
      RankRegistry.get().DEMIGOD
  );

  public static final PermissionGroup DONOR_HIGHER = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().ELITE,
      RankRegistry.get().DEMIGOD
  );

  public static final PermissionGroup DONOR_HIGHEST = new PermissionGroup(
      RankRegistry.get().OWNER,
      RankRegistry.get().DEVELOPER,
      RankRegistry.get().DEMIGOD
  );

  @Getter
  private final Set<Rank> ranks;

  private PermissionGroup(Rank... ranks) {
    Validate.isTrue(ranks.length > 0, "permission group must have 1 or more ranks");
    this.ranks = Set.of(ranks);
  }

  public static PermissionGroup of(Rank... ranks) {
    return new PermissionGroup(ranks);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermissionGroup that = (PermissionGroup) o;
    return Objects.equals(this.ranks, that.ranks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.ranks);
  }

}
