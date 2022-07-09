package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class StickClickViewPublicStorageCommands {


    private ItemStack AllDeposit() {
        ItemStack AllDeposit = new ItemStack(Material.SHULKER_BOX);
        ItemMeta AllDepositMeta = AllDeposit().getItemMeta();
        //§2 equals dark_green.
        AllDepositMeta.setDisplayName("§2Deposit All");
        AllDepositMeta.setLore(Arrays.asList("§fIf you select this option,", " you can deposit all items that allowed by Public Storage."));
        AllDeposit.setItemMeta(AllDepositMeta);
        return AllDeposit;
    }


    public void PublicStorageModeDisplay(Player sender) {
        Inventory PublicStorageMenu = Bukkit.createInventory(null, 9, "PublicStorage Mode Menu");
        PublicStorageMenu.setItem(0, new PublicStorageModeMenu().Show());
        PublicStorageMenu.setItem(2, new PublicStorageModeMenu().Deposit());
        PublicStorageMenu.setItem(4, new PublicStorageModeMenu().Pull());
        PublicStorageMenu.setItem(8, new StickViewBackHomeMenu().BackHomeMenu());
        ;
        sender.openInventory(PublicStorageMenu);
        sender.addScoreboardTag("PublicStorageMenuOpen");
    }

    public ItemStack CloseDeposit() {
        ItemStack PublicStorageDepositClose = new ItemStack(Material.BEACON);
        ItemMeta PublicStorageDepositCloseMeta = PublicStorageDepositClose.getItemMeta();
        PublicStorageDepositCloseMeta.setDisplayName("§eDeposit");
        PublicStorageDepositCloseMeta.setLore(Arrays.asList("§fDeposit that selected items"));
        PublicStorageDepositClose.setItemMeta(PublicStorageDepositCloseMeta);
        return PublicStorageDepositClose;
    }



    public void PublicStorageShowShow(Player sender,ItemStack DisplayStack,String InventoryName,String ScoreboardString){
        Inventory PublicStorageShowShow = Bukkit.createInventory(null,27,InventoryName);
        PublicStorageShowShow.setItem(13,DisplayStack);
        PublicStorageShowShow.setItem(18,ReturnButton("Return to ItemID Enter"));
        PublicStorageShowShow.setItem(26,new StickViewBackHomeMenu().BackHomeMenu());
        sender.openInventory(PublicStorageShowShow);
        sender.addScoreboardTag(ScoreboardString);
        //
    }

    public Inventory PublicStorageShowImitation(ArrayList<String> Array,Player player,String IventoryTile){
        DepositStrings DString = new DepositStrings();
        GetItemAmountOnStorage GIAoS = new GetItemAmountOnStorage();
        Inventory ReturnInventory = Bukkit.createInventory(null,54,IventoryTile);
        //インベントリの容量の関係で45個まで
        for(int i=0;i<45;i++){
            if(i==0 && Array.get(i).equalsIgnoreCase("-1")){
                //if array's first object is "-1" → no item found on storage
                ItemStack ErrorStack = this.PublicStorageShowPreparation("A",0);
                //error bedrock's ItemStack
                ReturnInventory.setItem(22,ErrorStack);
                break;
            }else if(i != 0 && Array.get(i).equalsIgnoreCase("-1")){
                //last loop
                break;
            }else if(!(Array.get(i).equalsIgnoreCase("-1"))){
                //items found on storage
                System.out.println("ReturnCode;"+Array.get(i));
                int Divide;
                if(Array.get(i).lastIndexOf("/") == -1){
                    //on windows
                    System.out.println("DivideBack;"+Array.get(i).lastIndexOf("\\"));
                    Divide = Array.get(i).lastIndexOf("\\");
                }else{
                    //on linux
                    System.out.println("Divide;"+ Array.get(i).lastIndexOf("/"));
                    Divide = Array.get(i).lastIndexOf("/");
                }
                //


                int SetItemAmount = 1;
                String ItemAmountDetails="";
                if(GIAoS.GetItemAmountOnStorage(Array.get(i).substring(Divide+1)) > 64){
                    SetItemAmount = 64;
                    ItemAmountDetails = "§fOn Storage:"+GIAoS.GetItemAmountOnStorage(Array.get(i).substring(Divide+1));
                }else{
                    SetItemAmount = GIAoS.GetItemAmountOnStorage(Array.get(i).substring(Divide+1));
                    ItemAmountDetails = "§fOn Storage:"+GIAoS.GetItemAmountOnStorage(Array.get(i).substring(Divide+1));
                }
                Material SetItemMaterial = Material.valueOf(Array.get(i).substring(Divide+1).toUpperCase(Locale.ROOT));
                ItemStack setShowItem = new ItemStack(SetItemMaterial,SetItemAmount);
                ItemMeta setShowItemMeta = setShowItem.getItemMeta();
                setShowItemMeta.setDisplayName(Array.get(i).substring(Divide+1));
                setShowItemMeta.setLore(Arrays.asList(ItemAmountDetails));
                setShowItem.setItemMeta(setShowItemMeta);
                ReturnInventory.setItem(i,setShowItem);
            }
        }
        ReturnInventory.setItem(53,new StickViewBackHomeMenu().BackHomeMenu());
        ReturnInventory.setItem(45,this.ReturnButton("Return to ItemID Enter"));
        return ReturnInventory;
    }

    public Inventory PublicStoragePullLastProcess(Material IDMaterial,String IDString,String InvTitle){

        DataBaseConnectionTest DBCT = new DataBaseConnectionTest();
        String SQLSentence = "select amount from publicstorage where itemid='"+IDString.toLowerCase(Locale.ROOT)+"';";

        //make inventory to use in pull final process that player can pull items.
        Inventory ReturnInventory = Bukkit.createInventory(null,54,InvTitle);

        //get storage amount from mysql database.
        int StorageAmount = Integer.valueOf(DBCT.Database(SQLSentence,"int","amount").toString());

        if(StorageAmount < 2){
            //if storage amount lest than 2
            ItemStack NullBedRock = this.PublicStorageShowPreparation("A",0);
            ReturnInventory.setItem(22,NullBedRock);

            //return bedrock with a lore that written "null".
            return ReturnInventory;

        }else if(StorageAmount > 64){
            //when the amount of the target item more than 1 stack on the storage.
            int ii = 0;
            ItemStack LoopStack;
            for (int i=StorageAmount;i> 64;i -=64){
                //make item-stack to set return inventory
                LoopStack = new ItemStack(IDMaterial,64);
                ReturnInventory.setItem(ii,LoopStack);
                ii += 1;
                if(ii == 44){
                    break;
                }
            }
            //finally
            int remainingAmount = StorageAmount - 64*ii;
            if(remainingAmount > 64){
                //
                LoopStack = new ItemStack(IDMaterial,64);
                ReturnInventory.setItem(ii,LoopStack);
            }else{
                LoopStack = new ItemStack(IDMaterial,remainingAmount);
                ReturnInventory.setItem(ii,LoopStack);
            }

        }else if(StorageAmount > 2 && StorageAmount <= 64){
            //
            ItemStack ItemStack = new ItemStack(IDMaterial,StorageAmount);
            ReturnInventory.setItem(22,ItemStack);
        }

        return ReturnInventory;
    }




    public ItemStack PublicStorageShowPreparation(String ItemID,int Amount){
        Material material;
        ItemStack Show;
        ItemMeta ShowMeta;
        try{
            material = Material.valueOf(ItemID.toUpperCase(Locale.ROOT));
            Show = new ItemStack(material);
            ShowMeta = Show.getItemMeta();
            ShowMeta.setDisplayName(material.name().toLowerCase(Locale.ROOT));
            ShowMeta.setLore(Arrays.asList("§fOn Storage:"+Amount));
            Show.setItemMeta(ShowMeta);
        }catch (IllegalArgumentException IAE){

            Show = new ItemStack(Material.BEDROCK);
            ShowMeta = Show.getItemMeta();
            ShowMeta.setDisplayName("§cNull");
            ShowMeta.setLore(Arrays.asList("§fNull"));
            Show.setItemMeta(ShowMeta);
        }

        return Show;
    }

    public ItemStack ReturnButton(String Lore){
        ItemStack Button = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta ButtonMeta = Button.getItemMeta();
        ButtonMeta.setDisplayName("§bReturn");
        ButtonMeta.setLore(Arrays.asList("§f"+Lore));
        Button.setItemMeta(ButtonMeta);
        return Button;
    }

    public void PublicStorageShow(Player sender) {
        //
        sender.closeInventory();

        /*
        PublicStorageSignInterface PSSI = new PublicStorageSignInterface();
        PSSI.sign.setLine(0,"this is a test sign interface.");
        sender.openSign(PSSI.sign);

         */
        //
        PublicStorageSignPlaceSearch PSSPS = new PublicStorageSignPlaceSearch();
        int ReturnCodePSSPSX = PSSPS.SignPlaceX();
        if(ReturnCodePSSPSX==-1){
            int ReturnCodePSSPSZ = PSSPS.SignPlaceZ();
            if(ReturnCodePSSPSZ == -1){
                //error code
                return;
            }else{
                //normal-return code(Z)
                PublicStorageShowSignPlace(sender,0.0,(double) ReturnCodePSSPSZ);
            }
            // If return code means error.
        }else{
            //normal return code(X)
            PublicStorageShowSignPlace(sender,(double) ReturnCodePSSPSX,0.0);
        }
    }
    private void PublicStorageShowSignPlace(Player sender,double x,double z){

        //double x = 0.0;
        double y = 300.0;
        //double z = 0.0;
        Location loc = new Location(Bukkit.getWorld("world"),x,y,z);
        Block block = loc.getBlock();
        block.setType(Material.OAK_SIGN);
        //BlockState singState = block.getState();
        //Sign sign = (Sign) singState;
        Sign sign1 = (Sign) block.getState();
        sender.openSign(sign1);



    }



}


    class PublicStorageModeMenu {
        public ItemStack Show() {
            ItemStack PublicStorageShow = new ItemStack(Material.SPYGLASS);
            ItemMeta PublicStorageShowMeta = PublicStorageShow.getItemMeta();
            // §e is a color code of yellow.
            PublicStorageShowMeta.setDisplayName("§eShow");
            // §f is a color code of white.
            PublicStorageShowMeta.setLore(Arrays.asList("§fShow items amount of PublicStorage "));
            PublicStorageShow.setItemMeta(PublicStorageShowMeta);
            return PublicStorageShow;
        }

        public ItemStack Deposit() {
            ItemStack PublicStorageDeposit = new ItemStack(Material.DROPPER);
            ItemMeta PublicStorageDepositMeta = PublicStorageDeposit.getItemMeta();
            PublicStorageDepositMeta.setDisplayName("§eDeposit");
            PublicStorageDepositMeta.setLore(Arrays.asList("§fDeposit items to PublicStorage."));
            PublicStorageDeposit.setItemMeta(PublicStorageDepositMeta);
            return PublicStorageDeposit;
        }

        public ItemStack Pull() {
            ItemStack PublicStoragePull = new ItemStack(Material.HOPPER);
            ItemMeta PublicStoragePullMeta = PublicStoragePull.getItemMeta();
            PublicStoragePullMeta.setDisplayName("§ePull");
            PublicStoragePullMeta.setLore(Arrays.asList("§fPull items from PublicStorage(1~2000)"));
            PublicStoragePull.setItemMeta(PublicStoragePullMeta);
            return PublicStoragePull;
        }

    }

    //ItemStack that use to display minus option
    class Minus {
        private ItemStack Minus10() {
            ItemStack Minus10 = new ItemStack(Material.ENDER_EYE, 10);
            ItemMeta Minus10Meta = Minus10.getItemMeta();
            // §9 is a color code of blue.
            Minus10Meta.setDisplayName("§9-10");
            Minus10Meta.setLore(Arrays.asList("§fSelect this option,", " you can minus 10 from quantity of you selected items."));
            Minus10.setItemMeta(Minus10Meta);
            return Minus10;
        }

        private ItemStack Minus5() {
            ItemStack Minus5 = new ItemStack(Material.ENDER_EYE, 5);
            ItemMeta Minus5Meta = Minus5.getItemMeta();
            Minus5Meta.setDisplayName("§9-5");
            Minus5Meta.setLore(Arrays.asList("§fSelect this option,", " you can minus 5 from quantity of you selected items."));
            Minus5.setItemMeta(Minus5Meta);
            return Minus5;
        }

        private ItemStack Minus1() {
            ItemStack Minus1 = new ItemStack(Material.ENDER_EYE, 1);
            ItemMeta Minus1Meta = Minus1.getItemMeta();
            Minus1Meta.setDisplayName("§9-1");
            Minus1Meta.setLore(Arrays.asList("§fSelect this option,", " you can minus 1 from quantity of you selected items."));
            Minus1.setItemMeta(Minus1Meta);
            return Minus1;
        }

        private ItemStack MinusAll(int Quantity) {
            ItemStack MinusAll = new ItemStack(Material.ENDER_EYE, Quantity);
            ItemMeta MinusAllMeta = MinusAll.getItemMeta();
            MinusAllMeta.setDisplayName("§9-All");
            MinusAllMeta.setLore(Arrays.asList("§fSelect this option,", " you can set 0 that quantity of you selected items."));
            MinusAll.setItemMeta(MinusAllMeta);
            return MinusAll;
        }
    }

    //ItemStack that use to display plus option
    class Plus {
        private ItemStack Plus10() {
            ItemStack Plus10 = new ItemStack(Material.ENDER_PEARL, 10);
            ItemMeta Plus10Meta = Plus10.getItemMeta();
            Plus10Meta.setDisplayName("§e+10");
            Plus10Meta.setLore(Arrays.asList("§fSelect this option,", " you can plus 10 to quantity of you selected items."));
            Plus10.setItemMeta(Plus10Meta);
            return Plus10;
        }

    }


