package com.github.ytshiyugh.guitest;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ExistMkdirDeposit {




    public boolean ItemAmountCheck(Player sender,int FindSlot,int RequestAmount){
        int ItemAmount = sender.getInventory().getItem(FindSlot).getAmount();
        if(RequestAmount <= ItemAmount){
            return true;
        }else{
            return false;
        }
    }

    public Integer ItemDeposit(String ItemIDRaw,int RequestAmount,int PlayerHave,String InterfaceType){
        //
        String ItemID = ItemIDRaw.toLowerCase(Locale.ROOT);
        IllegalItemJudge IIJ = new IllegalItemJudge();
        DataBaseConnectionTest DBCTest = new DataBaseConnectionTest();

        if(IIJ.Judge(ItemID)==false){
            return -2;
        }

        //OnStorage items amount get
        String SQLSentence = "select * from publicstorage where itemid = '"+ItemID+"';";
        int onStorage = Integer.valueOf(DBCTest.Database(SQLSentence,"int","amount").toString());
        if(onStorage==-1){
            //should mkdir
            String SQLSentenceMake = "insert into publicstorage (amount,itemid) values ("+PlayerHave+",'"+ItemID+"');";
            DBCTest.DatabaseUPDATE(SQLSentenceMake);
            return RequestAmount;
        }else{
            int StoragePlusPlayer = onStorage + RequestAmount;
            //update on PublicStorage amount
            SQLSentence = "update publicstorage set amount = '"+StoragePlusPlayer+"' where itemid = '"+ItemID+"';";
            DBCTest.DatabaseUPDATE(SQLSentence);
            int returnCode = PlayerHave - RequestAmount;
            if(InterfaceType.equalsIgnoreCase("player")){
                return returnCode;
            }else{
                //gui
                return -1;
            }

        }


        /*

        File ItemNameFile = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID);
        try{
            File[] ItemNameFileArray = ItemNameFile.listFiles();
            for(int i=0;i < ItemNameFileArray.length;i++){
                //get item amount on the public storage
                String FileName = ItemNameFileArray[i].getName().replace(".txt","");
                File fOld = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID+"/"+ItemNameFileArray[i].getName());


                int ItemAmountOnStorage = Integer.valueOf(FileName);
                // Storage's amount plus players item amount
                int ItemAmountOnStoragePlusPlayers = RequestAmount + ItemAmountOnStorage;

                File fNew = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID+"/"+ItemAmountOnStoragePlusPlayers+".txt");
                fOld.renameTo(fNew);

                if(InterfaceType.equalsIgnoreCase("player")){
                    //if send from player
                    int PlayersMinusDeposit = PlayerHave - RequestAmount;
                    return PlayersMinusDeposit;
                }else{
                    //if send from GUI
                    return -1;
                }
            }
        }catch (NullPointerException NullPo){
            // no items file there
            File MKDIR = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID);
            MKDIR.mkdir();
            Path NewItemAmountFile = Paths.get(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID+"/"+RequestAmount+".txt");
            System.out.println(Paths.get(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID+"/"+RequestAmount+".txt"));
            try{
                Files.createFile(NewItemAmountFile);
            }catch (java.io.IOException IOE){
                System.out.println("Error occurred. To create new items file.");
            }
            return RequestAmount;
        }
        return -1;

         */
    }


}
