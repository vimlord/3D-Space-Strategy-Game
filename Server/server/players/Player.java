/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.players;

/**
 *
 * @author Christopher
 */
public class Player {
    private final String username;
    private final int factionID;
    private boolean isActive = false;
    
    public Player(String name, int id){
        username = name;
        factionID = id;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public void setActive(boolean active){
        isActive = active;
    }
    
    public String getUsername(){
        return username;
    }
    
    public int getFactionID(){
        return factionID;
    }
    
    public boolean equals(Player other){
        if(this.getUsername().equals(other.getUsername())){
            if(this.getFactionID() == other.getFactionID()){
                return true;
            }
        }
        
        return false;
    }
    
}
