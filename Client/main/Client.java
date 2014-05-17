/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static BufferedReader stdIn;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outputStream;
    
    private static ArrayList<Object> outputQueue = new ArrayList<>();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String hostName;
        int portNumber;
        
        //Attempts to set values for the host name and the port number
        try {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        } catch(Exception e){
            System.out.println("An error occured while reading from console:");
            System.err.println(e.toString());
            System.out.println("Usage: Server <hostName> <portNumber>");
            
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
        
        try {
            
            while(true){
                Object input = inStream.readObject();
                if(outputQueue.size() > 0){
                    for(Object obj: outputQueue)
                        outputStream.writeObject(obj);
                    outputQueue = new ArrayList<>();
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
    
    public static void sendObject(Object obj){
        outputQueue.add(obj);
    }
    
}
