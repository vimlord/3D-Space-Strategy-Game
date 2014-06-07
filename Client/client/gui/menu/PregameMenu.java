/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.gui.menu.buttons.Button;
import client.main.Client;
import engine.gameMechanics.gameModes.*;
import engine.main.CycleRunner;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class PregameMenu extends Menu{
    
    public PregameMenu(){
        super("preGame", true);
    }
    
    protected ArrayList<Button> initButtons(){
        ArrayList<Button> buttons = new ArrayList<>();
        
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        buttons.add(new Button((xGUI) - 160, yGUI - 60, 120, 30, "[SETMENU]hostORjoin", "Leave Game"));
        
        return buttons;
    }

    @Override
    public void cycle() {
        super.cycle();
    }

    @Override
    public void drawMenu(Graphics g) {
        int xGUI = Client.getGUI().getWidth();
        int yGUI = Client.getGUI().getHeight();
        
        Graphics2D g2 = (Graphics2D) g;
        drawButtons(g);
        
        g2.drawRect(xGUI - 325, 25, 300, 450);
        
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Gamemode:", xGUI - 320, 42);
        g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        
        String gameName;
        try{
            GameMode gm = CycleRunner.getGamemode();
            if(gm instanceof Domination)
                gameName = "Domination";
            else if(gm instanceof FreeForAll)
                gameName = "Free-for-All";
            else if(gm instanceof TeamDeathmatch)
                gameName = "Team Deathmatch";
            else if(gm instanceof TeamDomination)
                gameName = "Team Domination";
            else
                gameName = "Unknown Gamemode";
            
        } catch(NullPointerException e){
            gameName = "Unknown Gamemode";
        }
        
        
        g2.drawString(gameName, xGUI - 204, 42);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        int numPlayers = 0;
        int numTeams;
        
        try{
            
            int[][] factions = CycleRunner.getGamemode().getFactions();

            numTeams = factions.length;
            
            for(int i = 0; i < factions.length; i++){
                numPlayers += factions[i].length;
            }
            
        } catch(NullPointerException npe){
            numTeams = 0;
        }
            
        g2.drawString("Number of Players: " + numPlayers, xGUI - 320, 58);
        g2.drawString("Number of Teams: " + numTeams, xGUI - 320, 72);
        
    }
    
}
