package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class PublicStorageCommandSendAsPlayer {
    public void PublicStorageFromGUIMenu(Player sender, int ClickNo, Set UserTags,Inventory GetInventory){
        if(UserTags.contains("PublicStorageMenuOpen")){
            if(ClickNo==0){
                //show
                //add "PublicStorageShowOpen" tag to sender.
                sender.addScoreboardTag("PublicStorageShowOpen");
                StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();
                SCVPSC.PublicStorageShow(sender);

            }else if(ClickNo==2){
                //Deposit

                Inventory PublicStorageDeposit = Bukkit.createInventory(null,54,"PublicStorage Deposit");
                //set Back to GUIMenu top
                PublicStorageDeposit.setItem(53,new StickViewBackHomeMenu().BackHomeMenu());
                StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();
                // To click to close GUIMenu.(BEACON)
                PublicStorageDeposit.setItem(49,SCVPSC.CloseDeposit());
                sender.openInventory(PublicStorageDeposit);
                sender.addScoreboardTag("PublicStorageDepositOpen");

            }else if(ClickNo==4){
                //Pull
                //add "PublicStoragePullOpen" tag to sender.
                sender.addScoreboardTag("PublicStoragePullOpen");
                StickClickViewPublicStorageCommands SCVPSC = new StickClickViewPublicStorageCommands();
                SCVPSC.PublicStorageShow(sender);
            }
        }

        if(UserTags.contains("PublicStorageDepositOpen")){
            //if click Deposit button (beacon)
            if(ClickNo==49){
                for(int i=0;i<54;i++){

                    try{
                        // exclude back to GUIMenu-Top button and Close button
                        if(!(i==49 || i==53)){
                            // pstd2用のUUIDを発行
                            UUID PSTD2UUID = UUID.randomUUID();
                            //sender.sendMessage("Material:"+GetInventory.getItem(i).getType());
                            //sender.sendMessage("Amount:"+GetInventory.getItem(i).getAmount());
                            //sender.sendMessage("Slot:"+i);

                            int RequestAmout = GetInventory.getItem(i).getAmount();

                            //newpstd gui リクエスト量 アイテムID
                            sender.chat("/newpstd gui"+" "+RequestAmout+" "+GetInventory.getItem(i).getType());
                        }
                    }catch(NullPointerException NullPo){
                        //no items there
                    }
                }


                //if click the beacon to close GUIMenu
                sender.closeInventory();
                //ここでタグを消したことによってInventoryCloseで引っかからない
                sender.removeScoreboardTag("PublicStorageDepositOpen");
                //預けられたものを検出

            }
            }
        }

    }

