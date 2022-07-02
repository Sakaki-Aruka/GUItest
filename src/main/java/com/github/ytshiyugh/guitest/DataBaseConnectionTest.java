package com.github.ytshiyugh.guitest;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnectionTest {
   public void DatabaseUPDATE(String input){
       Connection con;
       try{
           con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","aruka3^2xa3sa");
           try{
               Statement st = con.createStatement();
               int result = st.executeUpdate(input);
               return;
           }catch (SQLException e){
               return;
           }
       }catch (SQLException SE) {
           System.out.println("fail to connect mysql.");
           System.out.println("Error:"+SE);
           //Illegal means illegal connection or fail to connection(=error occurred)
           return;
       }
   }

    public Object Database(String input,String type,String requestData) {
        //only for search that public storage items or items amount
        //String input is input sql sentence.
        //requestData is field name.


        //here
        Connection con;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","aruka3^2xa3sa");
            try{
                Statement st = con.createStatement();
                //request send to database
                ResultSet result = st.executeQuery(input);
                //if return argument type is string
                if(type.equalsIgnoreCase("string")){
                    String things = "-1";
                    while(result.next()){
                        things = result.getString(requestData);
                        System.out.println("things:"+things);
                    }

                    con.close();
                    System.out.println("End of Connection");
                    return things;

                //if return argument type is int
                }else if(type.equalsIgnoreCase("int")){
                    int things = -1;
                    while(result.next()){
                        things = result.getInt(requestData);
                        System.out.println("things:"+things);
                    }
                    con.close();
                    System.out.println("End of Connection");
                    return things;

                }else if(type.equalsIgnoreCase("arraylist")){
                    //
                    ArrayList<String> things = new ArrayList<>();
                    while(result.next()){
                        things.add(result.getString(requestData));
                    }
                    con.close();
                    System.out.println("End of Connection");
                    return things;
                }else{
                    // TEKITOU process
                    return null;
                }

                /* memo
                while(result.next()) {
                    int amount = result.getInt("amount");
                    String itemid = result.getString("itemid");
                    System.out.println("ItemID:" + itemid + "/Amount:" + amount);
                    return "";
                }

                 */

            }catch (SQLException SE){
                System.out.println("SQLError:"+SE);
                return "Illegal";
            }
        }catch (SQLException SE) {
            System.out.println("fail to connect mysql.");
            System.out.println("Error:"+SE);
            //Illegal means illegal connection or fail to connection(=error occurred)
            return "Illegal";
        }

    }
}
