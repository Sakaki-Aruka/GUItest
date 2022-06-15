package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StickClickView {
    Inventory invMenu = Bukkit.createInventory(null,9,"Menu");
    ItemStack zeroST = new zeroMeta().zeroSlot();
    ItemStack oneST = new oneMeta().oneSlot();
    ItemStack twoST = new twoMeta().twoSlot();


    private class zeroMeta {

        public ItemStack zeroSlot(){
            //return ItemStack
            ItemStack zeroSlotIS = new ItemStack(Material.COMPASS, 1);

            ItemMeta zeroMeta = zeroSlotIS.getItemMeta();
            zeroMeta.setLore(Arrays.asList("Jump to your HomePoint"));
            zeroMeta.setDisplayName("HomePoint");

            //set meta data
            zeroSlotIS.setItemMeta(zeroMeta);
            return zeroSlotIS;


        }
    }
    private class oneMeta{
        public ItemStack oneSlot(){
            ItemStack oneSlotIS = new ItemStack(Material.CHEST,1);
            ItemMeta oneMeta = oneSlotIS.getItemMeta();
            oneMeta.setLore(Arrays.asList("Enter Public Storage"));
            oneMeta.setDisplayName("Public Storage");

            oneSlotIS.setItemMeta(oneMeta);
            return oneSlotIS;
        }
    }

    private class twoMeta{
        public ItemStack twoSlot(){
            ItemStack twoSlotIS = new ItemStack(Material.OAK_DOOR,1);
            ItemMeta twoMeta = twoSlotIS.getItemMeta();
            twoMeta.setLore(Arrays.asList("Use Home Commands"));
            twoMeta.setDisplayName("Home Commdns");

            twoSlotIS.setItemMeta(twoMeta);
            return twoSlotIS;
        }
    }


}
