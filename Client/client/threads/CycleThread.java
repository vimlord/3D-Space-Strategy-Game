/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.threads;

import client.gui.menu.MenuManager;
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
        
        int tick = 0;
        
        while(true){
            Client.getGUI().redraw();
            try{
                MenuManager.getMenu().cycle();
            } catch(NullPointerException e){
                
            }
            
            if(tick >= 1000){
                
                try{
                    if(Client.getConnection().listening()){
                        Client.getConnection().sendObject("[SEND][STATUS]");
                    }
                } catch(Exception e){
                    
                }
                
                tick = 0;
            }
            
            tick++;
            
        }
        
    }
    
}
