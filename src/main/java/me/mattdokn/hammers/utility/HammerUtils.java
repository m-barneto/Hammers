package me.mattdokn.hammers.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Set;

public class HammerUtils {
    public static final int IS_HAMMER = 1;
    public static ItemStack createHammerFromPickaxe(ItemStack pickaxe) {
        ItemStack hammer = new ItemStack(pickaxe.getType());
        ItemMeta hammerMeta = hammer.getItemMeta();

        hammerMeta.displayName(Component.text(hammer.displayName().toString().split(" ")[0] + " Hammer"));

        hammerMeta.setCustomModelData(IS_HAMMER);

        Set<Enchantment> enchants = pickaxe.getEnchantments().keySet();
        for (Enchantment ench : enchants) {
            hammerMeta.addEnchant(ench, pickaxe.getEnchantmentLevel(ench), false);
        }

        hammer.setItemMeta(hammerMeta);

        return hammer;
    }
}
