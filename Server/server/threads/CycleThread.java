/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.threads;

import engine.main.CycleRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.main.Server;

/**
 *
 * @author Christopher Hittner
 */
public class CycleThread extends Thread{
    
    
    public CycleThread() {
        
    }
    
    public void run(){
        int count = 0;
        while(true){
            CycleRunner.executeCycle();
            count++;
            try{
                if(count%32 == 0){
                    Thread.sleep(0,1);
                    count = count%32;
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            Server.cycleClock();
            Server.getGUI().redraw();
        }
    }
}
