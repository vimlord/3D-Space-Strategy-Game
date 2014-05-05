/*
 * This class stores the data required for a free-for-all gametype
 */

package gameMechanics.gameModes;

/**
 *
 * @author Christopher
 */
public class FreeForAll extends GameMode {
    public FreeForAll(int factions, int map){
        super(factions,map);
    }
    public FreeForAll(int factions){
        super(factions);
    }

    @Override
    public boolean testVictory() {
        if(testSurvivors() < 2){
            determineWinner();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * For all free-for-all gametypes, the winning player is determined by who the
     * last person to have surviving units is. If the last two people lose at the
     * exact same time, everyone loses.
     */
    public void determineWinner(){
        if(survivors.size() == 1){
            winner = survivors.get(0);
        } else 
            winner = -1;
        
    }
}
