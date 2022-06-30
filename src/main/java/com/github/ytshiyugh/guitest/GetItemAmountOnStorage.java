package com.github.ytshiyugh.guitest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetItemAmountOnStorage {
    public int GetItemAmountOnStorage(String ItemID){
        File file = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID);
        File[] files = file.listFiles();
        for(int i=0;i < files.length;i++){
            String AmountOnStorageStr = files[i].toString().replace(".txt","");

            try{
                Pattern pattern = Pattern.compile("(\\d{1,20})");
                Matcher match = pattern.matcher(AmountOnStorageStr);
                while (match.find()){
                    int AmountOnStorageInt = Integer.valueOf(match.group(1));
                    return AmountOnStorageInt;
                }
            }catch (NumberFormatException NFE){
                return -1;
            }
        }
        return -1;
    }

    public ArrayList<String> GetImitation(String ItemID){
        File file = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems");
        File[] files = file.listFiles();
        ArrayList<String> ReturnItems = new ArrayList<>();
        for(int i = 0;i < files.length;i++){
            //
            if(files[i].toString().indexOf(ItemID) != -1){
                //find ItemID in Storage Item
                int ItemStart = files[i].toString().indexOf("/")+1;
                int ItemEnd = files[i].toString().length();
                ReturnItems.add(files[i].toString().substring(ItemStart,ItemEnd));
            }
        }
        ReturnItems.add("-1");
        return ReturnItems;
    }
}
