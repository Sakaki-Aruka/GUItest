package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        Set<String> UserTags = event.getWhoClicked().getScoreboardTags();


        UUID UserUUID = event.getWhoClicked().getUniqueId();
        Player sender =  event.getWhoClicked().getServer().getPlayer(UserUUID);

        //get click slot (int
        int ClickNo = event.getRawSlot();

        //GUIメニューから呼び出したときに判定して勝手にコマンド送ってくれるやつ(HomeCommands)
        HomeCommandSendAsPlayer HCSAP = new HomeCommandSendAsPlayer();
        HCSAP.HomeCommandFromGUIMenu(sender,UserTags,ClickNo);


        //GUIメニューから呼び出したときに判定して勝手にコマンド送ってくれるやつ(WorldTeleport)
        WorldTeleportCommandSendAsPlayer WTCSAP = new WorldTeleportCommandSendAsPlayer();
        WTCSAP.WorldTeleportFromGUIMenu(sender,UserTags,ClickNo);


        //GUIメニューから呼び出したときに判定して勝手にコマンド送ってくれるやつ(PublicStorage)
        try {
            PublicStorageCommandSendAsPlayer PSCSAP = new PublicStorageCommandSendAsPlayer();
            PSCSAP.PublicStorageFromGUIMenu(sender, ClickNo, UserTags,event.getInventory());
        }catch (NullPointerException NullPo){
            // User clicked blank slot. No process here.
        }



        if(UserTags.contains("MenuOpen") || UserTags.contains("HomeCommandOpen" )|| UserTags.contains("WorldTeleportOpen") || UserTags.contains("PublicStorageOpen") || UserTags.contains("PublicStorageMenuOpen") || UserTags.contains("PublicStorageShowResultOpen") || UserTags.contains("PublicStorageShowResultOpenImitation") || UserTags.contains("PublicStoragePullResultOpen") || UserTags.contains("PublicStoragePullResultOpenImitation")){
            //クリックイベントをキャンセルしたいときはここに書く
            sender.sendMessage("ClickSlot:"+ClickNo);

            //メインメニュー1番をクリック（WTPコマンドを開く）
            if(ClickNo==0 && UserTags.contains("MenuOpen")){
                //WorldTeleportページを表示
                sender.openInventory(new StickClickViewWorldTeleport().WorldTeleportCommands());
                sender.addScoreboardTag("WorldTeleportOpen");

            }

            //メインメニュー2番をクリック(PSTコマンドのテスト
            if(ClickNo==1 && UserTags.contains("MenuOpen")){
                StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();
                //Tag付与まで行う
                SCVPSC.PublicStorageModeDisplay(sender);
            }

            //メインメニュー3番をクリック（Homeコマンドを開く）
            if(ClickNo==2 && UserTags.contains("MenuOpen")){

                //HomeCommandsページを表示
                sender.openInventory(new StickClickViewHomeCommands().HomeCommands());
                //移動すると消えるので再度付与
                sender.addScoreboardTag("HomeCommandOpen");
            }

            //Process to Back to GUI Menu Top
            if(ClickNo==8 &&(UserTags.contains("HomeCommandOpen") || UserTags.contains("WorldTeleportOpen") || UserTags.contains("PublicStorageMenuOpen"))){
                sender.openInventory(new StickClickView().HomeMenu);
                sender.addScoreboardTag("MenuOpen");
            }
            if(ClickNo==53 &&(UserTags.contains("PublicStorageDepositOpen"))){
                sender.openInventory(new StickClickView().HomeMenu);
                sender.addScoreboardTag("MenuOpen");
            }

            if(ClickNo==26 && UserTags.contains("PublicStorageShowResultOpen")){
                sender.openInventory(new StickClickView().HomeMenu);
                sender.addScoreboardTag("MenuOpen");
            }

            //return to ItemID enter process(open sign)
            if((ClickNo==18 && UserTags.contains("PublicStorageShowResultOpen")) || (ClickNo==45 && (UserTags.contains("PublicStorageShowResultOpenImitation")))){
                //
                PublicStorageCommandSendAsPlayer PSCSAP = new PublicStorageCommandSendAsPlayer();
                sender.addScoreboardTag("PublicStorageMenuOpen");
                PSCSAP.PublicStorageFromGUIMenu(sender,0,sender.getScoreboardTags(), sender.getInventory());
            }
            if(ClickNo==53 && UserTags.contains("PublicStorageShowResultOpenImitation")){
                //
                sender.openInventory(new StickClickView().HomeMenu);
                sender.addScoreboardTag("MenuOpen");
            }

            //return to ItemID enter process(open sign)
            if((ClickNo==18 && UserTags.contains("PublicStoragePullResultOpen")) || (ClickNo==45 && UserTags.contains("PublicStoragePullResultOpenImitation"))){
                //
                PublicStorageCommandSendAsPlayer PSCSAP = new PublicStorageCommandSendAsPlayer();
                sender.addScoreboardTag("PublicStorageMenuOpen");
                PSCSAP.PublicStorageFromGUIMenu(sender,0,sender.getScoreboardTags(), sender.getInventory());
            }
            if(ClickNo==53 && UserTags.contains("PublicStoragePullResultOpenImitation")){
                //
                sender.openInventory(new StickClickView().HomeMenu);
                sender.addScoreboardTag("MenuOpen");
            }


            event.setCancelled(true);

        }
        //Public Storage Deposit
        if(UserTags.contains("PublicStorageDepositOpen")){
            //a process for click close button or back GUIMenu top button.
            if(ClickNo==49 || ClickNo==53){
                event.setCancelled(true);
                // back to GUIMenu-top
                if(ClickNo==53){
                    try{
                        for(int i=0;i<54;i++){
                            //sender.sendMessage("LeaveMaterial:"+event.getInventory().getItem(i).getAmount());
                            //sender.sendMessage("LeaveAmount:"+event.getInventory().getItem(i).getAmount());
                        }
                    }catch (NullPointerException NullPo){
                        //no items there
                    }
                    sender.openInventory(new StickClickView().HomeMenu);
                    sender.addScoreboardTag("MenuOpen");
                }
            }else{
                event.setCancelled(false);
            }
        }
        //Public Storage Show
        if(UserTags.contains("PublicStorageShow")){
            //

        }
    }
}
