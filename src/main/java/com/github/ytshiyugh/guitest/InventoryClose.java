package com.github.ytshiyugh.guitest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;
import java.util.UUID;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){


        UUID senderUUID = event.getPlayer().getUniqueId();
        Player sender = event.getPlayer().getServer().getPlayer(senderUUID);

        /*
        String MenuBool = String.valueOf(event.getPlayer().getScoreboardTags().contains("MenuOpen"));
        sender.sendMessage("-----\nCloseEvent:"+event+"\nMenuOpen are there Bool:"+MenuBool+"\n-----");

         */

        Set<String> UserTags = event.getPlayer().getScoreboardTags();
        if(UserTags.contains("MenuOpen")){
            event.getPlayer().removeScoreboardTag("MenuOpen");
            sender.sendMessage("MenuOpen Removed");
        }

        if(UserTags.contains("HomeCommandOpen")){
            event.getPlayer().removeScoreboardTag("HomeCommandOpen");
            sender.sendMessage("HomeCommandOpen Removed");
        }

        if(UserTags.contains("WorldTeleportOpen")){
            event.getPlayer().removeScoreboardTag("WorldTeleportOpen");
            sender.sendMessage("WorldTeleportOpen Removed");
        }

        if(UserTags.contains("PublicStorageOpen")){
            event.getPlayer().removeScoreboardTag("PublicStorageOpen");
            sender.sendMessage("PublicStorageOpen Removed");
        }

        if(UserTags.contains("PublicStorageMenuOpen")){
            event.getPlayer().removeScoreboardTag("PublicStorageMenuOpen");
            sender.sendMessage("PublicStorageMenuOpen Removed");
        }

        if(UserTags.contains("PublicStorageShowResultOpen")){
            event.getPlayer().removeScoreboardTag("PublicStorageShowResultOpen");
            sender.sendMessage("PublicStorageShowResultOpen Removed");
        }

        if(UserTags.contains("PublicStorageShowResultOpenImitation")){
            event.getPlayer().removeScoreboardTag("PublicStorageShowResultOpenImitation");
            sender.sendMessage("PublicStorageShowResultOpenImitation Removed");
        }

        if(UserTags.contains("PublicStoragePullResultOpen")){
            event.getPlayer().removeScoreboardTag("PublicStoragePullResultOpen");
            sender.sendMessage("PublicStoragePullResultOpen Removed");
        }

        if(UserTags.contains("PublicStoragePullResultOpenImitation")){
            event.getPlayer().removeScoreboardTag("PublicStoragePullResultOpenImitation");
            sender.sendMessage("PublicStoragePullResultOpenImitation Removed");
        }

        if(UserTags.contains("PublicStorageDepositOpen")){
            try{
                for(int i=0;i<54;i++){
                    //sender.sendMessage("LeaveMaterial:"+event.getInventory().getItem(i).getType());
                    //sender.sendMessage("LeaveAmount:"+event.getInventory().getItem(i).getAmount());
                }
            }catch(NullPointerException NullPo){
                //null here
            }
            event.getPlayer().removeScoreboardTag("PublicStorageDepositOpen");
            sender.sendMessage("PublicStorageDepositOpen Removed");

        }

        /*
        if(UserTags.contains("PublicStorageShowOpen")){
            event.getPlayer().removeScoreboardTag("PublicStorageShowOpen");
            sender.sendMessage("PublicStorageShowOpen Removed");
        }

         */

    }
}
