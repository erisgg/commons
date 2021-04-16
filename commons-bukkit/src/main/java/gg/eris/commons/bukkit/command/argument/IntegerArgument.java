package gg.eris.commons.bukkit.command.argument;

import lombok.Getter;

@Getter
public final class IntegerArgument extends Argument<Integer> {

  private final boolean hasMin;
  private final boolean hasMax;
  private final int min;
  private final int max;

  private IntegerArgument(String argumentId, boolean hasMin, int min, boolean hasMax,
      int max) {
    super(
        argumentId,
        Integer.class,
        value -> {
          try {
            return Integer.valueOf(value);
          } catch (NumberFormatException ignored) {
            return null;
          }
        },
        value -> (!hasMin || !(value < min)) && (!hasMax || !(value > max))
    );
    this.hasMin = true;
    this.hasMax = true;
    this.min = min;
    this.max = max;
  }

  public static IntegerArgument.Builder newBuilder(String argumentId) {
    return new IntegerArgument.Builder(argumentId);
  }

  public static class Builder {

    private final String argumentId;

    private boolean hasMin;
    private boolean hasMax;
    private int min;
    private int max;

    private Builder(String argumentId) {
      this.argumentId = argumentId;
    }

    public IntegerArgument.Builder withMin(int min) {
      this.hasMin = true;
      this.min = min;
      return this;
    }

    public IntegerArgument.Builder withMax(int max) {
      this.hasMax = true;
      this.max = max;
      return this;
    }

    public IntegerArgument build() {
      return new IntegerArgument(this.argumentId, this.hasMin, this.min, this.hasMax, this.max);
    }
  }

}
