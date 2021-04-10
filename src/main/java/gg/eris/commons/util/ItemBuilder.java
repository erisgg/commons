package gg.eris.commons.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemBuilder {

  private final ItemStack item;
  private final ItemMeta meta;

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
    this.item = item;
    this.meta = item.getItemMeta();
  }


  /**
   * Sets the name of the item
   *
   * @param name is the name to set
   * @return the item builder
   */
  public ItemBuilder withName(String name) {
    meta.setDisplayName(color(name));
    return this;
  }

  /**
   * Sets the lore of the item
   *
   * @param lore is the lore to set
   * @return the item builder
   */
  public ItemBuilder withLore(String... lore) {
    meta.setLore(Arrays.stream(lore).map(ItemBuilder::color).collect(Collectors.toList()));
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
    this.meta.addEnchant(enchantment, level, true);
    return this;
  }

  public ItemBuilder editMeta(Consumer<ItemMeta> meta) {
    meta.accept(this.meta);
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
      Enchantment enchantment = entry.getKey();
      int level = entry.getValue();
      this.meta.addEnchant(enchantment, level, true);
    }
    return this;
  }

  /**
   * Adds flags to the item
   *
   * @param flags are the flags to add
   * @return the item builder
   */
  public ItemBuilder withFlags(ItemFlag... flags) {
    this.meta.addItemFlags(flags);
    return this;
  }

  @SuppressWarnings("unchecked")
  public <T extends ItemMeta> ItemBuilder applyMeta(Consumer<T> metaApplier) {
    Class<T> metaType = (Class<T>) ItemMeta.class;
    return applyMeta(metaType, metaApplier);
  }

  public <T extends ItemMeta> ItemBuilder applyMeta(Class<T> metaType, Consumer<T> metaApplier) {

    if (!metaType.isAssignableFrom(meta.getClass())) {
      return this;
    }

    T specificMeta = metaType.cast(meta);
    metaApplier.accept(specificMeta);
    return this;
  }

  /**
   * Builds the item
   *
   * @return an itemstack of the item
   */
  public ItemStack build() {
    item.setItemMeta(meta);
    return this.item;
  }


  private static String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

}
