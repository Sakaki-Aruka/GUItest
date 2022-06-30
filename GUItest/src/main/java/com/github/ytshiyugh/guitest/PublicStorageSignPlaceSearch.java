package com.github.ytshiyugh.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PublicStorageSignPlaceSearch {
    public int SignPlaceX() {
        //
        int first = 1;
        while (first < 101) {
            Location loc = new Location(Bukkit.getWorld("world"), first, 300, 0);
            Material locBlock = loc.getBlock().getType();
            System.out.println(locBlock);
            if (locBlock.toString().equalsIgnoreCase("air")) {
                return first;
            }
            first += 1;
        }

        int third = 1;
        while (third < 101){
            Location loc = new Location(Bukkit.getWorld("world"),third,300,0);
            Material locBlock = loc.getBlock().getType();
            System.out.println("third:"+locBlock);
            if(locBlock.toString().equalsIgnoreCase("air")){
                return third;
            }
            third += 1;
        }
        return -1;
    }

    public int SignPlaceZ(){
        int second = 1;
        while (second < 101){
            Location loc = new Location(Bukkit.getWorld("world"),0,300,second);
            Material locBlock = loc.getBlock().getType();
            System.out.println("second:"+locBlock);
            if(locBlock.toString().equalsIgnoreCase("air")){
                return second;
            }
            second += 1;
        }
        int forth = 1;
        while (forth < 101) {
            Location loc = new Location(Bukkit.getWorld("world"), 0, 300, forth);
            Material locBlock = loc.getBlock().getType();
            System.out.println("forth:"+locBlock);
            if (locBlock.toString().equalsIgnoreCase("air")) {
                return forth;
            }
            forth += 1;
        }


        return -1;
    }
}
