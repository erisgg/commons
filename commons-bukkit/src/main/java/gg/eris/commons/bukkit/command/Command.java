package gg.eris.commons.bukkit.command;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
 import gg.eris.commons.core.Validate;
import java.util.Arrays;
import java.util.Set;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public final class Command {

  private final String name;
  private final String description;
  private final Set<String> aliases;
  private final boolean playerOnly;

  public Command(String name, String description, Set<String> aliases,
      boolean playerOnly) {
    this.name = name;
    this.playerOnly = playerOnly;
    this.aliases = aliases;
    this.description = description;
  }

  public void run(CommandSender sender, String label, String[] args) {
    if (this.playerOnly) {
      if (!(sender instanceof Player)) {

      } else {

      }
    } else {
    }
  }

  public static Command.Builder builder(String name) {
    return new Builder(name);
  }

  public static class Builder {

    private final String name;
    private final Set<String> aliases;

    private String description;
    private String basePermission;
    private boolean playerOnly;

    private Builder(String name) {
      Validate.isTrue(name != null, "name cannot be null");
      this.name = name;
      this.aliases = Sets.newHashSet();
    }

    public Builder withAliases(String... aliases) {
      this.aliases.addAll(Arrays.asList(aliases));
      return this;
    }

    public Builder asPlayerOnly() {
      return asPlayerOnly(true);
    }

    public Builder asPlayerOnly(boolean playerOnly) {
      this.playerOnly = playerOnly;
      return this;
    }

    public Builder requiresPermission(String permission) {
      this.basePermission = permission;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Command build() {
      Validate.isTrue(this.description != null, "description cannot be null");
      return new Command(
          name,
          this.description,
          ImmutableSet.copyOf(this.aliases),
          this.playerOnly
      );
    }

  }


}
