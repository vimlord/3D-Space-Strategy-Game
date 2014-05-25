/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.threads;

import engine.main.CycleRunner;
import server.main.Server;

/**
 *
 * @author Christopher Hittner
 */
public class GameThread extends Thread{
    
    
    public GameThread(){
        
    }
    
    public void run(){
        while(true){
            CycleRunner.executeCycle();
            Server.cycleClock();
        }
    }
}
