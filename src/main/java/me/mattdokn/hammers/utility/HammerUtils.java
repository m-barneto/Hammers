package me.mattdokn.hammers.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Set;

public class HammerUtils {
    public static final int IS_HAMMER = 1;
    public static ItemStack createHammer(ItemStack pickaxe) {
        ItemStack hammer = createHammer(pickaxe.getType());
        ItemMeta meta = hammer.getItemMeta();

        Set<Enchantment> enchants = pickaxe.getEnchantments().keySet();
        for (Enchantment ench : enchants) {
            meta.addEnchant(ench, pickaxe.getEnchantmentLevel(ench), false);
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
}
