/*
 * The ToggleButton class stores data for a button that can be turned on and off
 * like a switch.
 */

package client.gui.menu.buttons;

/**
 *
 * @author Christopher
 */
public class ToggleButton extends Button{
    private boolean mode;
    private String falseCommand;
    
    public ToggleButton(int x1, int y1, int width, int height, String command, String offCommand, String text, boolean startMode) {
        super(x1, y1, width, height, command, text);
        mode = startMode;
        falseCommand = offCommand;
    }
    
    public ToggleButton(int x1, int y1, int width, int height, String command, String offCommand, String text) {
        super(x1, y1, width, height, command, text);
        mode = false;
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
    
}
