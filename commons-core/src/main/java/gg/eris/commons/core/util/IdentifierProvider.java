package gg.eris.commons.core.util;

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
