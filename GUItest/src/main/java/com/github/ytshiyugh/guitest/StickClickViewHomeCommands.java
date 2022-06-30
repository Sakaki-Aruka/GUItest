package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StickClickViewHomeCommands {

    public Inventory HomeCommands(){
        Inventory HomeCommands = Bukkit.createInventory(null,9,"HomeCommands");
        HomeCommands.setItem(0,Teleport());
        HomeCommands.setItem(2,Set());
        HomeCommands.setItem(4,Remove());
        HomeCommands.setItem(8,new StickViewBackHomeMenu().BackHomeMenu());
        return HomeCommands;
    }

    public ItemStack Teleport(){
        ItemStack Teleport = new ItemStack(Material.ELYTRA);
        ItemMeta TeleportMeta = Teleport.getItemMeta();
        TeleportMeta.setLore(Arrays.asList("§fGo HomePoint"));
        TeleportMeta.setDisplayName("Teleport");
        Teleport.setItemMeta(TeleportMeta);
        return Teleport;
    }

    public ItemStack Remove(){
        ItemStack Remove = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta RemoveMeta = Remove.getItemMeta();
        RemoveMeta.setLore(Arrays.asList("§fRemove HomePoint"));
        RemoveMeta.setDisplayName("Remove");
        Remove.setItemMeta(RemoveMeta);
        return Remove;
    }

    public ItemStack Set(){
        ItemStack Set = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta SetMeta = Set.getItemMeta();
        SetMeta.setLore(Arrays.asList("§fSet HomePoint"));
        SetMeta.setDisplayName("Set");
        Set.setItemMeta(SetMeta);
        return Set;
    }
}
