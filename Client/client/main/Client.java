/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.main;

import client.threads.ConnectionThread;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Client {
    
    
    
    private static ConnectionThread connection;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connection = new ConnectionThread(args);
        
        while(connection.listening()){
            
        }
        
    }
    
    public static ConnectionThread getConnection(){
        return connection;
    }
    
    
    
}
