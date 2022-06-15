package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractEventTest implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        int false_count = 0;

        Player sender = event.getPlayer();
        sender.sendMessage("----------");
        try {
            sender.sendMessage("Player:" + event.getPlayer());
        }catch (NullPointerException nullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            sender.sendMessage("Action:" + event.getAction());
        }catch (NullPointerException NullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            sender.sendMessage("Item:" + event.getItem().toString());
        }catch (NullPointerException NullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            sender.sendMessage("Hand:" + event.getHand().toString());
        }catch (NullPointerException NullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        try {
            sender.sendMessage("Block:" + event.getClickedBlock().toString());
        }catch (NullPointerException NullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        try{
            sender.sendMessage("ItemStack:"+event.getItem());
        }catch (NullPointerException NullPo){
            sender.sendMessage("NullPo");
            false_count += 1;
        }

        if(false_count > 1){

        }else{



        //Menu Slot 1 start

        //Menu SLot 1 end

        sender.sendMessage("---");
        sender.sendMessage("---");

        if(event.getAction().toString().substring(0,11).equals("RIGHT_CLICK")){
            if(event.getItem().toString().substring(0,17).equals("ItemStack{STICK x")){
                if(event.getHand().toString().equals("HAND")){
                    //call Menu slot from StickClickView class

                    Inventory inv = Bukkit.createInventory(null,9,"Menu");
                    inv.setItem(0,new StickClickView().zeroST);
                    inv.setItem(1,new StickClickView().oneST);
                    sender.openInventory(inv);

                }else{
                    sender.sendMessage("Not HAND");
                    sender.sendMessage("Your "+event.getHand().toString());
                }
            }else{
                sender.sendMessage("Not STICK x");
                sender.sendMessage("Your "+event.getItem().toString().substring(0,15));
            }
        }else{
            sender.sendMessage("Not RIGHT_CLICK");
            sender.sendMessage("Your "+event.getAction().toString().substring(0,10));
        }
        }
    }
}
