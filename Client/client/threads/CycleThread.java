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
import engine.main.EntityList;
import java.io.Serializable;

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
            
            //System.out.println("EntityList size: " + EntityList.getEntityList().size());
            
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
                    
                    Serializable request;
                    
                    if(MenuManager.getMenu() instanceof GameMenu){
                        request = "[SEND][ENTITYLIST]";
                        Client.getConnection().sendObject(request);
                    } else if(MenuManager.getMenu() instanceof PregameMenu){
                        request = "[SEND][GAMEMODE]";
                    } else
                        request = null;
                    
                    if(request != null)
                        Client.getConnection().sendObject(request);
                    
                } catch(Exception e){
                    
                }
                
                gameTick = 0;
            }
            
            
            try{
                if(MenuManager.getMenu().connects()){
                    gameTick++;
                    statusTick++;
                }
            } catch(Exception e){
                
            }
        }
        
    }
    
}
