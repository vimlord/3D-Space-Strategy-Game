/*
 * This class stores data for a game mode. This will track stats for how a
 * series of Factions are doing.
 *
 * I want to have the following gamemodes:
    Free-for-all
    Team Deathmatch
    
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
    
    protected int winner = -1;
    
    private int[] involvedFactions;
    protected ArrayList<Integer> survivors = new ArrayList<>();
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
        survivors = new ArrayList<>();
        for(Faction f : factions){
            ArrayList<Long> members = f.getMembers();
            for(long l : members){
                if(EntityList.getEntity(l) != null){
                    survivors.add(f.getID());
                    break;
                }
            }
        }
        return survivors.size();
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
