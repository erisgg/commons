package gg.eris.commons.core.util;

import java.util.Objects;
import lombok.Getter;

@Getter
public final class Identifier {

  private final String namespace;
  private final String value;

  private Identifier(String namespace, String value) {
    this.namespace = namespace;
    this.value = value;
  }

  public static Identifier of(String namespace, String value) {
    return new Identifier(namespace, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Identifier that = (Identifier) o;
    return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.namespace, value);
  }
}
