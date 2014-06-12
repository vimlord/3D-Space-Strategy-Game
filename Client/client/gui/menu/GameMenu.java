/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import client.gui.menu.buttons.Slider;
import client.gui.menu.buttons.ToggleButton;
import client.main.Client;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class GameMenu extends Menu{

    public GameMenu() {
        super("gameInterface", true);
    }

    @Override
    protected ArrayList<Button> initButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getAppletWidth();
        int yGUI = Client.getGUI().getAppletHeight();
        
        //Specific direction buttons
        buttons.add(new Button(xGUI - 200, yGUI - 120, 40, 40,"[SHIP_ORDER]Prograde","PG"));
        buttons.add(new Button(xGUI - 160, yGUI - 120, 40, 40,"[SHIP_ORDER]Retrograde","RG"));
        buttons.add(new Button(xGUI - 200, yGUI - 80, 40, 40,"[SHIP_ORDER]Normal","N"));
        buttons.add(new Button(xGUI - 160, yGUI - 80, 40, 40,"[SHIP_ORDER]Antinormal","AN"));
        buttons.add(new Button(xGUI - 200, yGUI - 40, 40, 40,"[SHIP_ORDER]RadialIn","RIN"));
        buttons.add(new Button(xGUI - 160, yGUI - 40, 40, 40,"[SHIP_ORDER]RadialOut","ROUT"));
        
        //Direction adjustment buttons
        buttons.add(new Button(xGUI - 80, yGUI - 120, 40, 40,"[SHIP_ORDER]PointUp",""));
        buttons.add(new Button(xGUI - 80, yGUI - 40, 40, 40,"[SHIP_ORDER]PointDown",""));
        buttons.add(new Button(xGUI - 120, yGUI - 80, 40, 40,"[SHIP_ORDER]PointLeft",""));
        buttons.add(new Button(xGUI - 40, yGUI - 80, 40, 40,"[SHIP_ORDER]PointRight",""));
        buttons.add(new Button(xGUI - 80, yGUI - 80, 40, 40,"[SHIP_ORDER]ExecuteRotation","EXEC"));
        
        //Weapon Controls
        buttons.add(new ToggleButton(xGUI - 200, yGUI - 200, 40, 40,"[PLAYER_CONTROL]AttackON","[PLAYER_CONTROL]AttackOFF","ARM"));
        buttons.add(new ToggleButton(xGUI - 200, yGUI - 240, 40, 40,"[PLAYER_CONTROL]AutoFireON","[PLAYER_CONTROL]AutoFireOFF","AUTO"));
        buttons.add(new Button(xGUI - 200, yGUI - 280, 40, 40,"[SHIP_ORDER]FireMissiles","MSSL"));
        buttons.add(new Button(xGUI - 200, yGUI - 320, 40, 40,"[SHIP_ORDER]FireRailgun","RLGN"));
        
        //Throttle Controls
        buttons.add(new Slider(xGUI - 120, yGUI - 320, 40, 120,"[SHIP_ORDER]SetThrottle",true, true));
        buttons.add(new Slider(xGUI - 80, yGUI - 320, 40, 120,"[SHIP_ORDER]SetWarp",true, true));
        
        
        
        for(Button b : buttons){
            if(b.getCommand().substring(0,b.getCommand().indexOf("]")).equals("[SHIP_ORDER]")){
                b.setStatus(false);
            }
        }
        
        return buttons;
    }

    @Override
    public void drawMenu(Graphics g) {
        Client.getGUI().drawGameInterface(g);
        drawButtons(g);
        
    }
    
}
