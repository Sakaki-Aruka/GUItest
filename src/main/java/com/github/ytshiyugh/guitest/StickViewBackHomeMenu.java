package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StickViewBackHomeMenu {
    public ItemStack BackHomeMenu(){
        ItemStack BackHomeMenu = new ItemStack(Material.END_CRYSTAL);
        ItemMeta BackHomeMenuMeta = BackHomeMenu.getItemMeta();
        BackHomeMenuMeta.setDisplayName("Back Menu");
        BackHomeMenuMeta.setLore(Arrays.asList("§fBack to main Menu"));
        BackHomeMenu.setItemMeta(BackHomeMenuMeta);
        return BackHomeMenu;
    }
}
