package com.github.ytshiyugh.guitest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ItemExistCheck {
    public boolean ItemExistCheck(String ItemID){
        //
        ItemID = ItemID.toLowerCase(Locale.ROOT);
        File ItemDirectory = new File(Paths.get("").toAbsolutePath()+"/plugins/TestPlugin/StorageItems/"+ItemID);

        if(ItemDirectory.exists()){
            //file exist
            return true;
        }else{
            return false;
        }
    }
}
