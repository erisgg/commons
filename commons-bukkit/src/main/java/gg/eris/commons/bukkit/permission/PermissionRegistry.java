package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;

public abstract class PermissionRegistry extends Registry<Permission> {

  private static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("eris_permission");

  public PermissionRegistry() {
    super(IDENTIFIER_PROVIDER.id("permission_registry"));
  }

}
