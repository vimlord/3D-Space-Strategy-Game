/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;
import java.net.*;
import threads.ListenerThread;

/**
 *
 * @author Christopher
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int portNumber = 25565;
        
        boolean running = true;
        
        try(ServerSocket serverSocket = new ServerSocket(portNumber)){
            
            while(running){
                new ListenerThread(serverSocket.accept()).start();
                CycleRunner.executeCycle();
            }
            
            
        } catch(IOException e){
            System.out.println("Exception caught while trying to listen on port " + portNumber);
            System.out.println(e.getMessage());
        }
        
        
    }
    
}
