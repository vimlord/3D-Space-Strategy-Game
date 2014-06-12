/*
 * This class stores data for a Button. A Button is an object that will issue a
 * String as output, which will basically be a set of instructions.
 * Any instructions that I create code for will be listed below.
 * [SETMENU] - Sets the menu to a new value
 *
 */

package client.gui.menu.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher Hittner
 */
public class Button {
    protected boolean active = true;
    protected final int x, y, width, height;
    private String command, text;
    
    /**
     *
     * @param x Upper left x coordinate
     * @param y Upper left y coordinate
     * @param width The width
     * @param height The height
     * @param command The command issued when the button is pressed
     * @param text The text written on the button
     */
    public Button(int x, int y, int width, int height, String command, String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.command = command;
        this.text = text;
    }
    
    public void setStatus(boolean status){
        active = status;
    }
    public boolean getStatus(){
        return active;
    }
    
    public boolean testHit(int x_, int y_){
        if(x_ > x && x_ <= (x + width)){ //Tests the x value
            if(y_ > y && y_ <= (y + height)){ //Tests the y value
                return active;
            }
            
        }
        return false;
    }
    
    public String getCommand(){
        return command;
    }
    
    public String pushButton(){
        return getCommand();
    }

    public void drawButton(Graphics2D g2, Color c) {
        if(!active) return;
        g2.setColor(c);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
        
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        
        int centX = x + (width)/2;
        int centY = y + (height)/2;
        
        g2.setColor(Color.BLACK);
        if(active)
            g2.drawString(text,centX - (5 * text.length()),centY + 5);
        
    }
    
    public void drawButton(Graphics2D g2){
        drawButton(g2, Color.GRAY);
    }
    
}
