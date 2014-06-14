/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.threads;

import client.gui.menu.GameMenu;
import client.gui.menu.MenuManager;
import client.gui.menu.PregameMenu;
import client.main.Client;

/**
 *
 * @author Christopher
 */
public class CycleThread extends Thread{
    
    public CycleThread(){
    }
    
    @Override
    public void run(){
        
        int statusTick = 0, gameTick = 5000;
        
        while(true){
            Client.getGUI().redraw();
            try{
                MenuManager.getMenu().cycle();
            } catch(Exception e){
                
            }
            
            if(statusTick >= 10000){
                
                try{
                    
                    if(Client.getConnection().listening()){
                        Client.getConnection().sendObject("[SEND][STATUS]");
                    }
                    
                } catch(Exception e){
                    
                }
                
                statusTick = 0;
                
            }
            if(gameTick >= 10000){
                
                try {
                    
                    /*if(MenuManager.getMenu() instanceof GameMenu){
                        Client.getConnection().sendObject("[SEND][ENTITY_LIST]");
                    } else */ if(MenuManager.getMenu() instanceof PregameMenu){
                        Client.getConnection().sendObject(new String("[SEND][GAMEMODE]"));
                    }
                    
                } catch(Exception e){
                    
                }
                
                gameTick = 0;
            }
            
            
            try{
                if(MenuManager.getMenu().connects())
                    gameTick++;
                    statusTick++;
            } catch(Exception e){
                
            }
        }
        
    }
    
}
