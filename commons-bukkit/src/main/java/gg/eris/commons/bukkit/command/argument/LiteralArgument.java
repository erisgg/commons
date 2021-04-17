package gg.eris.commons.bukkit.command.argument;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public final class LiteralArgument extends Argument<String> {

  private final Set<String> literals;
  private final boolean caseSensitive;

  private LiteralArgument(String argumentId, Set<String> literals, boolean caseSensitive) {
    super(
        argumentId,
        String.class,
        value -> value,
        value -> {
          if (caseSensitive) {
            return literals.contains(value);
          } else {
            return literals.contains(value.toLowerCase(Locale.ROOT));
          }
        },
        value -> {
          // Soft match shouldn't overwrite soft match for string - literals do not need to soft
          // match
          if (caseSensitive) {
            return literals.contains(value);
          } else {
            return literals.contains(value.toLowerCase(Locale.ROOT));
          }
        }
    );
    this.literals = literals;
    this.caseSensitive = caseSensitive;
  }

  @Override
  public boolean isSimilar(Argument<?> other) {
    if (super.isSimilar(other)) {
      LiteralArgument otherLiteral = (LiteralArgument) other;
      for (String literal : this.literals) {
        if (otherLiteral.literals.contains(literal)) {
          return true;
        }
      }
    }

    return other instanceof PlayerArgument || other instanceof StringArgument;
  }

  public static LiteralArgument.Builder newBuilder(String argumentId) {
    return new Builder(argumentId);
  }

  public static LiteralArgument singleLiteral(String argumentId, String literal) {
    return new Builder(argumentId).withLiterals(literal).build();
  }

  private static class Builder {

    private final String argumentId;
    private boolean caseSensitive;

    private final Set<String> literals;

    private Builder(String argumentId) {
      this.argumentId = argumentId;
      this.caseSensitive = false;
      this.literals = Sets.newHashSet();
    }

    public Builder asCaseSensitive() {
      return asCaseSensitive(true);
    }

    public Builder asCaseSensitive(boolean caseSensitive) {
      this.caseSensitive = caseSensitive;
      return this;
    }

    public Builder withLiterals(String... literals) {
      this.literals.addAll(Arrays.asList(literals));
      return this;
    }

    public LiteralArgument build() {
      if (this.caseSensitive) {
        return new LiteralArgument(this.argumentId,
            ImmutableSet.copyOf(
                this.literals.stream().map(literal -> literal.toLowerCase(Locale.ROOT))
                    .collect(Collectors.toSet())),
            true);
      } else {
        return new LiteralArgument(this.argumentId, ImmutableSet.copyOf(this.literals), false);
      }
    }


  }


}
