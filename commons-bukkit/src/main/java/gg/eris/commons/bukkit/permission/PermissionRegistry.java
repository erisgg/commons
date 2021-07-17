package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.registry.Registry;

public final class PermissionRegistry extends Registry<Permission> {

  public PermissionRegistry() {
    super();
  }

  public final void register(Identifier identifier) {
    this.register(Permission.of(this, identifier));
  }

  public final void registerOfDefault(String name) {
    this.register(Permission.ofDefault(this, name));
  }
}
