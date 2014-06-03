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
public class PregameMenu extends Menu{
    
    public PregameMenu(){
        super("preGame", true);
    }
    
    protected ArrayList<Button> initButtons(){
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        buttons.add(new Button((xGUI/2) + 80, yGUI - 100, 120, 30, "[SETMENU]hostORjoin", "Leave Game"));
        
        return buttons;
    }

    @Override
    public void cycle() {
        super.cycle();
    }

    @Override
    public void drawMenu(Graphics g) {
        drawButtons(g);
    }
    
}
