package me.mattdokn.hammers.commands;

import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HammerCommand implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            ItemStack hammer = HammerUtils.createHammer(new ItemStack(Material.DIAMOND_PICKAXE));
            p.getInventory().addItem(hammer);
        }
        return true;
    }
}
