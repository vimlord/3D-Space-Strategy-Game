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
 * @author Christopher Hittner
 */
public class MainMenu extends Menu{

    public MainMenu() {
        super("mainMenu");
    }
    
    protected ArrayList<Button> initButtons(){
        
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        //A button for the Host/join match menu
        buttons.add(new Button((xGUI/2) - 60, 200, 120, 30, "[SETMENU]hostORjoin", "Play Game"));
        
        //For when I add an Options menu
        buttons.add(new Button((xGUI/2) - 60, 250, 120, 30, "[SETMENU]optionsMain", "Options"));
        
        //Credits menu when implemented
        buttons.add(new Button((xGUI/2) - 60, 300, 120, 30, "[SETMENU]credits", "Credits"));
        
        //Quit button
        buttons.add(new Button((xGUI/2) - 60, 350, 120, 30, "[END]", "Exit"));
        
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
