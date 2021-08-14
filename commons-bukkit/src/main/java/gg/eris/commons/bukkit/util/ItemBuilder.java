package gg.eris.commons.bukkit.util;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemBuilder {

  private ItemStack item;

  /**
   * Creates an ItemBuilder instance of a given type with amount 1
   *
   * @param material is the material of the item
   */
  public ItemBuilder(Material material) {
    this(material, 1);
  }

  /**
   * Creates an ItemBuilder instance of a given type with a given amount
   *
   * @param material is the material of the item
   * @param amount   is the amount of the item
   */
  public ItemBuilder(Material material, int amount) {
    this(new ItemStack(material, amount));
  }

  /**
   * Creates an ItemBuilder instance of a given item
   *
   * @param item is the new item
   */
  public ItemBuilder(ItemStack item) {
    this.item = item.clone();
  }


  /**
   * Sets the name of the item
   *
   * @param name is the name to set
   * @return the item builder
   */
  public ItemBuilder withName(String name) {
    ItemMeta meta = getMeta();
    meta.setDisplayName(color(name));
    saveMeta(meta);
    return this;
  }

  /**
   * Sets the lore of the item
   *
   * @param lore is the lore to set
   * @return the item builder
   */
  public ItemBuilder withLore(String... lore) {
    ItemMeta meta = getMeta();
    meta.setLore(Arrays.stream(lore).map(ItemBuilder::color).collect(Collectors.toList()));
    saveMeta(meta);
    return this;
  }

  /**
   * Adds to the lore of the item
   *
   * @param newLore is the new lore to add
   * @return the item builder
   */
  public ItemBuilder addLore(String... newLore) {
    ItemMeta meta = getMeta();
    List<String> lore = meta.getLore();
    if (lore == null) {
      lore = Lists.newArrayList();
    }
    lore.addAll(Arrays.asList(newLore));
    meta.setLore(lore);
    saveMeta(meta);
    return this;
  }

  public ItemBuilder withDurability(short durability) {
    this.item.setDurability(durability);
    return this;
  }

  /**
   * Sets the amount of the item
   *
   * @param amount is the amount to set
   * @return the item builder
   */
  public ItemBuilder withAmount(int amount) {
    Validate.isTrue(amount >= 0, "Amount cannot be negative");
    this.item.setAmount(amount);
    return this;
  }

  /**
   * Sets the type of the item
   *
   * @param material is the material to set
   * @return the item builder
   */
  public ItemBuilder withMaterial(Material material) {
    this.item.setType(material);
    return this;
  }

  /**
   * Adds an enchantment to the item
   *
   * @param enchantment is the enchantment
   * @param level       is the level to set
   * @return the item builder
   */
  public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
    ItemMeta meta = getMeta();
    if (meta instanceof EnchantmentStorageMeta) {
      ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
    } else {
      meta.addEnchant(enchantment, level, true);
    }

    saveMeta(meta);
    return this;
  }

  public ItemBuilder editMeta(Consumer<ItemMeta> metaConsumer) {
    ItemMeta meta = getMeta();
    metaConsumer.accept(meta);
    saveMeta(meta);
    return this;
  }

  /**
   * Adds enchantments to the item
   *
   * @param enchantments is the map of enchantment/level to add
   * @return the item builder
   */
  public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchantments) {
    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
      withEnchantment(entry.getKey(), entry.getValue());
    }
    return this;
  }

  public ItemBuilder unbreakable() {
    return unbreakable(true);
  }

  public ItemBuilder unbreakable(boolean unbreakable) {
    ItemMeta meta = getMeta();
    meta.spigot().setUnbreakable(unbreakable);
    saveMeta(meta);
    return this;
  }

  /**
   * Adds flags to the item
   *
   * @param flags are the flags to add
   * @return the item builder
   */
  public ItemBuilder withFlags(ItemFlag... flags) {
    ItemMeta meta = getMeta();
    meta.addItemFlags(flags);
    saveMeta(meta);
    return this;
  }

  @SuppressWarnings("unchecked")
  public <T extends ItemMeta> ItemBuilder applyMeta(Consumer<T> metaApplier) {
    Class<T> metaType = (Class<T>) ItemMeta.class;
    return applyMeta(metaType, metaApplier);
  }

  public <T extends ItemMeta> ItemBuilder applyMeta(Class<T> metaType, Consumer<T> metaApplier) {
    ItemMeta meta = getMeta();
    if (!metaType.isAssignableFrom(meta.getClass())) {
      return this;
    }

    T specificMeta = metaType.cast(meta);
    metaApplier.accept(specificMeta);
    saveMeta(meta);
    return this;
  }

  public ItemBuilder nonAnvillable() {
    return anvillable(false);
  }

  public ItemBuilder anvillable(boolean anvillable) {
    this.item = NBTUtil.setAnvillable(this.item, anvillable);
    return this;
  }

  public ItemBuilder nonCraftable() {
    return craftable(false);
  }

  public ItemBuilder craftable(boolean craftable) {
    this.item = NBTUtil.setCraftable(this.item, craftable);
    return this;
  }

  public ItemBuilder nonBrewable() {
    return brewable(false);
  }

  public ItemBuilder unstackable() {
    return unstackable(true);
  }

  public ItemBuilder unstackable(boolean unstackable) {
    this.item = NBTUtil.setUnstackable(this.item, unstackable);
    return this;
  }

  public ItemBuilder brewable(boolean brewable) {
    this.item = NBTUtil.setBrewable(this.item, brewable);
    return this;
  }

  /**
   * Builds the item
   *
   * @return a built {@link ItemStack}
   */
  public ItemStack build() {
    return this.item;
  }

  private ItemMeta getMeta() {
    return this.item.getItemMeta();
  }

  public void saveMeta(ItemMeta meta) {
    this.item.setItemMeta(meta);
  }

  private static String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

}
