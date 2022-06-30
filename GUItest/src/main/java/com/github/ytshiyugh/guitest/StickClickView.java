package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StickClickView {

    ItemStack zeroST = new zeroMeta().zeroSlot();
    ItemStack oneST = new oneMeta().oneSlot();
    ItemStack twoST = new twoMeta().twoSlot();
    Inventory HomeMenu = new zeroMeta().invMenu();




    private class zeroMeta {

        public Inventory invMenu(){
            Inventory invMenu = Bukkit.createInventory(null,9,"Menu");
            invMenu.setItem(0,zeroST);
            invMenu.setItem(1,oneST);
            invMenu.setItem(2,twoST);
            //invMenu.setItem(8,new StickViewBackHomeMenu().BackHomeMenu());
            return invMenu;
        }

        public ItemStack zeroSlot(){
            //return ItemStack
            ItemStack zeroSlotIS = new ItemStack(Material.COMPASS, 1);

            ItemMeta zeroMeta = zeroSlotIS.getItemMeta();
            zeroMeta.setLore(Arrays.asList("§fYou can teleport other world."));
            zeroMeta.setDisplayName("World Teleport");

            //set meta data
            zeroSlotIS.setItemMeta(zeroMeta);
            return zeroSlotIS;


        }
    }
    private class oneMeta{
        public ItemStack oneSlot(){
            ItemStack oneSlotIS = new ItemStack(Material.CHEST,1);
            ItemMeta oneMeta = oneSlotIS.getItemMeta();
            oneMeta.setLore(Arrays.asList("§fEnter Public Storage"));
            oneMeta.setDisplayName("Public Storage");

            oneSlotIS.setItemMeta(oneMeta);
            return oneSlotIS;
        }
    }

    private class twoMeta{
        public ItemStack twoSlot(){
            ItemStack twoSlotIS = new ItemStack(Material.OAK_DOOR,1);
            ItemMeta twoMeta = twoSlotIS.getItemMeta();
            twoMeta.setLore(Arrays.asList("§fUse Home Commands"));
            twoMeta.setDisplayName("Home Commdns");

            twoSlotIS.setItemMeta(twoMeta);
            return twoSlotIS;
        }
    }


}
