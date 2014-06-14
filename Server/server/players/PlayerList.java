/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.players;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class PlayerList {
    private static ArrayList<Player> players = new ArrayList<>();
    
    public static boolean addPlayer(Player p){
        if(wasAlreadyAdded(p)){
            return false;
        } else {
            players.add(p);
            return true;
        }
    }
    
    public static boolean wasAlreadyAdded(Player test){
        for(Player p : players){
            if(test.equals(p)){
                return true;
            }
        }
        
        return false;
    }
    
    public static Player getPlayer(int id){
        
        for(Player p : players){
            if(p.getFactionID() == id){
                return p;
            }
        }
        
        return null;
        
    }
    
    public static Player getPlayer(String name){
        
        for(Player p : players){
            if(p.getUsername().equals(name)){
                return p;
            }
        }
        
        return null;
        
    }
    
    public static ArrayList<Player> getPlayerList(){
        return players;
    }
    
}
