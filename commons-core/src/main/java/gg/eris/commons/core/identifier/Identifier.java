package gg.eris.commons.core.identifier;

import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Validate;
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
    Validate.isTrue(namespace.indexOf(':') == -1 && value.indexOf(':') == -1,
        "namespace and value cannot contain colon (namespace='"
            + namespace + "', value='" + value + "')");
    return new Identifier(namespace, value);
  }

  public static Identifier fromString(String string) {
    Validate.isTrue(isValid(string),
        "identifier can only contain one colon (provided: '" + string + "')");
    int index = string.indexOf(':');
    String namespace = string.substring(0, index);
    String value = string.substring(index + 1);
    return Identifier.of(namespace, value);
  }

  public static boolean isValid(String string) {
    return Text.countMatches(string, ":") == 1;
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
    return Objects.hash(this.namespace, this.value);
  }

  @Override
  public String toString() {
    return this.namespace + ":" + this.value;
  }

}
