package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


public class GUIcall implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        //here
        Inventory inv = Bukkit.createInventory(null,9,"Menu");

        ItemStack firstSlot = new ItemStack(Material.COMPASS,1);

        ItemMeta firstMeta = firstSlot.getItemMeta();
        firstMeta.setLore(Arrays.asList("Jump to your HomePoint"));
        firstMeta.setDisplayName("HomePoint");

        //set meta data
        firstSlot.setItemMeta(firstMeta);
        //set item on No.0 slot
        inv.setItem(0,firstSlot);


        //inv.setItem();

        ((Player) sender).openInventory(inv);



        return false;
    }
}
