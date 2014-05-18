/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.threads;

import client.object_wrappers.EntityListWrapper;
import engine.entities.Entity;
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
        } catch(Exception e){
            System.out.println("An error occured while reading from console:");
            System.err.println(e.toString());
            System.out.println("Usage: Client <hostName> <portNumber>");
            
            hostName = "localhost";
            portNumber = 25565;
            
            System.out.println("The host name has been set to \"" + hostName + "\".");
            System.out.println("The port number has been set to \"" + portNumber + "\"." + "\n");
        }
        
        try{
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            inStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        
        
        
    }
    
    @Override
    public void run(){
        
        listening = true;
        
        try {
            
            while(listening){
                Object input = inStream.readObject();
                processInput(input);
                if(outputQueue.size() > 0){
                    outputStream.writeObject(outputQueue.remove(0));
                }
            }
            
        } catch (UnknownHostException e) {
            
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
            
        } catch (IOException e) {
            
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
            
        } catch(ClassNotFoundException e){
            
            System.err.println("Couldn't interpret the read object.");
            System.exit(1);
            
        }
        
    }
    
    public void processInput(Object obj){
        if(obj instanceof EntityListWrapper){
            
            EntityListWrapper w = (EntityListWrapper) obj;
            EntityList.setList(w.getContents());
            
        } else if(obj instanceof GameMode){
            
            CycleRunner.setGamemode((GameMode) obj);
            
        }
    }
    
    public void sendObject(Object obj){
        outputQueue.add(obj);
    }
    
    public boolean listening(){
        return listening;
    }
    
}
