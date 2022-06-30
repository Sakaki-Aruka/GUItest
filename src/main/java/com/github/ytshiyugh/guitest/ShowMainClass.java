package com.github.ytshiyugh.guitest;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowMainClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,String[] args){
        if(!(sender instanceof Player)){
            //command send from console.
            return false;
        }
        DepositStrings DString = new DepositStrings();

        /*
        Showの書き方

        /psts (player/gui) (ItemID) (PST-UUID(only GUI))

        player/gui
        →プレイヤーが送信したものかGUIから送信されたものかを判断する

        ItemID
        →リクエストされたアイテムID

        PST-UUID
        →整合性チェックのためのUUID（テスト段階では未実装）
         */

        try{
            String InterfaceType = args[0];
            if(!(InterfaceType.equalsIgnoreCase("gui") || InterfaceType.equalsIgnoreCase("player"))){
                DString.Error((Player) sender,"Illegal Args(InterfaceType Other)");
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException AIOoBE){
            DString.Error((Player)sender,"Illegal Args(InterfaceType Illegal)");
            return false;
        }

        try{
            String ItemID = args[1];
            Pattern ItemIDPattern = Pattern.compile("[^a-z_A-Z]{1,100}");
            Matcher ItemIDMatcher = ItemIDPattern.matcher(ItemID);
            boolean ItemIDMatcherBool = ItemIDMatcher.find();
            if(ItemIDMatcherBool==true){
                DString.Error((Player) sender,"Illegal Args(Illegal Symbol)");
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException AIOoBE){
            DString.Error((Player) sender,"Illegal Args(Args Shortage)");
            return false;
        }

        //pst-uuidを使用するようにしたら3に変更しておく
        if(args.length != 2){
            DString.Error((Player)sender,"Illegal Args(Number of Args");
            return false;
        }

        String InterfaceType = args[0];
        String ItemID = args[1].toLowerCase(Locale.ROOT);
        if(ItemID.length() < 3){
            DString.Error((Player) sender,"Illegal Args(ItemID length)");
            return false;
        }
        //String PSTUUID = args[2];

        ItemExistCheck IEC = new ItemExistCheck();
        GetItemAmountOnStorage GIAoS = new GetItemAmountOnStorage();

        if(InterfaceType.equalsIgnoreCase("player")){
            //show process write here.(Player)

            if(IEC.ItemExistCheck(ItemID)){
                //find player input item
                int ReturnCode = GIAoS.GetItemAmountOnStorage(ItemID);
                if(ReturnCode != -1){

                    //return code is not an error code.
                    DString.Successful((Player)sender,"Show / "+ItemID+" / "+ReturnCode);
                    return true;
                }
            }else{
                //not found player input item
                DString.Error((Player)sender,"Show / NoItem Match \""+ItemID+"\" that you send.");
                ArrayList<String> ReturnCode = GIAoS.GetImitation(ItemID);
                for(int i=0;i < ReturnCode.size();i++){
                    ///if arrays first object is "-1" = no item found on storage
                    if(i==0 & ReturnCode.get(0).equalsIgnoreCase("-1")){
                        DString.Error((Player)sender,"Illegal Args(No Items Match)");
                        return false;
                    }else if(i != 0 && ReturnCode.get(i).equalsIgnoreCase("-1")){
                        //last loop
                        return false;
                    }else if(!(ReturnCode.get(i).equalsIgnoreCase("-1"))){
                        //items found on storage
                        System.out.println("ReturnCode;"+ReturnCode.get(i));
                        int Divide;
                        if(ReturnCode.get(i).lastIndexOf("/") == -1){
                            //on windows
                            System.out.println("DivideBack;"+ReturnCode.get(i).lastIndexOf("\\"));
                            Divide = ReturnCode.get(i).lastIndexOf("\\");
                        }else{
                            //on linux
                            System.out.println("Divide;"+ReturnCode.get(i).lastIndexOf("/"));
                            Divide = ReturnCode.get(i).lastIndexOf("/");
                        }

                        DString.Successful((Player)sender,"Show / "+ReturnCode.get(i).substring(Divide+1)+" / "+GIAoS.GetItemAmountOnStorage(ReturnCode.get(i).substring(Divide+1)));
                    }

                }
                return false;
            }
        }else if(InterfaceType.equalsIgnoreCase("gui")){
            //show process write here.(GUI)
            //must write graphical process

            if(IEC.ItemExistCheck(ItemID)){
                //find player input item
                int ReturnCode = GIAoS.GetItemAmountOnStorage(ItemID);
                if(ReturnCode != -1){

                    //return code is not an error code.
                    DString.Successful((Player)sender,"Show / "+ItemID+" / "+ReturnCode);
                    return true;
                }
            }else{
                //not found player input item
                DString.Error((Player)sender,"Show / NoItem Match \""+ItemID+"\" that you send.");
                ArrayList<String> ReturnCode = GIAoS.GetImitation(ItemID);
                for(int i=0;i < ReturnCode.size();i++){
                    ///if arrays first object is "-1" = no item found on storage
                    if(i==0 & ReturnCode.get(0).equalsIgnoreCase("-1")){
                        DString.Error((Player)sender,"Illegal Args(No Items Match)");
                        return false;
                    }else if(i != 0 && ReturnCode.get(i).equalsIgnoreCase("-1")){
                        //last loop
                        return false;
                    }else if(!(ReturnCode.get(i).equalsIgnoreCase("-1"))){
                        //items found on storage
                        System.out.println("ReturnCode;"+ReturnCode.get(i));
                        int Divide;
                        if(ReturnCode.get(i).lastIndexOf("/") == -1){
                            //on windows
                            System.out.println("DivideBack;"+ReturnCode.get(i).lastIndexOf("\\"));
                            Divide = ReturnCode.get(i).lastIndexOf("\\");
                        }else{
                            //on linux
                            System.out.println("Divide;"+ReturnCode.get(i).lastIndexOf("/"));
                            Divide = ReturnCode.get(i).lastIndexOf("/");
                        }

                        DString.Successful((Player)sender,"Show / "+ReturnCode.get(i).substring(Divide+1)+" / "+GIAoS.GetItemAmountOnStorage(ReturnCode.get(i).substring(Divide+1)));
                    }

                }
                return false;
            }

        }else{
            DString.Error((Player)sender,"Illegal Args(Illegal InterfaceType)");
            return false;
        }


        return false;
    }
}
//test
