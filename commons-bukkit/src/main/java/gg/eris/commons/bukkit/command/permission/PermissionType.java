package gg.eris.commons.bukkit.command.permission;

/**
 * Represents the types of permission
 */
public enum PermissionType {
  /**
   * A named permission, which is automatically prefixed with eris.
   */
  NAMED,
  /**
   * An inherited permission, which inherits the permission from the base command (can only be used
   * for sub commands)
   */
  INHERITED,
  /**
   * A permission which only the Alfie and Aifle accounts have (hardcoded that way)
   */
  OWNER;
}
