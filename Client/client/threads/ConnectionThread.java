/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.threads;

import client.gui.menu.GameMenu;
import client.gui.menu.MenuManager;
import client.gui.menu.PregameMenu;
import client.main.Client;
import client.object_wrappers.EntityListWrapper;
import engine.entities.Entity;
import engine.entities.ships.*;
import engine.gameMechanics.gameModes.GameMode;
import engine.main.CycleRunner;
import engine.main.EntityList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class ConnectionThread extends Thread{
    
    private ArrayList<Object> outputQueue = new ArrayList<>();
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    private ObjectInputStream inStream;
    private ObjectOutputStream outputStream;
    
    private boolean listening = false;
    private String hostName;
    private int portNumber;
    
    
    public ConnectionThread(String[] args, boolean running){
        
        listening = running;
        
        
        //Attempts to set values for the host name and the port number
        try {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        } catch(Exception e){
            System.out.println("An error occured while reading from console:");
            System.err.println(e.toString());
            System.out.println("Usage: Client <hostName> <portNumber> <clientName>");
            
            hostName = "localhost";
            portNumber = 25565;
            
            System.out.println("The host name has been set to \"" + hostName + "\".");
            System.out.println("The port number has been set to \"" + portNumber + "\"." + "\n");
        }
        
        System.out.println("You will be connected as \"" + Client.getName() + "\"." + "\n");
        
        try{
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            
        }
        
        
        
        
    }
    
    @Override
    public void run(){
        listening = true;
        
        try{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        } catch(NullPointerException n){
            listening = false;
        }
        
        
        
        try {
            System.out.println("Name: " + Client.getName());
            System.out.println(listening);
            while(listening){
                if(Client.getID() == -1){
                    outputQueue.add("[CONNECT]" + Client.getName());
                }
                
                if(outputQueue.size() > 0){
                    Object outputObject = outputQueue.remove(0);
                    //System.out.println(outputObject);
                    outputStream.writeObject(outputObject);
                }
                
                Object input = inStream.readObject();
                //System.out.println("Current input: " + input);
                processInput(input);
            }
            
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            
        } catch(ClassNotFoundException e){
            
            System.err.println("Couldn't interpret the read object.");
            
        }
        
        Client.setID(-1);
        listening = false;
        MenuManager.setMenu("hostORjoin");
        
        
    }
    
    public void processInput(Object obj){
        System.out.println(obj);
        if(obj instanceof EntityListWrapper){
            
            EntityListWrapper w = (EntityListWrapper) obj;
            EntityList.setList(w.getContents());
            
        } else if(obj instanceof GameMode){
            
            CycleRunner.setGamemode((GameMode) obj);
            
        } else if(obj instanceof String){
            processInput((String) obj);
        }
    }
    
    public void processInput(String input){
        String alphaTag = input.substring(0,input.indexOf("]") + 1);
        
        System.out.println(alphaTag);
        
        if(alphaTag.equals("[FACTIONID]")){
            //Sets the Faction ID number
            Client.setID(Integer.parseInt(input.substring(alphaTag.length())));
            System.out.println("Faction ID established for \"" + Client.getName() + "\": " + Client.getID());
        } else if(alphaTag.equals("[GAMESTATUS]")){
            //Something about the game is changing
            String status = input.substring(alphaTag.length());
            if(status.equals("STARTING")){
                //The match is beginning
                if((MenuManager.getMenu() instanceof PregameMenu)){
                    MenuManager.setMenu("gameInterface");
                }
            }
        }
        
    }
    
    public void sendObject(Object obj) {
        try{
            outputStream.writeObject(obj);
        } catch (Exception e){
            
        }
    }
    
    public boolean listening(){
        return listening;
    }
    
}
