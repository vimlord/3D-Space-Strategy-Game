/*
 * This class stores data for a game mode. This will track stats for how a
 * series of Factions are doing.
 */

package gameMechanics.gameModes;

import gameMechanics.factions.*;
import java.util.ArrayList;
import main.*;

/**
 *
 * @author Christopher
 */
public abstract class GameMode {
    private boolean running = false;
    
    int winner = -1;
    
    private int[] involvedFactions;
    private int mapNumber;
    
    public GameMode(int factions, int map){
        involvedFactions = new int[factions];
        mapNumber = map;
    }
    
    public GameMode(int factions){
        involvedFactions = new int[factions];
        mapNumber = 0;
    }
    
    
    public void startGame(){
        running = true;
        
        winner = -1;
        
        FactionList.resetFactions();
        EntityList.resetList();
        EntityList.loadMap(mapNumber, false);
        
    }
    
    public void endGame(){
        running = false;
    }
    
    
    public void executeCycle(){
        if(!running)
            return;
    }
    
    /**
     *
     * @return Whether the game has been won
     */
    public abstract boolean testVictory();
    
    /**
     * Tests how many Factions still have members
     * @return
     */
    public int testSurvivors(){
        ArrayList<Faction> factions = FactionList.getFactionList();
        int living = 0;
        for(Faction f : factions){
            ArrayList<Long> members = f.getMembers();
            for(long l : members){
                if(EntityList.getEntity(l) != null){
                    living++;
                    //This will only remain if it is the only one with members.
                    //If it isn't, it will become null again.
                    winner = f.getID();
                    break;
                }
            }
        }
        if(living > 1){
            winner = -1;
        }
        return living;
    }
    
    /**
     * Returns the ID of the winning Faction, or -1 if there is no winner.
     * @return The winner's ID, or -1.
     */
    public int getWinner(){
        return winner;
    }
    public boolean getStatus(){
        return running;
    }
            
}
