/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.main;

import client.game.GameControlSettings;
import client.gui.GUI;
import client.gui.menu.GameMenu;
import client.gui.menu.HostOrJoinMenu;
import client.gui.menu.MainMenu;
import client.gui.menu.MenuManager;
import client.gui.menu.PregameMenu;
import client.settings.Options;
import client.threads.ConnectionThread;
import client.threads.CycleThread;
import engine.entities.ships.Corvette;
import engine.main.EntityList;

/**
 *
 * @author Christopher
 */
public class Client {
    
    private static GUI gui = new GUI(800,600);
    
    private static int factionID = -1;
    private static String clientName = "Guest2";
    
    private static ConnectionThread connection;
    private static CycleThread cycler;
    
    private static boolean lookingForConnection = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        cycler = new CycleThread();
        cycler.start();
        
        Options.loadPredeterminedOptions();
        
        try{
           setName(args[2]); 
        } catch (Exception e){
            
        }
        
        MenuManager.addMenu(new MainMenu());
        MenuManager.addMenu(new HostOrJoinMenu());
        MenuManager.addMenu(new PregameMenu());
        MenuManager.addMenu(new GameMenu());
        MenuManager.setMenu(0);
        
        while(true){
            
            Thread.sleep(1);
            
            if(lookingForConnection){
                
                
                connection = new ConnectionThread(args, true);
                connection.start();
                
                while(connection.listening()){
                    
                    Thread.sleep(1);
                    
                    if(!(MenuManager.getMenu() instanceof PregameMenu || MenuManager.getMenu() instanceof GameMenu)){
                        connection.sendObject("[EXIT]");
                        break;
                    }
                    
                    
                }
                
            }
            
        }
        
    }
    
    public static ConnectionThread getConnection(){
        return connection;
    }
    
    public static void setName(String nm){
        clientName = nm;
    }
    
    public static String getName(){
        return clientName;
    }
    
    public static void setID(int id){
        factionID = id;
    }
    
    public static int getID(){
        return factionID;
    }
    
    public static GUI getGUI(){
        return gui;
    }
    
    public static boolean isLookingForConnection(){
        return lookingForConnection;
    }
    
    public static void setLookingForConnection(boolean isListening){
        lookingForConnection = isListening;
    }
    
    
}
