package gg.eris.commons.core.registry;

import com.google.common.collect.Maps;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.util.Validate;
import java.util.Collections;
import java.util.Map;

/**
 * The {@link Registry} object represents a collection of registered objects. These objects are all
 * identifiable under the same namespace, which is that of the registry
 *
 * @param <T> is the value type of the registry
 */
public abstract class Registry<T extends Identifiable> implements Identifiable {

  private final IdentifierProvider identifierProvider;
  private final Identifier identifier;

  private final Map<Identifier, T> registeredItems;

  public Registry(String name) {
    this.identifierProvider = new IdentifierProvider(name);
    this.identifier = this.identifierProvider.id("registry");
    this.registeredItems = Collections.synchronizedMap(Maps.newHashMap());
  }

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

  /**
   * Generates an {@link Identifier} in the {@link Registry} namespace
   *
   * @param value is the value of the {@link Identifier} to generate
   * @return the {@link Identifier}
   */
  public final Identifier generateIdentifier(String value) {
    return this.identifierProvider.id(value);
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
    Validate.isTrue(item.getIdentifier().getNamespace().equals(this.identifier.getNamespace()),
        "registry and its contents must have the same namespace");
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

  /**
   * Retrieves an item from the {@link Registry}
   *
   * @param value is the value of the
   * @return the item if it is in the {@link Registry}, null if it is not
   */
  public final T get(String value) {
    return get(generateIdentifier(value));
  }

}
