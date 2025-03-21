package me.mattdokn.hammers.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class HammerUtils {
    public static final int IS_HAMMER = 1;
    public static ArrayList<NamespacedKey> recipes;

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
        ItemStack netheriteHammer = createHammer(Material.NETHERITE_PICKAXE);

        NamespacedKey woodHammerKey = new NamespacedKey(plugin, "wooden_hammer");
        NamespacedKey stoneHammerKey = new NamespacedKey(plugin, "stone_hammer");
        NamespacedKey ironHammerKey = new NamespacedKey(plugin, "iron_hammer");
        NamespacedKey goldHammerKey = new NamespacedKey(plugin, "golden_hammer");
        NamespacedKey diamondHammerKey = new NamespacedKey(plugin, "diamond_hammer");
        NamespacedKey netheriteHammerKey = new NamespacedKey(plugin, "netherite_hammer");

        ShapedRecipe woodRecipe = new ShapedRecipe(woodHammerKey, woodHammer);
        ShapedRecipe stoneRecipe = new ShapedRecipe(stoneHammerKey, stoneHammer);
        ShapedRecipe ironRecipe = new ShapedRecipe(ironHammerKey, ironHammer);
        ShapedRecipe goldRecipe = new ShapedRecipe(goldHammerKey, goldHammer);
        ShapedRecipe diamondRecipe = new ShapedRecipe(diamondHammerKey, diamondHammer);
        SmithingRecipe netheriteRecipe = new SmithingTransformRecipe(
                netheriteHammerKey,
                netheriteHammer,
                new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                new RecipeChoice.ExactChoice(diamondHammer),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT)
        );

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
        Bukkit.addRecipe(netheriteRecipe);

        recipes = new ArrayList<>();
        recipes.add(woodHammerKey);
        recipes.add(stoneHammerKey);
        recipes.add(ironHammerKey);
        recipes.add(goldHammerKey);
        recipes.add(diamondHammerKey);
        recipes.add(netheriteHammerKey);
    }
}
