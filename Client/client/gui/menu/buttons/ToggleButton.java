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
    private boolean mode;
    private String falseCommand;
    private final Color onColor;
    
    public ToggleButton(int x1, int y1, int width, int height, String command, String offCommand, String text, boolean startMode) {
        super(x1, y1, width, height, command, text);
        mode = startMode;
        falseCommand = offCommand;
        onColor = new Color(255, 255, 255);
    }
    
    public ToggleButton(int x1, int y1, int width, int height, String command, String offCommand, String text) {
        super(x1, y1, width, height, command, text);
        mode = false;
        onColor = new Color(255,255,255);
        falseCommand = offCommand;
    }
    
    public String pushButton(){
        mode = !mode;
        return getCommand();
    }
    
    public String getCommand(){
        if(mode)
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
    
    public void drawButton(Graphics2D g2){
        if(mode) super.drawButton(g2, onColor);
        else super.drawButton(g2);
    }
    
}
