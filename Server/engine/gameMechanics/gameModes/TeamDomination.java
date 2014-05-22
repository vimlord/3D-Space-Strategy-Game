/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gameMechanics.gameModes;

/**
 *
 * @author Christopher
 */
public class TeamDomination extends TeamDeathmatch{
    final int warpGates;
    
    /**
     * Creates a Team Domination game
     * @param factions
     * @param map
     * @param gates The number of Warp Gate objects to spawn in
     */
    public TeamDomination(int[][] factions, int map, int gates) {
        super(factions, map);
        warpGates = gates;
    }
    
    /**
     * Creates a Team Domination game
     * @param factions
     * @param gates The number of Warp Gate objects
     */
    public TeamDomination(int[][] factions, int gates) {
        super(factions);
        warpGates = gates;
    }
}
