package com.github.ytshiyugh.guitest;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Set;

public class HomeCommandSendAsPlayer {

    public String SuccessfulMessage(){
        //訳：「GUIチャットからのコマンド送信に成功したよ。」の雛形
        String chat = ChatColor.GREEN+"[Result]:"+ChatColor.WHITE+"The command send from Menu was successful.";
        return chat;
    }
    private void HomeCommandSendTeleport(Player sender){
        sender.chat("/home tel");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    private void HomeCommandSendRemove(Player sender){
        sender.chat("/home rem");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    private void HomeCommandSendSet(Player sender){
        sender.chat("/home set");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    public void HomeCommandFromGUIMenu(Player sender, Set UserTags,int ClickNo){
        HomeCommandSendAsPlayer HCSAP = new HomeCommandSendAsPlayer();
        if(UserTags.contains("HomeCommandOpen")){
            if(ClickNo==0){
                HCSAP.HomeCommandSendTeleport(sender);
                sender.closeInventory();
            }else if(ClickNo==2){
                HCSAP.HomeCommandSendSet(sender);
                sender.closeInventory();
            }else if(ClickNo==4){
                HCSAP.HomeCommandSendRemove(sender);
                sender.closeInventory();
                }
            }
        }
    }

