/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.main;

import client.gui.GUI;
import client.gui.menu.HostOrJoinMenu;
import client.gui.menu.MainMenu;
import client.gui.menu.MenuManager;
import client.threads.ConnectionThread;
import client.threads.CycleThread;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Client {
    
    private static GUI gui = new GUI();
    
    private static int factionID = -1;
    private static String clientName = null;
    
    private static ConnectionThread connection;
    private static CycleThread cycler;
    
    private static boolean lookingForConnection = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        cycler = new CycleThread();
        cycler.start();
        
        MenuManager.addMenu(new MainMenu());
        MenuManager.addMenu(new HostOrJoinMenu());
        MenuManager.setMenu(0);
        
        while(true){
            
            
            while(lookingForConnection){
                connection = new ConnectionThread(args);
                connection.start();
                
                
                while(connection.listening()){
                
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
