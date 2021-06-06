package gg.eris.commons.core.identifier;

import java.util.function.Consumer;

/**
 * Marks an object as {@link Identifiable}, meaning it has an associated {@link Identifier}
 */
@FunctionalInterface
public interface Identifiable {

  /**
   * Returns the {@link Identifier} of the object
   *
   * @return the {@link Identifier} of the object
   */
  Identifier getIdentifier();

}
