package gg.eris.commons.core.registry;

import com.google.common.collect.Maps;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;

public abstract class Registry<T> {

  @Getter
  private final Identifier identifier;

  private final Map<Identifier, T> registeredItems;

  public Registry(Identifier identifier) {
    this.identifier = identifier;
    this.registeredItems = Collections.synchronizedMap(Maps.newHashMap());
  }

  public <S extends T> S register(Identifier identifier, S item) {
    return (S) this.registeredItems.put(identifier, item);
  }

  public T get(Identifier identifier) {
    return this.registeredItems.get(identifier);
  }

}
