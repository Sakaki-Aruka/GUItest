package com.github.ytshiyugh.guitest;

import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetItemAmountOnStorage {
    public int GetItemAmountOnStorage(String ItemID){
        DataBaseConnectionTest DBCtest = new DataBaseConnectionTest();
        /*
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

         */
        String SQLSentence = "select amount from publicstorage where itemid='"+ItemID+"';";
        String type = "int";
        String requestsData = "amount";
        int Amount;
        try{
            Amount = Integer.valueOf(DBCtest.Database(SQLSentence,type,requestsData).toString());
            return Amount;
        }catch (NumberFormatException NFE){
            System.out.println("NFE");
            return -1;
        }
    }

    public ArrayList<String> GetImitation(String ItemID){
        DataBaseConnectionTest DBCTest = new DataBaseConnectionTest();
        /*
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

        String SQLSentence = "select * from publicstorage where itemid like '%"+ItemID+"%';";
        System.out.println("fromDataBase String:"+DBCTest.Database(SQLSentence,"arraylist","itemid"));
         */
        //ArrayList<String> ReturnItems = new ArrayList<>();
        String SQLSentence = "select * from publicstorage where itemid like '%"+ItemID+"%';";
        ArrayList<String> ReturnItems = (ArrayList<String>)DBCTest.Database(SQLSentence,"arraylist","itemid");



        ReturnItems.add("-1");
        return ReturnItems;
    }
}
