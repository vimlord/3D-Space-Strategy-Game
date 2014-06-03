/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class OptionsMenu extends Menu {

    public final Integer i = 6;
    
    public OptionsMenu() {
        super("optionsMenu", false);
        
    }

    @Override
    protected ArrayList<Button> initButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        
        
        return buttons;
    }

    @Override
    public void drawMenu(Graphics g) {
    
        drawButtons(g);
        
    }
    
    
}
