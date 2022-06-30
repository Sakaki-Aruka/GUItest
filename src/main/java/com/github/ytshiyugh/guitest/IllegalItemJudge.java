package com.github.ytshiyugh.guitest;

import java.util.Locale;

public class IllegalItemJudge {
    public boolean Judge(String InputItem){
        String InputItems = InputItem.toLowerCase(Locale.ROOT);
        String[] IllegalItems = {"helmet", "chestplate", "leggins", "boots", "bow", "sword", "tipped_arrow", "crossbow", "shield", "trident", "enchanted_book", "flint_and_steel", "shovel", "pickaxe", "axe", "hoe", "rod", "shears", "potion", "writable_book", "firework_rocket", "banner", "carrot_on_a_stick", "warped_fungus_on_a_stick", "elytra", "shulker_box", "player_head", "map", "water_bucket", "lava_bucket", "skull", "obsidian", "tnt", "compass","bucket_of_tadpole","disc_fragment","goat_horn","music_disc","recovery_compass","spawn_egg"};;
        for(int i=0;i<IllegalItems.length;i++){
            if(InputItems.contains(IllegalItems[i])){
                return false;
            }
        }
        return true;
    }
}
