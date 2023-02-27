package me.mattdokn.hammers;

import me.mattdokn.hammers.listeners.HammerListener;
import me.mattdokn.hammers.utility.HammerUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hammers extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register listeners and commands
        getServer().getPluginManager().registerEvents(new HammerListener(), this);
        // Register hammer recipes
        HammerUtils.registerRecipes(this);
    }
}
