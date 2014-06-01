/*
 * This class will store data for a menu. It can be on or off, and will contain
 * a series of buttons.
 */

package client.gui.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import client.gui.menu.buttons.Button;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Menu {
    private boolean active = false;
    private ArrayList<Button> buttons;
    private final String name;
    
    public Menu(ArrayList<Button> b, String name){
        buttons = b;
        this.name = name;
    }
    
    
    public abstract void cycle();
    
    
    public void setStatus(boolean status){
        active = status;
    }
    public boolean getStatus(){
        return active;
    }
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
                order = b.getCommand();
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
