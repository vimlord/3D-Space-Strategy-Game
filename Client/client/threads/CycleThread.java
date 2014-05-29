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
        
        while(true){
            Client.getGUI().redraw();
            MenuManager.getMenu().cycle();
        }
        
    }
    
}
