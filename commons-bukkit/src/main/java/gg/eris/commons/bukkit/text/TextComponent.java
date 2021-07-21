package gg.eris.commons.bukkit.text;

public final class TextComponent {

  private final String text;
  private final TextColor color;
  private final boolean bold;
  private final boolean italic;
  private final boolean underlined;
  private final boolean strikethrough;
  private final boolean obfuscated;
  private final HoverEvent hoverEvent;
  private final ClickEvent clickEvent;

  private TextComponent(String text, TextColor color, boolean bold, boolean italic,
      boolean underlined, boolean strikethrough, boolean obfuscated, HoverEvent hoverEvent,
      ClickEvent clickEvent) {
    this.text = text;
    this.color = color;
    this.bold = bold;
    this.italic = italic;
    this.underlined = underlined;
    this.strikethrough = strikethrough;
    this.obfuscated = obfuscated;
    this.hoverEvent = hoverEvent;
    this.clickEvent = clickEvent;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder("{");

    boolean added = false;

    if (this.text != null) {
      builder.append("\"text\":\"").append(this.text).append("\"");
      added = true;
    }

    if (this.color != null) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"color\":\"").append(this.color.getId()).append("\"");
      added = true;
    }

    if (this.bold) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"bold\":").append("true");
      added = true;
    }

    if (this.italic) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"italic\":").append("true");
      added = true;
    }

    if (this.underlined) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"underlined\":").append("true");
      added = true;
    }

    if (this.strikethrough) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"strikethrough\":").append("true");
      added = true;
    }

    if (this.obfuscated) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"obfuscated\":").append("true");
      added = true;
    }

    if (this.hoverEvent != null) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"hoverEvent\":").append(this.hoverEvent.toJsonMessage());
      added = true;
    }

    if (this.clickEvent != null) {
      if (added) {
        builder.append(",");
      }

      builder.append("\"clickEvent\":").append(this.clickEvent.toJsonMessage());
      added = true;
    }

    builder.append("}");
    return builder.toString();
  }

  public static TextComponent.Builder builder() {
    return new Builder();
  }

  public static TextComponent.Builder builder(String text) {
    return new Builder(text);
  }

  public static final class Builder {

    private String text;
    private TextColor color;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;

    private HoverEvent hoverEvent;
    private ClickEvent clickEvent;

    public Builder() {
      this(null);
    }

    public Builder(String text) {
      this.text = text;
    }

    public Builder text(String text) {
      this.text = text;
      return this;
    }

    public Builder color(TextColor color) {
      this.color = color;
      return this;
    }

    public Builder bold() {
      return bold(true);
    }

    public Builder bold(boolean bold) {
      this.bold = bold;
      return this;
    }

    public Builder italic() {
      return italic(true);
    }

    public Builder italic(boolean italic) {
      this.italic = italic;
      return this;
    }

    public Builder underlined() {
      return underlined(true);
    }

    public Builder underlined(boolean underlined) {
      this.underlined = underlined;
      return this;
    }

    public Builder strikethrough() {
      return strikethrough(true);
    }

    public Builder strikethrough(boolean strikethrough) {
      this.strikethrough = strikethrough;
      return this;
    }

    public Builder obfuscated() {
      return obfuscated(true);
    }

    public Builder obfuscated(boolean obfuscated) {
      this.obfuscated = obfuscated;
      return this;
    }

    public Builder hoverEvent(HoverEvent hoverEvent) {
      this.hoverEvent = hoverEvent;
      return this;
    }

    public Builder clickEvent(ClickEvent clickEvent) {
      this.clickEvent = clickEvent;
      return this;
    }

    public TextComponent build() {
      return new TextComponent(
          this.text,
          this.color,
          this.bold,
          this.italic,
          this.underlined,
          this.strikethrough,
          this.obfuscated,
          this.hoverEvent,
          this.clickEvent
      );
    }

  }
}
