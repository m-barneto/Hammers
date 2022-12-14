package me.mattdokn.hammers;

import me.mattdokn.hammers.commands.HammerCommand;
import me.mattdokn.hammers.listeners.HammerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hammers extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new HammerListener(), this);
        getCommand("hammer").setExecutor(new HammerCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
