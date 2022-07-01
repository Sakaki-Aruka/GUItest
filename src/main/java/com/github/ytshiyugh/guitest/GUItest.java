package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class GUItest extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic


        getCommand("gui").setExecutor(new GUIcall());
        getServer().getPluginManager().registerEvents(new PlayerInteractEventTest(),this);
        getServer().getPluginManager().registerEvents(new InventoryClick(),this);
        getServer().getPluginManager().registerEvents(new InventoryClose(),this);
        getServer().getPluginManager().registerEvents(new SignEdit(),this);
        getCommand("newpstd").setExecutor(new DepositMainClass());
        getCommand("newpsts").setExecutor(new ShowMainClass());
        getCommand("dbtest").setExecutor(new DataBaseConnectionTest());

        //getServer().getPluginManager().registerEvents(new Ichikara(),this);


        Path JarCurrent = Paths.get("").toAbsolutePath();
        Path MenuBool = Paths.get(JarCurrent+"/MenuBool");
        if(Files.exists(MenuBool)){
            getLogger().info("MenuBool check Passed");
        }else{
            File MenuBoolMkdir = new File(JarCurrent+"/MenuBool");
            MenuBoolMkdir.mkdir();
            getLogger().info("MenuBool mkdir did.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
