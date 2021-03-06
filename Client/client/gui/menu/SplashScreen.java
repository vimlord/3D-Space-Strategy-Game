/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public abstract class SplashScreen extends Menu {
    private int cycle = 0;
    private final int numCycles;
    private final String nextMenu;
    
    public SplashScreen(String endMenu, String name, int numCycles) {
        super(name, false);
        this.numCycles = numCycles;
        nextMenu = endMenu;
    }

    @Override
    public void cycle() {
        super.cycle();
        
        cycle++;
        
        if(cycle > numCycles){
            MenuManager.setMenu(MenuManager.getMenuIndex(MenuManager.getMenu(nextMenu)));
            cycle = 0;
        }
        
    }

    @Override
    public abstract void drawMenu(Graphics g);
    
    
    public double getCompletionStatus(){
        return ((double) cycle) / numCycles;
    }
    
    public int getNumCycles(){
        return numCycles;
    }
    
    public int getCycle(){
        return cycle;
    }
    
}
