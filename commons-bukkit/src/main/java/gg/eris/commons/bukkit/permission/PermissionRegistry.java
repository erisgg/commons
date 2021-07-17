package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.registry.Registry;

public final class PermissionRegistry extends Registry<Permission> {

  public PermissionRegistry() {
    super();
  }

  public final Permission register(Identifier identifier) {
    return this.register(Permission.of(this, identifier));
  }

  public final Permission registerOfDefault(String name) {
    return this.register(Permission.ofDefault(this, name));
  }
}
