package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.registry.Registry;

public final class PermissionRegistry extends Registry<Permission> {

  private static final PermissionRegistry REGISTRY = new PermissionRegistry();

  private PermissionRegistry() {
    super();
  }

  public final Permission register(Identifier identifier, PermissionGroup group) {
    return this.register(Permission.of(this, identifier, group));
  }

  public final Permission registerOfDefault(String name, PermissionGroup group) {
    return this.register(Permission.ofDefault(this, name, group));
  }

  public static PermissionRegistry get() {
    return REGISTRY;
  }

}
