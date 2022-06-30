package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class StickClickViewWorldTeleport {
    public Inventory WorldTeleportCommands(){
        Inventory WorldTeleport = Bukkit.createInventory(null,9,"WorldTeleport");
        WorldTeleport.setItem(0,Lobby());
        WorldTeleport.setItem(1,OverWorld());
        WorldTeleport.setItem(2,Everyone());
        WorldTeleport.setItem(3,WeeklyEnd());
        WorldTeleport.setItem(8,new StickViewBackHomeMenu().BackHomeMenu());
        return WorldTeleport;
    }
    public ItemStack WeeklyEnd(){
        ItemStack WeeklyEnd = new ItemStack(Material.CHORUS_FLOWER);
        ItemMeta WeeklyEndMeta = WeeklyEnd.getItemMeta();
        WeeklyEndMeta.setDisplayName("Weekly End");
        WeeklyEndMeta.setLore(Arrays.asList("§fJump to Weekly End"));
        WeeklyEnd.setItemMeta(WeeklyEndMeta);
        return WeeklyEnd;
    }

    public ItemStack Everyone(){
        ItemStack Everyone = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta EveryoneMeta = Everyone.getItemMeta();
        EveryoneMeta.setDisplayName("Everyone");
        EveryoneMeta.setLore(Arrays.asList("§fJump to Everyone"));
        Everyone.setItemMeta(EveryoneMeta);
        return Everyone;
    }

    public ItemStack OverWorld(){
        ItemStack OverWorld = new ItemStack(Material.OAK_LOG);
        ItemMeta OverWorldMeta = OverWorld.getItemMeta();
        OverWorldMeta.setDisplayName("OverWorld");
        OverWorldMeta.setLore(Arrays.asList("§fJump to OverWorld"));
        OverWorld.setItemMeta(OverWorldMeta);
        return OverWorld;
    }

    public ItemStack Lobby(){
        ItemStack Lobby = new ItemStack(Material.STONE);
        ItemMeta LobbyMeta = Lobby.getItemMeta();
        LobbyMeta.setDisplayName("Lobby");
        LobbyMeta.setLore(Arrays.asList("§fJump to Lobby"));
        Lobby.setItemMeta(LobbyMeta);
        return Lobby;
    }
}
