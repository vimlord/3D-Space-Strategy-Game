/*
 * This class will store data for a menu. It can be on or off, and will contain
 * a series of buttons.
 */

package client.gui.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import client.gui.menu.buttons.Button;
import client.main.Client;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Menu {
    protected final boolean usesConnection;
    private ArrayList<Button> buttons;
    private final String name;
    
    public Menu(ArrayList<Button> b, String name, boolean connects){
        buttons = b;
        this.name = name;
        usesConnection = connects;
    }
    
    public Menu(String name, boolean connects){
        buttons = initButtons();
        this.name = name;
        usesConnection = connects;
    }
    
    
    public void cycle(){
        if(!Client.isLookingForConnection() && usesConnection){
            Client.setLookingForConnection(true);
        } else if(Client.isLookingForConnection() && !usesConnection){
            Client.setLookingForConnection(false);
        }
    }
    
    protected abstract ArrayList<Button> initButtons();
    
    public ArrayList<Button> getButtons(){
        return buttons;
    }
    
    
    public void drawButtons(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for(Button b : buttons){
            b.drawButton(g2);
        }
    }
    public String clickButtons(int x, int y){
        String order = null;
        for(Button b : buttons){
            if(b.testHit(x, y)){
                order = b.pushButton();
                break;
            }
        }
        return order;
        
        
    }
    
    public String getName(){
        return name;
    }

    public abstract void drawMenu(Graphics g);
}
