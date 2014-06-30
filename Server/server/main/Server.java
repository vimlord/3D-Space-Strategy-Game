/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.main;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import server.gui.GUI;
import server.threads.CycleThread;
import server.threads.ListenerThread;
import server.tools.GameClock;

/**
 *
 * @author Christopher
 */
public class Server {
    
    private static GameClock clock;
    
    private static final ArrayList<String> log = new ArrayList<>();
    
    private static GUI gui;
    
    private static int playerLimit = 0;
    
    private static ArrayList<ListenerThread> connections = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int portNumber;
        
        clock = new GameClock();
        gui = new GUI();
        
        //Attempts to set values for the host name and the port number
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch(Exception e){
            System.out.println("An error occured while reading from console:");
            System.err.println(e.toString());
            System.out.println("Usage: Server <portNumber>");
            
            portNumber = 25565;
            
            addLogEvent("The port number has been set to \"" + portNumber + "\"." + "\n");
            System.out.println(getLatestLogEvent());
        }
        
        boolean running = true;
        
        try(ServerSocket serverSocket = new ServerSocket(portNumber)){
            
            CycleThread game = new CycleThread();
            game.start();
            
            while(running){
                //Looks for a connection with a client and establishes it if possible
                connections.add(new ListenerThread(serverSocket.accept()));
                connections.get(connections.size() - 1).start();
                //Executes a cycle in the CycleRunner class. This will move Entities
                //and execute collisions, Ship orders, and other necessary processes.
                //For a broader list, look at CycleRunner.executeCycle()
            }
            
            
        } catch(IOException e){
            addLogEvent("Exception caught while trying to listen on port " + portNumber);
            System.out.println(getLatestLogEvent());
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        System.exit(0);
        
    }
    
    public static void addLogEvent(String event){
        log.add(0,getTime(true) + " - "+ event);
    }
    
    public static ArrayList<String> getLog(){
        return log;
    }
    
    public static String getLatestLogEvent(){
        return log.get(0);
    }

    public static void cycleClock() {
        clock.cycle();
    }
    
    public static String getTime(boolean showTicks){
        return clock.toString(showTicks);
    }
    
    public static GUI getGUI(){
        return gui;
    }
    
    public static int getPlayerLimit(){
        return playerLimit;
    }
    
    public static void setPlayerLimit(int limit){
        playerLimit = limit;
    }
    
    public static ArrayList<ListenerThread> getConnections(){
        return connections;
    }
    
}
