package gg.eris.commons.bukkit.player.nickname;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.core.util.Pair;
import gg.eris.commons.core.util.Validate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class PlayerNicknameProfile {

  private final ErisPlayer player;

  private GameProfile playerProfile;
  private GameProfile disguisedProfile;
  @Getter
  private String nickName;
  @Getter
  private Pair<String, String> skin;

  public boolean isNicked() {
    return this.disguisedProfile != null;
  }

  public void setRealProfile(Player player) {
    Validate.isNull(this.playerProfile, "player profile has already been set");
    this.playerProfile = PlayerUtil.getHandle(player).getProfile();
  }

  public String getRealUsername() {
    return this.player.getName();
  }

  public String getDisplayName() {
    return isNicked() ? this.disguisedProfile.getName() : getRealUsername();
  }

  public Rank getPriorityDisplayRank() {
    return isNicked() ? RankRegistry.get().DEFAULT : this.player.getPriorityRank();
  }

  public void unnick() {
    setNickName(null, null);
  }

  public void setNickName(String name, Pair<String, String> skin) {
    CraftPlayer player = (CraftPlayer) Bukkit.getPlayer(this.player.getUniqueId());
    EntityPlayer entityPlayer = player.getHandle();

    if (name != null) {
      this.nickName = name;
      this.skin = skin;
      this.disguisedProfile = new GameProfile(this.playerProfile.getId(), name);
      if (skin != null) {
        this.disguisedProfile.getProperties().put("textures", new Property(
            "textures",
            skin.getKey(),
            skin.getValue())
        );
      }
      entityPlayer.setGameProfile(this.disguisedProfile);
    } else {
      this.nickName = null;
      this.skin = null;
      this.disguisedProfile = null;
      entityPlayer.setGameProfile(this.playerProfile);
    }

    PlayerNicknamePipeline.updatePlayer(this.player);

    Bukkit.getScheduler().runTaskAsynchronously(ErisBukkitCommonsPlugin.getInstance(),
        () -> PlayerNicknamePipeline.saveNickname(this.player));
    ;
  }

}
