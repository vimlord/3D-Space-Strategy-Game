/*
 * This class stores data for a Button. A Button is an object that will issue a
 * String as output, which will basically be a set of instructions.
 * Any instructions that I create code for will be listed below.
 * [SETMENU] - Sets the menu to a new value
 *
 */

package client.menu.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class Button {
    private boolean active = false;
    private int x1, x2, y1, y2;
    private String command, text;
    
    /**
     *
     * @param x1 Upper left x coordinate
     * @param y1 Upper left y coordinate
     * @param x2 Lower right x coordinate
     * @param y2 Lower right y coordinate
     * @param command The command issued when the button is pressed
     */
    public Button(int x1, int y1, int width, int height, String command, String text){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        this.command = command;
        this.text = text;
    }
    
    public void setStatus(boolean status){
        active = status;
    }
    public boolean getStatus(){
        return active;
    }
    
    public boolean testHit(int x, int y){
        if(x > x1 && x <= x2){ //Tests the x value
            if(x > y1 && y <= y2){ //Tests the y value
                return true;
            }
            
        }
        return false;
    }
    
    public String getCommand(){
        return command;
    }

    public void drawButton(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.fillRect(x1, y1, x2 - x1, y2 - y1);
        g2.setColor(Color.BLACK);
        g2.drawRect(x1, y1, x2 - x1, y2 - y1);
        
        g2.setFont(new Font("Courier New", Font.BOLD, 24));
        
        int centX = (x1 + x2)/2;
        int centY = (y1 + y2)/2;
        
        g2.drawString(text,centX - (8 * text.length()),centY);
        
    }
    
}
