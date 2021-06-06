package gg.eris.commons.bukkit.permission;

import gg.eris.commons.core.registry.Registry;

public abstract class PermissionRegistry extends Registry<Permission> {

  protected static final String NAMESPACE = "permission";

  public PermissionRegistry() {
    super(NAMESPACE);
  }

}
