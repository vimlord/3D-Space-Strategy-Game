/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import java.io.*;
import java.net.*;

/**
 *
 * @author Christopher
 */
public class ListenerThread extends Thread{
    private Socket socket = null;
    
    
    public ListenerThread(Socket socket) {
        super("ListenerThread");
        this.socket = socket;
    }
    
    public void run(){
        
        try(
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            ){
            
            String input = null;
            
            while((input = in.readLine()) != null){
                processInput(input);
                if(input.equals("[EXIT]")){
                    break;
                }
                
            }
            
            socket.close();
            
        } catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void processInput(String input){
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
            
        } else if(alphaTag.equals("[EXIT]")){
            return;
        }
    }
    
}
