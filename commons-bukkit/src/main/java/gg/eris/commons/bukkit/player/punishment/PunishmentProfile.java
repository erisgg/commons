package gg.eris.commons.bukkit.player.punishment;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.util.CC;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public final class PunishmentProfile {

  @Getter
  @EqualsAndHashCode.Include
  private final UUID owner;
  private final List<Punishment> punishments;
  @Getter
  private long lastUnmute;
  @Getter
  private long lastUnban;
  private int chatPunishmentCount;
  private int inGamePunishmentCount;

  public PunishmentProfile(UUID owner, List<Punishment> punishments, long lastUnmute,
      long lastUnban) {
    this.owner = owner;
    this.punishments = Lists.newArrayList(punishments);
    this.lastUnmute = lastUnmute;
    this.lastUnban = lastUnban;
    for (Punishment punishment : punishments) {
      if (punishment.getType() == PunishmentType.CHAT) {
        this.chatPunishmentCount++;
      } else {
        this.inGamePunishmentCount++;
      }
    }
    this.punishments.sort(Comparator.reverseOrder());
  }

  public Punishment getLatestChatInfraction() {
    return this.punishments.stream()
        .filter(punishment -> punishment.getType() == PunishmentType.CHAT)
        .findFirst().orElse(null);
  }

  public Punishment getLatestInGameInfraction() {
    return this.punishments.stream()
        .filter(punishment -> punishment.getType() == PunishmentType.IN_GAME)
        .findFirst()
        .orElse(null);
  }

  public Punishment getLatestPunishment() {
    return this.punishments.stream()
        .findFirst().orElse(null);
  }

  public void addPunishment(Punishment punishment) {
    this.punishments.add(punishment); // Add to start of the list
    this.punishments.sort(Comparator.reverseOrder());
    if (punishment.getType() == PunishmentType.CHAT) {
      this.chatPunishmentCount++;
    } else {
      this.inGamePunishmentCount++;
      if (getBanDuration() > 0) {
        Player player = Bukkit.getPlayer(this.owner);
        if (player != null) {
          Bukkit.getScheduler().runTask(ErisBukkitCommonsPlugin.getInstance(), () ->
              player.kickPlayer(CC.GOLD.bold() + "(!) " + CC.GOLD + "You have been "
                  + CC.YELLOW + "banned" + CC.GOLD + "."));
        }
      }
    }
  }

  public void unmute() {
    this.lastUnmute = System.currentTimeMillis();
  }

  public void unban() {
    this.lastUnban = System.currentTimeMillis();
  }

  public long getMuteDuration() {
    Punishment punishment = getLatestChatInfraction();
    if (punishment == null || this.lastUnmute > punishment.getDate()) {
      return 0;
    }

    return PunishmentDurations.getPunishmentDuration(
        punishment.getType(),
        punishment.getSeverity(),
        this.chatPunishmentCount - 1 // The durations don't include this (starts 0)
    );
  }

  public long getBanDuration() {
    Punishment punishment = getLatestInGameInfraction();
    if (punishment == null || this.lastUnban > punishment.getDate()) {
      return 0;
    }
    return PunishmentDurations.getPunishmentDuration(
        punishment.getType(),
        punishment.getSeverity(),
        this.inGamePunishmentCount - 1 // The durations don't include this (starts 0)
    );
  }

  public List<JsonNode> toPunishmentDataNodes() {
    return this.punishments.stream().map(Punishment::toNode).collect(Collectors.toList());
  }
}
