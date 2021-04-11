package gg.eris.commons.bukkit.command.argument;

import lombok.Getter;

@Getter
public class DoubleArgument extends Argument<Double> {

  private final boolean hasMin;
  private final boolean hasMax;
  private final double min;
  private final double max;

  private DoubleArgument(String argumentId, boolean hasMin, double min, boolean hasMax,
      double max) {
    super(argumentId, value -> (!hasMin || !(value < min)) && (!hasMax || !(value > max)));
    this.hasMin = true;
    this.hasMax = true;
    this.min = min;
    this.max = max;
  }

  public static DoubleArgument.Builder newBuilder(String argumentId) {
    return new DoubleArgument.Builder(argumentId);
  }

  public static class Builder {

    private final String argumentId;

    private boolean hasMin;
    private boolean hasMax;
    private double min;
    private double max;

    private Builder(String argumentId) {
      this.argumentId = argumentId;
    }

    public Builder withMin(double min) {
      this.hasMin = true;
      this.min = min;
      return this;
    }

    public Builder withMax(double max) {
      this.hasMax = true;
      this.max = max;
      return this;
    }

    public DoubleArgument build() {
      return new DoubleArgument(this.argumentId, this.hasMin, this.min, this.hasMax, this.max);
    }
  }

}
