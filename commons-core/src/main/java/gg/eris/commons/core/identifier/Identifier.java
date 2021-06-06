package gg.eris.commons.core.identifier;

import java.util.Objects;
import lombok.Getter;

/**
 * Used as a key value. Composed of a namespace and a value, to group together common values.
 */
@Getter
public final class Identifier {

  private final String namespace;
  private final String value;

  private Identifier(String namespace, String value) {
    this.namespace = namespace;
    this.value = value;
  }

  /**
   * @param namespace is the namespace of the {@link Identifier}
   * @param value     is the value of the {@link Identifier}
   * @return a new {@link Identifier} with the given namespace and value
   */
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

  @Override
  public String toString() {
    return this.namespace + ":" + this.value;
  }

}
