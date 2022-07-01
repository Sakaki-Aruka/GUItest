package com.github.ytshiyugh.guitest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;

public class DataBaseConnectionTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        //

        if(!(sender instanceof Player)){
            return false;
        }
        //here
        Connection con;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","aruka3^2xa3sa");
            try{
                Statement st = con.createStatement();
                //st.executeQuery("aruka3^2xa3sa");
                String sql = "select * from publicstorage;";
                ResultSet result = st.executeQuery(sql);
                while(result.next()) {
                    int amount = result.getInt("amount");
                    String itemid = result.getString("itemid");
                    System.out.println("ItemID:" + itemid + "/Amount:" + amount);
                }
            }finally {
                con.close();
            }
        }catch (SQLException SE) {
            System.out.println("fail to connect mysql.");
            System.out.println("Error:"+SE);
        }
        return false;
    }
}
