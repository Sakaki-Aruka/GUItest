package com.github.ytshiyugh.guitest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepositMainClass implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        /*
        /pstdは(p)ublic (st)orage (d)epositの略で、ストレージへのアップロードを担う
        コマンドの文法は以下の通り

        /pstd (player/gui) (amount) (ItemID) (PST-UUID)

        player/gui
        →プレイヤーが送信したものかGUIから送信されたものかを判断する

        amount
        →リクエストするアイテムの数量

        ItemID
        →リクエストするアイテムID(大文字）

        PST-UUID
        →整合性チェックのためのUUID

        Depositの引数は合計4個とする
         */
        //write here
        DepositStrings DString = new DepositStrings();
        try {
            String InterfaceType = args[0];
            if(!(InterfaceType.equalsIgnoreCase("gui") || InterfaceType.equalsIgnoreCase("player"))){
                DString.Error((Player) sender,"Illegal Args(InterfaceType Other)");
                return false;
            }
        }catch(ArrayIndexOutOfBoundsException AIOoBE){
            DString.Error((Player) sender,"Illegal Args(InterfaceType Illegal)");
            return false;
        }
        try{
            String Amount = args[1];
        }catch (ArrayIndexOutOfBoundsException AIOoBE){
            DString.Error((Player) sender,"Illegal Args(Amount Illegal)");
            return false;
        }
        try{
            String ItemID = args[2];
            Pattern ItemIDPattern = Pattern.compile("[^a-z_A-Z]{1,100}");
            Matcher ItemIDMatcher = ItemIDPattern.matcher(ItemID);
            boolean ItemIDMatcherBool = ItemIDMatcher.find();
            if(ItemIDMatcherBool==true){
                DString.Error((Player) sender,"Illegal Args(Illegal Symbol)");
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException AIOoBE){
            DString.Error((Player) sender,"Illegal Args(ItemID Illegal)");
            return false;
        }
        //String PSTUUID = args[3];
        //String Expansion = args[4];
        String InterfaceType = args[0];
        String Amount = args[1];
        String ItemID = args[2];

        IllegalItemJudge IIJ = new IllegalItemJudge();
        ItemExistCheck IEC = new ItemExistCheck();
        ExistMkdirDeposit EMD = new ExistMkdirDeposit();

        Player player = (Player) sender;
        Path JarCurrent = Paths.get("").toAbsolutePath();
        boolean ArgThis;
        if(ItemID.equalsIgnoreCase("this")){
            ArgThis = true;
        }else{
            ArgThis = false;
        }

        //Check UUID
        /*
        Set<String> PlayerUUID = player.getScoreboardTags();
        if(!(PlayerUUID.contains(PSTUUID))){
            DString.Error(player,"Illegal Args(Requests UUID");
            return false;
        }

         */

        if(InterfaceType.equalsIgnoreCase("player")) {
            //from player
            if (!(args.length == 3)) {
                //if it is not 4 that args length.
                DString.Error(player, "Illegal Args(Request InterfaceType)");
            }
            int AmountInt = 0;
            boolean AmountAll;

            //Amount Check
            try {
                AmountInt = Integer.valueOf(Amount);
                if (!(0 < AmountInt && AmountInt < 65)) {
                    DString.Error(player, "Illegal Args(Request Amount Range)");
                    return false;
                }
            } catch (NumberFormatException NFE) {
                if (!(Amount.equalsIgnoreCase("all"))) {
                    DString.Error(player, "Illegal Args(Request Amount)");
                    return false;
                }else if(Amount.equalsIgnoreCase("all")){
                    //if amount = "all"
                    if(ArgThis==true){
                        // all && this
                        String ThisItem = player.getInventory().getItemInMainHand().getType().toString();
                        System.out.println(ThisItem);
                        for(int ThisAllLoop = 0;ThisAllLoop<36;ThisAllLoop++){
                            try{
                                //EMDのDepositにリクエスト投げまくるところ
                                if(player.getInventory().getItem(ThisAllLoop).getType().toString().equalsIgnoreCase(ThisItem)){
                                    int RequestAmount = player.getInventory().getItem(ThisAllLoop).getAmount();
                                    //インベントリ0～35でthisと一致したアイテムはリクエスト
                                    int EMDDepositReturn = EMD.ItemDeposit(ThisItem,RequestAmount,RequestAmount,"player");
                                    if(EMDDepositReturn==-1){
                                        DString.Error(player,"Illegal Args(All-Deposit:-1)");
                                    }else if(EMDDepositReturn == -2){
                                        DString.Error(player,"Illegal Args(Illegal Item)");
                                    }else{
                                        player.getInventory().getItem(ThisAllLoop).setAmount(0);
                                        DString.Successful(player,"Deposit / "+ThisItem+" / "+RequestAmount);
                                    }
                                }
                            }catch (NullPointerException NullPo){
                                continue;
                            }
                        }
                        return false;
                    }else{
                        // all && (not this)
                        for(int ThisNotThisLoop = 0;ThisNotThisLoop<36;ThisNotThisLoop++){
                            try{
                                //EMDのDepositにリクエストを投げまくるところ
                                if(player.getInventory().getItem(ThisNotThisLoop).getType().toString().equalsIgnoreCase(ItemID)){
                                    //インベントリ0～35でthisと一致したアイテムはリクエスト
                                    int RequestAmount = player.getInventory().getItem(ThisNotThisLoop).getAmount();
                                    int EMDDepositResult = EMD.ItemDeposit(ItemID,RequestAmount,RequestAmount,"player");
                                    if(EMDDepositResult==-1){
                                        DString.Error(player,"Illegal Args(All-Deposit:-1)");
                                    }else if(EMDDepositResult==-2){
                                        DString.Error(player,"Illegal Args(Illegal Item)");
                                    }else if(EMDDepositResult==0){
                                        player.getInventory().getItem(ThisNotThisLoop).setAmount(0);
                                        String SendString = "Deposit / "+ItemID+" / "+RequestAmount;
                                        DString.Successful(player,SendString);
                                    }
                                }
                            }catch (NullPointerException NullPo){
                                continue;
                            }
                        }
                        return false;
                    }
                }

            }

            //here
            int InventorySlotFind;
            if(ArgThis==true){
                try{
                    //get MainHand's slot
                    int ItemSlot = player.getInventory().getHeldItemSlot();

                    if(!(player.getInventory().getItemInMainHand().getType().toString()==null)){
                        boolean EMDItemAmountCheck = EMD.ItemAmountCheck(player,ItemSlot,AmountInt);
                        if(EMDItemAmountCheck==false){
                            DString.Error(player,"Illegal Args(Illegal Request Amount(Player))");
                            return false;
                        }

                        //
                        int EMDItemDeposit = EMD.ItemDeposit(player.getInventory().getItemInMainHand().getType().toString(),AmountInt,player.getInventory().getItem(ItemSlot).getAmount(),"player");
                        if(EMDItemDeposit==-1){
                            DString.Error(player,"Illegal (Deposit Error)");
                            return false;
                        }else if(EMDItemDeposit==-2){
                            DString.Error(player,"Illegal Args(Illegal Item)");
                            return false;
                        }
                        player.getInventory().getItem(ItemSlot).setAmount(EMDItemDeposit);

                        //file rename


                        DString.Successful(player,"Deposit / "+player.getInventory().getItemInMainHand().getType().toString().toLowerCase(Locale.ROOT)+" / "+Amount);

                    }else{
                        DString.Error(player,"Illegal Args(404(This))");
                        return false;
                    }

                }catch (NullPointerException NullPo){
                    DString.Error(player,"Illegal Args(\"This\" = Null)");
                    return false;
                }
            }else{
                for(int i = 0;i<36;i++){
                    try{
                        String InventorySlot = player.getInventory().getItem(i).getType().toString();
                        if(ItemID.equalsIgnoreCase(InventorySlot)){
                            // 要求アイテムとインベントリアイテムが一致
                            //InventorySlotFind = i;
                            boolean EMDItemAmountCheck = EMD.ItemAmountCheck(player,i,AmountInt);
                            if(EMDItemAmountCheck==false){
                                DString.Error(player,"Illegal Args(Illegal Request Amount(Player))");
                                return false;
                            }

                            int EMDItemDeposit = EMD.ItemDeposit(ItemID,AmountInt,player.getInventory().getItem(i).getAmount(),"player");
                            if(EMDItemDeposit==-1){
                                DString.Error(player,"Illegal (Deposit Error)");
                                return false;
                            }
                            player.getInventory().getItem(i).setAmount(EMDItemDeposit);
                            DString.Successful(player,"Deposit / "+ItemID+" / "+Amount);
                            return false;

                        }else{
                            if(i==35){
                                DString.Error(player,"Illegal Args(404(Not This))");
                                return false;
                            }
                        }
                    }catch (NullPointerException NP){
                        continue;
                    }

                }
            }

            /*
            boolean EMDItemAmountCheck = EMD.ItemAmountCheck(player,EMDItemNameCheck,AmountInt);
            if(EMDItemAmountCheck==false){
                DString.Error(player,"Illegal Args(Illegal Request Amount(Player))");
                return false;
            }

            int EMDItemDeposit = EMD.ItemDeposit(ItemID,AmountInt,player.getInventory().getItem(EMDItemNameCheck).getAmount(),"player");
            if(EMDItemDeposit==-1){
                DString.Error(player,"Illegal (Deposit Error)");
                return false;
            }
            player.getInventory().getItem(EMDItemNameCheck).setAmount(EMDItemDeposit);
            DString.Successful(player,"Deposit / "+ItemID+" / "+Amount);

             */

        }else if(InterfaceType.equalsIgnoreCase("gui")){
            //from GUI
            try{
                int AmountInt = Integer.valueOf(Amount);
                int EMDItemDeposit = EMD.ItemDeposit(ItemID,AmountInt,AmountInt,"gui");
                String DebugString = "Deposit / "+ItemID+" / "+AmountInt;
                DString.Successful(player,DebugString);
                return false;
            }catch (NumberFormatException NFE){
                DString.Error(player,"Illegal Args(Illegal Request Amount(GUI))");
                return false;
            }

        }else{
            //from other
            return false;
        }

        return false;
    }
}
