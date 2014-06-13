/*
 * The ToggleButton class stores data for a button that can be turned on and off
 * like a switch.
 */

package client.gui.menu.buttons;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Christopher
 */
public class ToggleButton extends Button{
    private boolean state;
    private String falseCommand;
    private Color onColor;
    
    public ToggleButton(int x, int y, int width, int height, String command, String offCommand, String text, boolean startMode) {
        super(x, y, width, height, command, text);
        state = startMode;
        falseCommand = offCommand;
        onColor = new Color(255, 255, 255);
    }
    
    /**
     *
     * @param x The y-coordinate of the top left corner
     * @param y The x-coordinate of the top left corner
     * @param width The width of the button
     * @param height The height of the button
     * @param command The command given in the "on" state
     * @param offCommand The command given in the "off" state
     * @param text The text on the button
     */
    public ToggleButton(int x, int y, int width, int height, String command, String offCommand, String text) {
        super(x, y, width, height, command, text);
        state = false;
        onColor = new Color(255,255,255);
        falseCommand = offCommand;
    }
    
    public String pushButton(){
        state = !state;
        return getCommand();
    }
    
    public String getCommand(){
        if(state)
            return super.getCommand();
        else
            return falseCommand;
    }
    
    public String getCommand(boolean hypotheticalCase){
        if(hypotheticalCase)
            return super.getCommand();
        else
            return falseCommand;
    }
    
    /**
     * Gets the color displayed when the button is active
     * @return The color of the "on" state
     */
    public Color getOnColor(){
        return onColor;
    }
    
    /**
     * Sets the Color used when drawing the "on" state
     * @param c The Color chosen
     */
    public void setOnColor(Color c){
        onColor = c;
    }
    
    public void drawButton(Graphics2D g2){
        if(!active) return;
        
        if(state && active) super.drawButton(g2, onColor);
        else super.drawButton(g2);
    }
    
}
