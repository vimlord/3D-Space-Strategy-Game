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
public class GameMenu extends Menu{

    public GameMenu(boolean connects) {
        super("gameInterface", true);
    }

    @Override
    protected ArrayList<Button> initButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        //Specific direction buttons
        buttons.add(new Button(xGUI - 200, yGUI - 120, 40, 40,"[INTERFACE]Prograde",""));
        buttons.add(new Button(xGUI - 160, yGUI - 120, 40, 40,"[INTERFACE]Retrograde",""));
        buttons.add(new Button(xGUI - 200, yGUI - 80, 40, 40,"[INTERFACE]Normal",""));
        buttons.add(new Button(xGUI - 160, yGUI - 80, 40, 40,"[INTERFACE]Antinormal",""));
        buttons.add(new Button(xGUI - 200, yGUI - 120, 40, 40,"[INTERFACE]RadialIn",""));
        buttons.add(new Button(xGUI - 160, yGUI - 120, 40, 40,"[INTERFACE]RadialOut",""));
        
        //Direction adjustment buttons
        buttons.add(new Button(xGUI - 80, yGUI - 120, 40, 40,"[INTERFACE]PointUp",""));
        buttons.add(new Button(xGUI - 80, yGUI - 40, 40, 40,"[INTERFACE]PointDown",""));
        buttons.add(new Button(xGUI - 120, yGUI - 80, 40, 40,"[INTERFACE]PointLeft",""));
        buttons.add(new Button(xGUI - 40, yGUI - 80, 40, 40,"[INTERFACE]PointRight",""));
        buttons.add(new Button(xGUI - 80, yGUI - 80, 40, 40,"[INTERFACE]ExecuteRotation",""));
        
        for(Button b : buttons){
            b.setStatus(false);
        }
        
        return buttons;
    }

    @Override
    public void drawMenu(Graphics g) {
        Client.getGUI().drawGameInterface(g);
        drawButtons(g);
        
    }
    
}
