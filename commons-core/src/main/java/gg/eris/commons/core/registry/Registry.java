package gg.eris.commons.core.registry;

import com.google.common.collect.Maps;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.Collections;
import java.util.Map;

public abstract class Registry<T extends Identifiable> implements Identifiable {

  private final Identifier identifier;

  private final Map<Identifier, T> registeredItems;

  public Registry(Identifier identifier) {
    this.identifier = identifier;
    this.registeredItems = Collections.synchronizedMap(Maps.newHashMap());
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  public final <S extends T> S register(S item) {
    Validate.isTrue(item.getIdentifier().getNamespace().equals(this.identifier.getNamespace()), "registry and its contents must have the same namespace");
    return (S) this.registeredItems.put(item.getIdentifier(), item);
  }

  public final T get(Identifier identifier) {
    return this.registeredItems.get(identifier);
  }

}
