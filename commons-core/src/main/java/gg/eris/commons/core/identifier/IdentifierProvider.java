package gg.eris.commons.core.identifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class IdentifierProvider {

  @Getter
  private final String namespace;

  public Identifier id(String value) {
    return Identifier.of(namespace, value);
  }



}
