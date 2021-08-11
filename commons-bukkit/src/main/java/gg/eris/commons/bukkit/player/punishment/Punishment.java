package gg.eris.commons.bukkit.player.punishment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.print.Doc;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public final class Punishment implements Comparable<Punishment> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final UUID issuer;
  private final UUID recipient;
  private final long date;
  private final PunishmentType type;
  private final int severity;

  public static Punishment newPunishment(UUID issuer, UUID recipient, PunishmentType type,
      int severity) {
    return new Punishment(issuer, recipient, System.currentTimeMillis(), type, severity);
  }

  public static Punishment fromNode(UUID recipient, JsonNode node) {
    return new Punishment(
        UUID.fromString(node.get("issuer").asText()),
        recipient,
        node.get("date").asLong(),
        PunishmentType.fromJsonValue(node.get("type").asText()),
        node.get("severity").asInt()
    );
  }

  public static Punishment fromDocument(UUID recipient, Document document) {
    return new Punishment(
        UUID.fromString(document.getString("issuer")),
        recipient,
        document.getLong("date"),
        PunishmentType.fromJsonValue(document.getString(document.get("type"))),
        document.getInteger("severity")
    );
  }

  public JsonNode toNode() {
    return MAPPER.createObjectNode()
        .put("issuer", this.issuer.toString())
        .put("date", this.date)
        .put("type", this.type.getJsonValue())
        .put("severity", this.severity);
  }

  public Document toDocument() {
    Document result = new Document();
    result.put("issuer", this.issuer.toString());
    result.put("date", this.date);
    result.put("type", this.type.getJsonValue());
    result.put("severity", this.severity);
    return result;
  }

  @Override
  public int compareTo(@NotNull Punishment o) {
    return Date.from(Instant.ofEpochMilli(this.date))
        .compareTo(Date.from(Instant.ofEpochMilli(o.date)));
  }


}
