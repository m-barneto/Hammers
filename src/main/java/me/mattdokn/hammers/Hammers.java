package me.mattdokn.hammers;

import me.mattdokn.hammers.commands.HammerCommand;
import me.mattdokn.hammers.listeners.HammerListener;
import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hammers extends JavaPlugin {
    public static Hammers instance;
    @Override
    public void onEnable() {
        instance = this;
        // Register listeners and commands
        getServer().getPluginManager().registerEvents(new HammerListener(), this);
        getCommand("hammer").setExecutor(new HammerCommand());
        // Register hammer recipes
        HammerUtils.registerRecipes(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
