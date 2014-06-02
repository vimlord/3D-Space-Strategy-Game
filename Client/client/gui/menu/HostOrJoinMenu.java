/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import client.main.Client;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class HostOrJoinMenu extends Menu{

    public HostOrJoinMenu() {
        super("hostORjoin");
    }
    
    
    @Override
    protected ArrayList<Button> initButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        buttons.add(new Button((xGUI/2) - 200, yGUI - 150, 120, 30, "[SETMENU]preGame", "Join Game"));
        buttons.add(new Button((xGUI/2) + 80, yGUI - 150, 120, 30, "[SETMENU]hostGame", "Host Game"));
        
        return buttons;
    }
    
    
    @Override
    public void cycle() {
        
    }
    

    @Override
    public void drawMenu(Graphics g) {
        drawButtons(g);
    }
    
}
