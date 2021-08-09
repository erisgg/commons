package gg.eris.commons.bukkit.text;

import lombok.Value;

@Value(staticConstructor = "of")
public class ClickEvent {

  public enum Action {
    RUN_COMMAND("run_command"),
    SUGGEST_COMMAND("suggest_command"),
    SUGGEST_TEXT("suggest_command"),
    OPEN_URL("open_url");

    private final String value;

    Action(String value) {
      this.value = value;
    }
  }

  Action action;
  String contents;

  public String toJsonMessage() {
    return
        "{\"action\":\"" + this.action.value + "\",\"value\":\"" +
            (this.action == Action.SUGGEST_COMMAND ? "/" + this.contents : this.contents)
            + "\"}";
  }

}

