package com.github.ytshiyugh.guitest;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Stream;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        Set<String> UserTags = event.getWhoClicked().getScoreboardTags();


        UUID UserUUID = event.getWhoClicked().getUniqueId();
        Player sender =  event.getWhoClicked().getServer().getPlayer(UserUUID);

        //get click slot (int
        int ClickNo = event.getRawSlot();

        //
        StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();

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

            if(ClickNo==13 && UserTags.contains("PublicStoragePullResultOpen")){
                //pull process
                try{
                    String ClickItemID = event.getCurrentItem().getType().toString();
                    sender.sendMessage("You clicked:"+ClickItemID);
                    //
                    Material LastProcessMaterial = event.getCurrentItem().getType();
                    Inventory Pull =SCVPSC.PublicStoragePullLastProcess(LastProcessMaterial,ClickItemID,"PublicStorage Pull");
                    //open final inventory ( in pull process)
                    sender.openInventory(Pull);

                    //add tag
                    sender.addScoreboardTag("PublicStoragePull-Pull");
                    //make temporary data on mysql db(table = temporary)
                    //first step is delete
                    String Name = event.getWhoClicked().getName();
                    String SQLSenteceDelete = "delete from temporary where playername='"+Name+"';";
                    DataBaseConnectionTest DBCT = new DataBaseConnectionTest();
                    DBCT.DatabaseUPDATE(SQLSenteceDelete);

                    //second step is insert
                    String SQLSentenceInsert = "insert into temporary(itemid,playername,pull,deposit,rawslot,amount,actiontype) values ('"+ClickItemID.toLowerCase(Locale.ROOT)+"','"+Name+"',0,0,-1,0,'nothing');";
                    DBCT.DatabaseUPDATE(SQLSentenceInsert);


                }catch (NullPointerException e){
                    //
                }


            }else if(ClickNo < 45 && UserTags.contains("PublicStoragePullResultOpenImitation")){
                //pull process
                try{
                    String ClickItemID = event.getCurrentItem().getType().toString();
                    sender.sendMessage("You clicked:"+ClickItemID);
                    //
                    Material LastProcessMaterial = event.getCurrentItem().getType();
                    Inventory Pull = SCVPSC.PublicStoragePullLastProcess(LastProcessMaterial,ClickItemID,"PublicStorage Pull");
                    //open final inventory(in pull process)
                    sender.openInventory(Pull);

                    //add tag
                    sender.addScoreboardTag("PublicStoragePull-Pull");

                    //make temporary data on mysql db(table = temporary)
                    //first step is delete
                    String Name = event.getWhoClicked().getName();
                    String SQLSenteceDelete = "delete from temporary where playername='"+Name+"';";
                    DataBaseConnectionTest DBCT = new DataBaseConnectionTest();
                    DBCT.DatabaseUPDATE(SQLSenteceDelete);

                    //second step is insert
                    String SQLSentenceInsert = "insert into temporary(itemid,playername,pull,deposit,rawslot,amount,actiontype) values ('"+ClickItemID.toLowerCase(Locale.ROOT)+"','"+Name+"',0,0,-1,0,'nothing');";
                    DBCT.DatabaseUPDATE(SQLSentenceInsert);


                }catch(NullPointerException e){
                    //click no items slot
                }

            }
            //return to ItemID enter process(open sign)


            event.setCancelled(true);

        }

        if(UserTags.contains("PublicStoragePull-Pull")){
            //
            String Action = event.getAction().toString();
            int RawSlot = event.getRawSlot();

            if(Integer.valueOf(new DataBaseConnectionTest().Database("select rawslot from temporary where playername='"+event.getWhoClicked().getName()+"';","int","rawslot").toString()) == -1){
                //when first
                new DataBaseConnectionTest().DatabaseUPDATE("update temporary set rawslot=0 where playername='"+event.getWhoClicked().getName()+"';");
                return;
            }

            if(Action.toLowerCase(Locale.ROOT).contains("place")){
                //when player does item place on the inventory
                if(RawSlot < 54){
                    //if player place items on public-storages inventory
                    //if player does place_half or place_some, deny the movement.
                    if(Action.toLowerCase(Locale.ROOT).contains("half") || Action.toLowerCase(Locale.ROOT).contains("some")){
                        event.setCancelled(true);
                    }
                    //
                    String GetBeforeSlot = "select rawslot from temporary where playername='"+event.getWhoClicked().getName()+"';";
                    if(Integer.valueOf(new DataBaseConnectionTest().Database(GetBeforeSlot,"int","rawslot").toString()) > 53 && Action.toLowerCase(Locale.ROOT).contains("all")){
                        //if one step before rawslot equals over 53. =>when player moves items from players inventory to pst-inventory

                        int temporaryDeposit = Integer.valueOf(new DataBaseConnectionTest().Database("select deposit from temporary where playername='"+event.getWhoClicked().getName()+"';","int","deposit").toString());
                        int temporaryAmount = Integer.valueOf(new DataBaseConnectionTest().Database("select amount from temporary where playername='"+event.getWhoClicked().getName()+"';","int","amount").toString());

                        int amountDeposit = temporaryDeposit + temporaryAmount;
                        String UpdateTemporaryDeposit = "update temporary set deposit="+amountDeposit+" where playername='"+event.getWhoClicked().getName()+"';";

                        new DataBaseConnectionTest().DatabaseUPDATE(UpdateTemporaryDeposit);
                    }else if(Integer.valueOf(new DataBaseConnectionTest().Database(GetBeforeSlot,"int","rawslot").toString()) < 54){
                        //pst-inventory to pst-inventory
                        int temporaryAmount = Integer.valueOf(new DataBaseConnectionTest().Database("select amount from temporary where playername='"+event.getWhoClicked().getName()+"';","int","amount").toString());
                        int temporaryPull = Integer.valueOf(new DataBaseConnectionTest().Database("select pull from temporary where playername='"+event.getWhoClicked().getName()+"';","int","pull").toString());

                        int AfterTune = temporaryPull - temporaryAmount;
                        new DataBaseConnectionTest().DatabaseUPDATE("update temporary set pull ="+AfterTune+" where playername='"+event.getWhoClicked().getName()+"';");
                    }
                }
            }

            if(Action.toLowerCase(Locale.ROOT).contains("pickup")){
                //when player dose item pickup from the inventory
                if(RawSlot < 54){
                    if(Action.toLowerCase(Locale.ROOT).contains("half") || Action.toLowerCase(Locale.ROOT).contains("some")){
                        event.setCancelled(true);
                    }

                    if(Action.toLowerCase(Locale.ROOT).contains("all")){
                        int deposit = event.getCurrentItem().getAmount() + Integer.valueOf(new DataBaseConnectionTest().Database("select pull from temporary where playername='"+event.getWhoClicked().getName()+"';","int","pull").toString());
                        String updatePull = "update temporary set pull="+deposit+";";
                        new DataBaseConnectionTest().DatabaseUPDATE(updatePull);
                    }
                }
            }

            sender.sendMessage("Action;"+event.getAction()+"/Slot;"+event.getRawSlot());



            String SQL = "update temporary set actiontype='"+Action+"',rawslot="+RawSlot+" where playername='"+event.getWhoClicked().getName()+"';";
            new DataBaseConnectionTest().DatabaseUPDATE(SQL);

            try{
                sender.sendMessage("/Amount;"+event.getCurrentItem().getAmount());
                String SQLamount = "update temporary set amount="+event.getCurrentItem().getAmount()+" where playername='"+event.getWhoClicked().getName()+"';";
                new DataBaseConnectionTest().DatabaseUPDATE(SQLamount);

            }catch (NullPointerException e){
                String SQLamount = "update temporary set amount=-1 where playername='"+event.getWhoClicked().getName()+"';";
                new DataBaseConnectionTest().DatabaseUPDATE(SQLamount);
            }

            try{

                DataBaseConnectionTest DBCT = new DataBaseConnectionTest();
                String SQLSentence = "select itemid from temporary where playername='"+event.getWhoClicked().getName()+"';";
                String ClickTemporary = DBCT.Database(SQLSentence,"string","itemid").toString();
                if(!(event.getCurrentItem().getType().toString().toLowerCase(Locale.ROOT).equalsIgnoreCase(ClickTemporary))){
                    //if player try to have items that without selected,denied.
                    event.setCancelled(true);
                }
            }catch (NullPointerException e){
                //
            }

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
