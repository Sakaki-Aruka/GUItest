package com.github.ytshiyugh.guitest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ItemExistCheck {
    public boolean ItemExistCheck(String ItemID){
        //
        DataBaseConnectionTest DBCTest = new DataBaseConnectionTest();
        String SQLSentence = "select amount from publicstorage where itemid ='"+ItemID+"';";
        int returnCode;
        try{
            returnCode = Integer.valueOf(DBCTest.Database(SQLSentence,"int","amount").toString());
            if(returnCode==-1){
                System.out.println("iec false");
                return false;
            }
            System.out.println("iec true");
            return true;
        }catch(NullPointerException e){
            System.out.println("iec false");
            return false;
        }

        /*
        ItemID = ItemID.toLowerCase(Locale.ROOT);
        File ItemDirectory = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID);

        if(ItemDirectory.exists()){
            //file exist
            return true;
        }else{
            return false;
        }

         */
    }
}
