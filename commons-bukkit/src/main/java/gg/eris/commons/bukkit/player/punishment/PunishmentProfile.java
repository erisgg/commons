package gg.eris.commons.bukkit.player.punishment;


import com.fasterxml.jackson.databind.JsonNode;
import gg.eris.commons.bukkit.util.CC;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PunishmentProfile {

  @Getter
  private final UUID owner;
  private final List<Punishment> punishments;
  private int chatPunishmentCount;
  private int inGamePunishmentCount;

  public PunishmentProfile(UUID owner, List<Punishment> punishments) {
    this.owner = owner;
    this.punishments = punishments;
    for (Punishment punishment : punishments) {
      if (punishment.getType() == PunishmentType.CHAT) {
        this.chatPunishmentCount++;
      } else {
        this.inGamePunishmentCount++;
      }
    }

    punishments.sort(Comparator.reverseOrder());
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
    this.punishments.add(0, punishment); // Add to start of the list
    if (punishment.getType() == PunishmentType.CHAT) {
      this.chatPunishmentCount++;
    } else {
      this.inGamePunishmentCount++;
      if (getBanDuration() > 0) {
        Player player = Bukkit.getPlayer(this.owner);
        if (player != null) {
          player.kickPlayer(CC.GOLD.bold() + "(!) " + CC.GOLD + "You have been " + CC.YELLOW +
              "banned" + CC.GOLD + ".");
        }
      }
    }
  }

  public long getMuteDuration() {
    Punishment punishment = getLatestChatInfraction();
    if (punishment == null) {
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
    if (punishment == null) {
      return 0;
    }
    return PunishmentDurations.getPunishmentDuration(
        punishment.getType(),
        punishment.getSeverity(),
        this.inGamePunishmentCount - 1 // The durations don't include this (starts 0)
    );
  }

  public List<JsonNode> toJsonNodes() {
    return this.punishments.stream().map(Punishment::toNode).collect(Collectors.toList());
  }
}
