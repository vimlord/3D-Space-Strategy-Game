/*
 * This class stores data for a Domination game. In this game, players compete for
 * control of several Warp Gates.
 */

package engine.gameMechanics.gameModes;


/**
 *
 * @author Christopher
 */
public class Domination extends FreeForAll{

    final int warpGates;
    
    /**
     * Creates a Domination game
     * @param factions
     * @param map
     * @param gates The number of Warp Gate objects to spawn in
     */
    public Domination(int[] factions, int map, int gates) {
        super(factions, map);
        warpGates = gates;
    }
    
    /**
     * Creates a Domination game
     * @param factions
     * @param gates The number of Warp Gate objects
     */
    public Domination(int[] factions, int gates) {
        super(factions);
        warpGates = gates;
    }
    
    
    
}
