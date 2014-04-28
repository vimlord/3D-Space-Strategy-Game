/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameMechanics;

import entities.Entity;
import entities.ships.Ship;
import java.util.ArrayList;
import main.EntityList;

/**
 *
 * @author Christopher
 */
public class Faction {
    private static int factionsGenerated = 0;
    
    private final int factionID;
    private final String factionTag, factionName;
    
    private ArrayList<Long> ownedEntities = new ArrayList<>();
    
    public Faction(String tag){
        factionID = factionsGenerated;
        factionTag = tag;
        
        //I will likely change this to something that'll help the lore of the game.
        //The idea I speak of is random team names.
        factionName = tag;
    }
    
    public Faction(String name, String tag){
        factionID = factionsGenerated;
        factionTag = tag;
        
        factionName = name;
    }
    
    public void addMember(Entity e){
        if(e instanceof FactionTag)
            ownedEntities.add(e.getID());
    }
    
    public Entity getEntity(int index){
        return EntityList.getEntity((ownedEntities.get(index)));
    }
    
}
