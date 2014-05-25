/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.threads;

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
    
    
    public ConnectionThread(String[] args){
        
        
        
        //Attempts to set values for the host name and the port number
        try {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
            Client.setName(args[2]);
        } catch(Exception e){
            System.out.println("An error occured while reading from console:");
            System.err.println(e.toString());
            System.out.println("Usage: Client <hostName> <portNumber> <clientName>");
            
            hostName = "localhost";
            portNumber = 25565;
            Client.setName("Guest");
            
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
            System.exit(1);
        }
        
        
        
        
    }
    
    @Override
    public void run(){
        
        listening = true;
        
        try{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            
            inStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject("[CONNECT]" + Client.getName());
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        
        
        
        try {
            
            while(listening){
                if(Client.getID() == -1){
                    outputQueue.add("[CONNECT]" + Client.getName());
                }
                
                Object input = inStream.readObject();
                //System.out.println("Current input: " + input);
                processInput(input);
                
                if(outputQueue.size() > 0){
                    Object outputObject = outputQueue.remove(0);
                    //System.out.println(outputObject);
                    outputStream.writeObject(outputObject);
                }
            }
            
        } catch (IOException e) {
            
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
            
        } catch(ClassNotFoundException e){
            
            System.err.println("Couldn't interpret the read object.");
            System.exit(1);
            
        }
        
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
        }
        
    }
    
    public void sendObject(Object obj){
        outputQueue.add(obj);
    }
    
    public boolean listening(){
        return listening;
    }
    
}
