package gg.eris.commons.bukkit.impl.text;

import lombok.Getter;

public final class TextTag {

  public static final TextTag BOLD = new TextTag("bold", "b");
  public static final TextTag ITALIC = new TextTag("italic", "i");
  public static final TextTag UNDERLINED = new TextTag("underlined", "u");
  public static final TextTag STRIKETHROUGH = new TextTag("strikethrough", "s");
  public static final TextTag OBFUSCATED = new TextTag("obfuscated", "obf");
  public static final TextTag COLOR = new TextTag("color", "col", true);
  public static final TextTag HIGHLIGHT = new TextTag("highlight", "h");
  public static final TextTag EVENT = new TextTag("event", "e", true);
  public static final TextTag RAW = new TextTag("raw");

  public static final TextTag[] TAGS = {
      BOLD, ITALIC, UNDERLINED, STRIKETHROUGH, OBFUSCATED, COLOR, HIGHLIGHT, EVENT, RAW
  };

  @Getter
  private final String id;
  @Getter
  private final String shortHand;
  @Getter
  private final boolean requiresValue;

  private TextTag(String id) {
    this(id, null);
  }

  private TextTag(String id, String shortHand) {
    this(id, shortHand, false);
  }

  private TextTag(String id, String shortHand, boolean requiresValue) {
    this.id = id;
    this.shortHand = shortHand;
    this.requiresValue = requiresValue;
  }

  protected static boolean isBold(String tag) {
    return isTag(BOLD, tag);
  }

  protected static boolean isItalic(String tag) {
    return isTag(ITALIC, tag);
  }

  protected static boolean isUnderlined(String tag) {
    return isTag(UNDERLINED, tag);
  }

  protected static boolean isStrikethrough(String tag) {
    return isTag(STRIKETHROUGH, tag);
  }

  protected static boolean isObfuscated(String tag) {
    return isTag(OBFUSCATED, tag);
  }

  protected static boolean isHighlight(String tag) {
    return isTag(HIGHLIGHT, tag);
  }

  protected static boolean isEvent(String tag) {
    return isTag(EVENT, tag);
  }

  protected static boolean isColor(String tag) {
    return isTag(COLOR, tag);
  }

  protected static boolean isRaw(String tag) {
    return isTag(RAW, tag);
  }

  protected static String getValue(TextTag type, String tag) {
    return type.getValue(tag);
  }

  private static boolean isTag(TextTag tag, String check) {
    return tag.isTag(check);
  }

  public boolean isTag(String check) {
    if (this.requiresValue) {
      return check.equals("/" + this.id) || check.startsWith(this.id + "=") || (
          this.shortHand != null && (check.equals("/" + this.shortHand) || check
              .startsWith(this.shortHand + "=")));
    } else {
      return check.equals(this.id) || check.equals("/" + this.id) || (this.shortHand != null && (
          check.equals(this.shortHand) || check.equals("/" + this.shortHand)));
    }
  }

  public String getValue(String tag) {
    if (tag.startsWith(this.id + "=")) {
      return tag.substring(this.id.length() + 1);
    } else if (this.shortHand != null && tag.startsWith(this.shortHand + "=")) {
      return tag.substring(this.shortHand.length() + 1);
    }

    return null;
  }

}
