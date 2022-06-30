package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class PlayerInteractEventTest implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        int false_count = 0;

        Player sender = event.getPlayer();
        //sender.sendMessage("----------");

        System.out.println("Player:"+event.getPlayer());

        try {
            Player player = event.getPlayer();
        }catch (NullPointerException nullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            String Action = event.getAction().toString();
        }catch (NullPointerException NullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            String GetItem = event.getItem().toString();
        }catch (NullPointerException NullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            String HandStr = event.getHand().toString();
            //sender.sendMessage("Hand:" + event.getHand().toString());
        }catch (NullPointerException NullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            String block = event.getClickedBlock().toString();
            //sender.sendMessage("Block:" + event.getClickedBlock().toString());
        }catch (NullPointerException NullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        try{
            ItemStack getItem = event.getItem();
            //sender.sendMessage("ItemStack:"+event.getItem());
            if(event.getItem()==null){
                false_count +=1;
            }
        }catch (NullPointerException NullPo){
            //sender.sendMessage("NullPo");
            false_count += 1;
        }

        if(false_count > 1){

        }else{



        //Menu Slot 1 start

        //Menu SLot 1 end

        sender.sendMessage("---");
        sender.sendMessage("---");

        int MenuCheck = 0;

        if(event.getAction().toString().substring(0,11).equals("RIGHT_CLICK")){
            if(event.getItem().toString().substring(0,17).equals("ItemStack{STICK x")){
                if(event.getHand().toString().equals("HAND")){
                    //call Menu slot from StickClickView class

                    //from StickClickView MenuPageTop
                    sender.openInventory(new StickClickView().HomeMenu);

                    //add tag "MenuOpen".This tag use to check inventory click event.
                    //sender.sendMessage("sender has these tags.:"+event.getPlayer().getScoreboardTags());
                    sender.addScoreboardTag("MenuOpen");

                }else{
                    //sender.sendMessage("Not HAND");
                    //sender.sendMessage("Your "+event.getHand().toString());
                    MenuCheck+=1;
                }
            }else{
                //sender.sendMessage("Not STICK x");
                //sender.sendMessage("Your "+event.getItem().toString().substring(0,15));
                MenuCheck+=1;
            }
        }else{
            //sender.sendMessage("Not RIGHT_CLICK");
            //sender.sendMessage("Your "+event.getAction().toString().substring(0,10));
            MenuCheck+=1;
        }
        if(MenuCheck!=0){
            //

        }
        }

    }


}
