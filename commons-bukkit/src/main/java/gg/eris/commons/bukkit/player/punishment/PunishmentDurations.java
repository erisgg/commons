package gg.eris.commons.bukkit.player.punishment;

import gg.eris.commons.bukkit.player.punishment.PunishmentType;
import gg.eris.commons.core.util.Validate;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PunishmentDurations {

  private static final long ONE_DAY = TimeUnit.DAYS.toMillis(1);
  private static final long SEVEN_DAYS = TimeUnit.DAYS.toMillis(7);
  private static final long FOURTEEN_DAYS = TimeUnit.DAYS.toMillis(14);
  private static final long TWENTY_ONE_DAYS = TimeUnit.DAYS.toMillis(21);
  private static final long TWENTY_EIGHT_DAYS = TimeUnit.DAYS.toMillis(28);
  private static final long FIFTY_SIX_DAYS = TimeUnit.DAYS.toMillis(56);
  public static final long INDEFINITE = -1;


  public static long getPunishmentDuration(PunishmentType type, int severity, int count) {
    Validate.notNull(type, "type cannot be null");
    Validate.isTrue(severity >= 1 && severity <= 4, "severity must be between 1 and 4 inclusive");
    Validate.isTrue(count >= 0, "count cannot be negative");
    Validate.isTrue(!(type == PunishmentType.IN_GAME && severity == 1),
        "ingame cannot have severity 1");
    if (type == PunishmentType.CHAT) {
      switch (severity) {
        case 1:
          if (count == 0) {
            return 0;
          } else {
            return SEVEN_DAYS;
          }
        case 2:
          switch (count) {
            case 0:
              return SEVEN_DAYS;
            case 1:
              return FOURTEEN_DAYS;
            case 2:
              return TWENTY_ONE_DAYS;
            case 3:
            default:
              return TWENTY_EIGHT_DAYS;
          }
        case 3:
        case 4:
          return INDEFINITE;
      }
    } else {
      switch (severity) {
        case 2:
          switch (count) {
            case 0:
            case 1:
              return FOURTEEN_DAYS;
            case 2:
              return TWENTY_EIGHT_DAYS;
            case 3:
              return FIFTY_SIX_DAYS;
          }
        case 3:
          switch (count) {
            case 0:
              return FIFTY_SIX_DAYS;
            case 1:
            case 2:
              return INDEFINITE;
          }
        case 4:
          return INDEFINITE;
      }
    }

    return 0;
  }

}
