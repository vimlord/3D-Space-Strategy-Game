/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.threads;

import engine.gameMechanics.gameModes.FreeForAll;
import engine.main.CycleRunner;
import java.util.ArrayList;
import server.main.Server;
import server.players.Player;
import server.players.PlayerList;

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
            
            try{
                if(!CycleRunner.getGamemode().getStatus() || CycleRunner.getGamemode() == null){
                    resetGame();
                }
            } catch(NullPointerException n){
                resetGame();
            }
            
            
            
            
            if(CycleRunner.getGamemode().getStatus()){
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
            } else {
                ArrayList<Player> players = PlayerList.getPlayerList();
                boolean allReady = true;
                for(Player p : players){
                    if(!p.getReadiness())
                        allReady = false;
                }
                if(allReady && players.size() > 1){
                    resetGame();
                    CycleRunner.getGamemode().startGame();
                }
                
            }
            
            /*if(CycleRunner.getGamemode().getStatus()){
                //Starts the game
                ArrayList<ListenerThread> listeners = Server.getConnections();
                for(ListenerThread l : listeners){
                    l.sendObject("[GAMESTATUS]STARTING");
                }
                
                
            }*/
            
            Server.getGUI().redraw();
            
        }
    }
    
    public void resetGame(){
        ArrayList<Player> players = PlayerList.getPlayerList();
        int[] factions = new int[players.size()];

        for(int i = 0; i < factions.length; i++)
            factions[i] = players.get(i).getFactionID();


        CycleRunner.setGamemode(new FreeForAll(factions));
    }
    
}
