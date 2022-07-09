package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.UUID;

public class InventoryDrag implements Listener {
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        String playerName = event.getWhoClicked().getName();
        Player player = Bukkit.getPlayer(playerName);
        if(player.getScoreboardTags().contains("PublicStoragePull-Pull")){
            event.setCancelled(true);
        }

    }
}
