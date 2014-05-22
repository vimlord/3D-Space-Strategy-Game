/*
 * This class stores data for a game mode. This will track stats for how a
 * series of Factions are doing.
 *
 * I want to have the following gamemodes:
    Free-for-all
    Team Deathmatch
    
 */

package engine.gameMechanics.gameModes;

import engine.gameMechanics.factions.*;
import java.util.ArrayList;
import engine.main.*;
import java.io.Serializable;

/**
 *
 * @author Christopher
 */
public abstract class GameMode  implements Serializable{
    private boolean running = false;
    
    protected int winner = -1;
    
    protected int[][] involvedFactions;
    protected ArrayList<Integer> survivors = new ArrayList<>();
    private int mapNumber;
    
    public GameMode(int[][] factions, int map){
        involvedFactions = factions;
        mapNumber = map;
    }
    
    public GameMode(int factions[][]){
        involvedFactions = factions;
        mapNumber = 0;
    }
    
    
    public void startGame(){
        for(int x = 0; x < involvedFactions.length; x++){
            for(int y = 0; y < involvedFactions[x].length; y++)
                //If it isn't a real Faction, it doesn't start
                if(FactionList.getFaction(involvedFactions[x][y]) == null){
                    return;
                }
        }
        
        running = true;
        
        winner = -1;
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
        ArrayList<Faction> factions = new ArrayList<>();
        for(int x = 0; x < involvedFactions.length; x++){
            for(int y = 0; y < involvedFactions[x].length; y++){
                factions.add(FactionList.getFaction(involvedFactions[x][y]));
            }
        }
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
    
    public int testSurvivingTeams(){
        int count = 0;
        survivors = new ArrayList<>();
        for (int x = 0; x < involvedFactions.length; x++) {
            for (int y = 0; y < involvedFactions[x].length; y++) {
                Faction f = FactionList.getFaction(involvedFactions[x][y]);
                ArrayList<Long> members = f.getMembers();
                boolean teamLiving = false;
                for(long l : members){
                    if(EntityList.getEntity(l) != null){
                        survivors.add(x);
                        teamLiving = true;
                        break;
                    }
                }
                if(teamLiving){
                    break;
                }
            }
        }
        return count;
    }
    
    protected abstract void determineWinner();
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
    public void addFaction(int ID){
        for(int x = 0; x < involvedFactions.length; x++){
            for(int y = 0; y < involvedFactions[x].length; y++)
                if(involvedFactions[x][y] == -1){
                involvedFactions[x][y] = ID;
                return;
                } else if(involvedFactions[x][y] == ID){
                    return;
                }
        }
    }
    public void removeFaction(int ID){
        for(int x = 0; x < involvedFactions.length; x++){
            for(int y = 0; y < involvedFactions[x].length; y++)
                if(involvedFactions[x][y] == ID){
                    involvedFactions[x][y] = -1;
                    return;
            }
        }
    }
            
}
