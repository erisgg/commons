package gg.eris.commons.core.registry;

import com.google.common.collect.Maps;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.Collections;
import java.util.Map;

/**
 * The {@link Registry} object represents a collection of registered objects. These objects are all
 * identifiable under the same namespace, which is that of the registry
 *
 * @param <T> is the value type of the registry
 */
public abstract class Registry<T extends Identifiable> {

  private final Map<Identifier, T> registeredItems;

  public Registry() {
    this.registeredItems = Collections.synchronizedMap(Maps.newHashMap());
  }

  /**
   * Registers an item an item of type T within the registry. The item must have a unique identifier
   * that is not already registered within the registry.
   *
   * @param item is the item to register
   * @param <S>  is the type of the item
   * @return the registered item
   */
  public final <S extends T> S register(S item) {
    Validate.isTrue(!this.registeredItems.containsKey(item.getIdentifier()),
        "identifier already registered");
    return (S) this.registeredItems.put(item.getIdentifier(), item);
  }

  /**
   * Retrieves an item from the {@link Registry}
   *
   * @param identifier is the identifier to get
   * @return the item if it is in the {@link Registry}, null if it is not
   */
  public final T get(Identifier identifier) {
    return this.registeredItems.get(identifier);
  }

}
