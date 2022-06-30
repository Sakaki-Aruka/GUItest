package com.github.ytshiyugh.guitest;

import org.bukkit.entity.Player;

import java.util.Set;

public class WorldTeleportCommandSendAsPlayer {
    private void WorldTeleportSendLobby(Player sender){
        sender.chat("/wtp l");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    private void WorldTeleportSendEveryone(Player sender){
        sender.chat("/wtp e");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    private void WorldTeleportSendOverWorld(Player sender){
        sender.chat("/wtp o");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    private void WorldTeleportSendWeeklyEnd(Player sender){
        sender.chat("/wtp w");
        sender.sendMessage(new HomeCommandSendAsPlayer().SuccessfulMessage());
    }

    public void WorldTeleportFromGUIMenu(Player sender, Set UserTags,int ClickNo){
        WorldTeleportCommandSendAsPlayer WTCSAP = new WorldTeleportCommandSendAsPlayer();
        if(UserTags.contains("WorldTeleportOpen")){
            if(ClickNo==0){
                WTCSAP.WorldTeleportSendLobby(sender);
                sender.closeInventory();
            }else if(ClickNo==1){
                WTCSAP.WorldTeleportSendOverWorld(sender);
                sender.closeInventory();
            }else if(ClickNo==2){
                WTCSAP.WorldTeleportSendEveryone(sender);
                sender.closeInventory();
            }else if(ClickNo==3){
                WTCSAP.WorldTeleportSendWeeklyEnd(sender);
                sender.closeInventory();
            }
        }
    }
}
