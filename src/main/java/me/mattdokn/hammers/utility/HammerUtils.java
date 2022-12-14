package me.mattdokn.hammers.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HammerUtils {
    public static final int IS_HAMMER = 1;
    public static ItemStack createHammer(ItemStack pickaxe) {
        ItemStack hammer = createHammer(pickaxe.getType());
        ItemMeta meta = hammer.getItemMeta();

        Set<Enchantment> enchants = pickaxe.getEnchantments().keySet();
        for (Enchantment enchant : enchants) {
            meta.addEnchant(enchant, pickaxe.getEnchantmentLevel(enchant), true);
        }

        hammer.setItemMeta(meta);

        return hammer;
    }

    public static ItemStack createHammer(Material material) {
        ItemStack hammer = new ItemStack(material);
        ItemMeta meta = hammer.getItemMeta();
        meta.displayName(Component.text(PlainTextComponentSerializer.plainText().serialize(hammer.displayName()).split(" ")[0].substring(1) + " Hammer"));
        meta.setCustomModelData(IS_HAMMER);
        hammer.setItemMeta(meta);
        return hammer;
    }

    public static void registerRecipes(Plugin plugin) {
        ItemStack woodHammer = createHammer(Material.WOODEN_PICKAXE);
        ItemStack stoneHammer = createHammer(Material.STONE_PICKAXE);
        ItemStack ironHammer = createHammer(Material.IRON_PICKAXE);
        ItemStack goldHammer = createHammer(Material.GOLDEN_PICKAXE);
        ItemStack diamondHammer = createHammer(Material.DIAMOND_PICKAXE);

        ShapedRecipe woodRecipe = new ShapedRecipe(new NamespacedKey(plugin, "wooden_hammer"), woodHammer);
        ShapedRecipe stoneRecipe = new ShapedRecipe(new NamespacedKey(plugin, "stone_hammer"), stoneHammer);
        ShapedRecipe ironRecipe = new ShapedRecipe(new NamespacedKey(plugin, "iron_hammer"), ironHammer);
        ShapedRecipe goldRecipe = new ShapedRecipe(new NamespacedKey(plugin, "golden_hammer"), goldHammer);
        ShapedRecipe diamondRecipe = new ShapedRecipe(new NamespacedKey(plugin, "diamond_hammer"), diamondHammer);

        woodRecipe.shape("BBB", "BBB", " S ");
        woodRecipe.setIngredient('S', Material.STICK);
        woodRecipe.setIngredient('B', new RecipeChoice.MaterialChoice(new ArrayList<Material>() {{
            add(Material.OAK_LOG);
            add(Material.BIRCH_LOG);
            add(Material.SPRUCE_LOG);
            add(Material.JUNGLE_LOG);
            add(Material.DARK_OAK_LOG);
            add(Material.ACACIA_LOG);
            add(Material.MANGROVE_LOG);
        }}));
        stoneRecipe.shape("BBB", "BBB", " S ");
        stoneRecipe.setIngredient('S', Material.STICK);
        stoneRecipe.setIngredient('B', Material.FURNACE);
        ironRecipe.shape("BBB", "BBB", " S ");
        ironRecipe.setIngredient('S', Material.STICK);
        ironRecipe.setIngredient('B', Material.IRON_BLOCK);
        goldRecipe.shape("BBB", "BBB", " S ");
        goldRecipe.setIngredient('S', Material.STICK);
        goldRecipe.setIngredient('B', Material.GOLD_BLOCK);
        diamondRecipe.shape("BBB", "BBB", " S ");
        diamondRecipe.setIngredient('S', Material.STICK);
        diamondRecipe.setIngredient('B', Material.DIAMOND_BLOCK);

        Bukkit.addRecipe(woodRecipe);
        Bukkit.addRecipe(stoneRecipe);
        Bukkit.addRecipe(ironRecipe);
        Bukkit.addRecipe(goldRecipe);
        Bukkit.addRecipe(diamondRecipe);
    }
}
