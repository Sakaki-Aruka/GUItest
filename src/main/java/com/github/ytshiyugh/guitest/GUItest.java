package com.github.ytshiyugh.guitest;

import org.bukkit.plugin.java.JavaPlugin;

public final class GUItest extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("gui").setExecutor(new GUIcall());
        getServer().getPluginManager().registerEvents(new PlayerInteractEventTest(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
