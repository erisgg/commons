package gg.eris.commons.bukkit.util;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NBTUtil {

  public static final String ANVILLABLE = "SPECIAL_anvillable";
  public static final String CRAFTABLE = "SPECIAL_craftable";
  public static final String BREWABLE = "SPECIAL_brewable";

  public static boolean hasNbtKey(ItemStack item, String nbtKey) {
    if (StackUtil.isNullOrAir(item)) {
      return false;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    if (item instanceof CraftItemStack) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }

    if (!nmsItem.hasTag()) {
      return false;
    }

    NBTTagCompound tag = nmsItem.getTag();
    return tag.hasKey(nbtKey);
  }

  public static String getStringNbtData(ItemStack item, String nbtKey) {
    CraftItemStack.asNMSCopy()

    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    if (item instanceof CraftItemStack) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }

    if (!nmsItem.hasTag()) {
      return null;
    }

    NBTTagCompound tag = nmsItem.getTag();
    if (tag.hasKey(nbtKey)) {
      return tag.getString(nbtKey);
    }

    return null;
  }

  public static int getIntNbtData(ItemStack item, String nbtKey) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    if (item instanceof CraftItemStack) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }
    if (!nmsItem.hasTag()) {
      return 0;
    }

    NBTTagCompound tag = nmsItem.getTag();
    if (tag.hasKey(nbtKey)) {
      return tag.getInt(nbtKey);
    }

    return 0;
  }

  public static boolean getBooleanNbtData(ItemStack item, String nbtKey) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    if (item instanceof CraftItemStack) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }

    if (!nmsItem.hasTag()) {
      return false;
    }

    NBTTagCompound tag = nmsItem.getTag();
    if (tag.hasKey(nbtKey)) {
      return tag.getBoolean(nbtKey);
    }

    return false;
  }

  public static double getDoubleNbtData(ItemStack item, String nbtKey) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    if (item instanceof CraftItemStack) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }

    if (!nmsItem.hasTag()) {
      return 0;
    }

    NBTTagCompound tag = nmsItem.getTag();
    if (tag.hasKey(nbtKey)) {
      return tag.getDouble(nbtKey);
    }

    return 0;
  }

  public static ItemStack setNbtData(ItemStack item, String nbtKey, Object data) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    boolean instanceOf = item instanceof CraftItemStack;
    if (instanceOf) {
      nmsItem = ((CraftItemStack) item).handle;
    } else {
      nmsItem = CraftItemStack.asNMSCopy(item);
    }

    NBTTagCompound compound;
    if (!nmsItem.hasTag()) {
      compound = new NBTTagCompound();
    } else {
      compound = nmsItem.getTag();
    }

    if (data instanceof String) {
      compound.setString(nbtKey, (String) data);
    } else if (data instanceof Integer) {
      compound.setInt(nbtKey, (Integer) data);
    } else if (data instanceof Double) {
      compound.setDouble(nbtKey, (Double) data);
    } else if (data instanceof Boolean) {
      compound.setBoolean(nbtKey, (Boolean) data);
    } else if (data instanceof NBTBase) {
      compound.set(nbtKey, (NBTBase) data);
    }

    nmsItem.setTag(compound);

    return instanceOf ? item : CraftItemStack.asBukkitCopy(nmsItem);
  }

  public static ItemStack setAnvillable(ItemStack item, boolean anvillable) {
    return setNbtData(item, ANVILLABLE, anvillable);
  }

  public static ItemStack setCraftable(ItemStack item, boolean craftable) {
    return setNbtData(item, CRAFTABLE, craftable);
  }

  public static ItemStack setBrewable(ItemStack item, boolean brewable) {
    return setNbtData(item, BREWABLE, brewable);
  }

  public static boolean isAnvillable(ItemStack item) {
    if (!hasNbtKey(item, ANVILLABLE)) {
      return true;
    }

    return getBooleanNbtData(item, ANVILLABLE);
  }

  public static boolean isCraftable(ItemStack item) {
    if (!hasNbtKey(item, CRAFTABLE)) {
      return true;
    }

    return getBooleanNbtData(item, CRAFTABLE);
  }

  public static boolean isBrewable(ItemStack item) {
    if (!hasNbtKey(item, BREWABLE)) {
      return true;
    }

    return getBooleanNbtData(item, BREWABLE);
  }

}
