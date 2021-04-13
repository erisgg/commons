package gg.eris.commons.bukkit.impl.command;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class SubCommandMatchResult {

  private static final SubCommandMatchResult NO_MATCH = new SubCommandMatchResult(null, false,
      null);

  private final SubCommand subCommand;
  private final boolean match;
  private final Map<String, Object> mappedArgs;

  public boolean isEmpty() {
    return NO_MATCH == this;
  }

  public static SubCommandMatchResult noMatch() {
    return NO_MATCH;
  }

  public static SubCommandMatchResult match(SubCommand subCommand, Map<String, Object> mappedArgs) {
    return new SubCommandMatchResult(subCommand, true, mappedArgs);
  }

}
