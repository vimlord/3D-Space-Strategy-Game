/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import client.main.Client;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class HostOrJoinMenu extends Menu{

    private ArrayList<String> connectionNames = new ArrayList<>();
    
    public HostOrJoinMenu() {
        super("hostORjoin", true);
    }
    
    
    @Override
    protected ArrayList<Button> initButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        buttons.add(new Button((xGUI/2) - 200, yGUI - 100, 120, 30, "[SETMENU]preGame", "Join Game"));
        buttons.add(new Button((xGUI/2) + 80, yGUI - 100, 120, 30, "[SETMENU]hostGame", "Host Game"));
        
        return buttons;
    }
    
    

    @Override
    public void drawMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawButtons(g);
        
        g2.drawRect(100, 50, Client.getGUI().getWidth() - 200, Client.getGUI().getHeight() - 200);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 14));
        if(connectionNames.size() > 0){
            for(int i = 0; i < (Client.getGUI().getHeight() - 250)/18 && i < connectionNames.size(); i++){
                g2.drawString(connectionNames.get(i), 105, 61 + (18 * i));
            }
        } else {
            g2.drawString("No games available to select.", 105, 61);
        }
        
    }
    
}
