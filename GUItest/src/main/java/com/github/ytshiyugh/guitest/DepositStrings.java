package com.github.ytshiyugh.guitest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DepositStrings {
    public void Error(Player sender,String arg){
        String ErrorMessage = ChatColor.YELLOW+"[Error]:"+arg;
        sender.sendMessage(ErrorMessage);
    }

    public void Successful(Player sender,String arg){
        String SuccessfulMessage = ChatColor.GREEN+"[Result]"+arg;
        sender.sendMessage(SuccessfulMessage);
    }
}