package com.github.ytshiyugh.guitest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignEdit implements Listener {
    @EventHandler
    public void SignEdit(SignChangeEvent event){
        //
        // this event is not to fired in the time to open the sign. when close sign to fire.

        Player player = event.getPlayer();

        // if user has the tag that "PublicStorageShowOpen" or "PublicStoragePullOpen".
        if(player.getScoreboardTags().contains("PublicStorageShowOpen") || player.getScoreboardTags().contains("PublicStoragePullOpen")){

            if(player.getScoreboardTags().contains("PublicStorageShowOpen")){
                player.removeScoreboardTag("PublicStorageShowOpen");
                player.sendMessage("PublicStorageShowOpen Removed");

                player.addScoreboardTag("PublicStorageShowResultOpen");
            }else if(player.getScoreboardTags().contains("PublicStoragePullOpen")){
                player.removeScoreboardTag("PublicStoragePullOpen");
                player.sendMessage("PublicStoragePullOpen Removed");

                player.addScoreboardTag("PublicStoragePullResultOpen");
            }


            //player entered string.
            String SignString = event.getLine(0);


            Location loc = event.getBlock().getLocation();

            //sign replace air
            loc.getBlock().setType(Material.AIR);


            DepositStrings DString = new DepositStrings();
            GetItemAmountOnStorage GIAoS = new GetItemAmountOnStorage();
            StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();

            if(SignString.length() < 3){
                ItemStack ErrorItemStack = SCVPSC.PublicStorageShowPreparation("A",0);
                if(player.getScoreboardTags().contains("PublicStorageShowResultOpen")){
                    SCVPSC.PublicStorageShowShow(player,ErrorItemStack,"PublicStorageShowResult","PublicStorageShowResultOpen");
                    DString.Error(player,"Illegal Args(ItemID length)");
                    return;
                }else if(player.getScoreboardTags().contains("PublicStoragePullResultOpen")){
                    SCVPSC.PublicStorageShowShow(player,ErrorItemStack,"PublicStoragePullResult","PublicStoragePullResultOpen");
                    DString.Error(player,"Illegal Args(ItemID length)");
                }

            }

            try{

                Pattern ItemIDPattern = Pattern.compile("[^a-z_A-Z]{1,100}");
                Matcher ItemIDMatcher = ItemIDPattern.matcher(SignString);
                boolean ItemIDMatcherBool = ItemIDMatcher.find();
                if(ItemIDMatcherBool==true){
                    DString.Error(player,"Illegal Args(Illegal Symbol)");
                    return;
                }
            }catch (ArrayIndexOutOfBoundsException AIOoBE){
                DString.Error(player,"Illegal Args(Args Shortage)");
                return;
            }

            ItemExistCheck IEC = new ItemExistCheck();
            if(IEC.ItemExistCheck(SignString)){
                //find player input item
                int ReturnCode = GIAoS.GetItemAmountOnStorage(SignString);
                if(ReturnCode != -1 && player.getScoreboardTags().contains("PublicStorageShowResultOpen")){
                    //
                    ItemStack ReturnItemStack = SCVPSC.PublicStorageShowPreparation(SignString,ReturnCode);
                    SCVPSC.PublicStorageShowShow(player,ReturnItemStack,"PublicStorageShowResult","PublicStorageShowResultOpen");
                }else if(ReturnCode != -1 && player.getScoreboardTags().contains("PublicStoragePullResultOpen")){
                    //
                    ItemStack ReturnItemStack = SCVPSC.PublicStorageShowPreparation(SignString,ReturnCode);
                    SCVPSC.PublicStorageShowShow(player,ReturnItemStack,"PublicStoragePullResult","PublicStoragePullResultOpen");
                }
            }else{
                if(player.getScoreboardTags().contains("PublicStorageShowResultOpen")){
                    //
                    //if an item that player send not find
                    ArrayList<String> ReturnCode = GIAoS.GetImitation(SignString);
                    Inventory ShowInventory = SCVPSC.PublicStorageShowImitation(ReturnCode,player,"PublicStorageShow Result");
                    player.openInventory(ShowInventory);
                    player.removeScoreboardTag("PublicStorageShowResultOpen");
                    player.addScoreboardTag("PublicStorageShowResultOpenImitation");
                }else if(player.getScoreboardTags().contains("PublicStoragePullResultOpen")){
                    //
                    ArrayList<String> ReturnCode = GIAoS.GetImitation(SignString);
                    Inventory ShowInventory = SCVPSC.PublicStorageShowImitation(ReturnCode,player,"PublicStoragePull Result");
                    player.openInventory(ShowInventory);
                    player.removeScoreboardTag("PublicStoragePullResultOpen");
                    player.addScoreboardTag("PublicStoragePullResultOpenImitation");
                }

            }


        }


    }
}
