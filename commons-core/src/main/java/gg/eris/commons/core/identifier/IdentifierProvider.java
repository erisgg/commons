package gg.eris.commons.core.identifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class IdentifierProvider {

  @Getter
  private final String namespace;

  public Identifier id(String value) {
    return Identifier.of(this.namespace, value);
  }

  public static IdentifierProvider fromIdentifier(Identifier identifier) {
    return new IdentifierProvider(identifier.getNamespace());
  }

}
