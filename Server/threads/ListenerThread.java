/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christopher
 */
public class ListenerThread extends Thread{
    private Socket socket = null;
    
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream inStream;
    private ObjectOutputStream outputStream;
    
    
    
    public ListenerThread(Socket socket) {
        super("ListenerThread");
        this.socket = socket;
    }
    
    public void run(){
        
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
        try(
                BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ){
            
            Object input = null;
            
            //This next line of code takes an Object as input. The Object will
            //then be tested to see if it matches any of the known Object types.
            while((input = inStream.readObject()) != null){
                if(input instanceof String){
                    String str = (String) input;
                    processStringInput(str);
                }
                if(input.equals("[EXIT]")){
                    break;
                }
                
            }
            
            socket.close();
            
        } catch(IOException e){
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
    }
    
    public void processStringInput(String input){
        //Makes sure the Alpha tag is correct
        if(!input.substring(0,1).equals("[")){
            return;
        }
        String alphaTag = input.substring(0,input.indexOf("]"));
        
        if(alphaTag.equals("[ORDER]")){
            String order = input.substring(alphaTag.length());
            String betaTag = input.substring(0,order.indexOf("]"));
            order = order.substring(betaTag.length());
            
            int faction = Integer.parseInt(betaTag.substring(betaTag.indexOf("(") + 1, betaTag.indexOf(")")));
            
            long ship = Long.parseLong(betaTag.substring(betaTag.indexOf("(", betaTag.indexOf(")")) + 1, betaTag.indexOf(")",betaTag.indexOf(")")+1)));
            
            new OrderThread(faction, ship, order).start();
            
        } else if(alphaTag.equals("[SEND]")){
            String order = input.substring(alphaTag.length());
            String betaTag = input.substring(0,order.indexOf("]"));
            
            if(betaTag.equals("[ENTITY_LIST]")){
                //When the required code is added, have some code here to send the
                //list of Entities to the client.
            }
        }
        
        if(alphaTag.equals("[EXIT]")){
            return;
        }
    }
    
}
