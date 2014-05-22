/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gameMechanics.gameModes;

import engine.gameMechanics.factions.Faction;
import engine.gameMechanics.factions.FactionList;
import java.util.ArrayList;
import engine.main.EntityList;

/**
 *
 * @author Christopher
 */
public class TeamDeathmatch extends GameMode{
    
    /**
     *
     * @param factions The list of factions, where the factions are sorted by team
     * @param map
     */
    public TeamDeathmatch(int[][] factions, int map){
        super(factions,map);
    }
    
    public TeamDeathmatch(int[][] factions) {
        super(factions);
    }
    
    
    @Override
    public boolean testVictory() {
        if(testSurvivingTeams() < 2){
            determineWinner();
            return true;
        } else {
            return false;
        }
    }
    
    protected void determineWinner(){
        if(survivors.size() == 1){
            winner = survivors.get(0);
        } else {
            winner = -1;
        }
    }
    
}
