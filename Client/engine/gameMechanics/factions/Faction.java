/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gameMechanics.factions;

import engine.gameMechanics.factions.FactionTag;
import engine.entities.Entity;
import engine.entities.ships.Ship;
import java.util.ArrayList;
import engine.main.EntityList;

/**
 *
 * @author Christopher
 */
public class Faction {
    
    
    private final int factionID;
    private final String factionTag, factionName;
    
    private ArrayList<Long> ownedEntities = new ArrayList<>();
    private ArrayList<Integer> allies = new ArrayList<>(), enemies = new ArrayList<>();
    
    public Faction(String tag, int ID){
        factionID = ID;
        factionTag = tag;
        
        //I will likely change this to something that'll help the lore of the game.
        //The idea I speak of is random team names.
        factionName = tag;
    }
    
    public Faction(String name, String tag, int ID){
        factionID = ID;
        factionTag = tag;
        
        factionName = name;
    }
    
    public void addMember(FactionTag f){
        ownedEntities.add(((Entity) f).getID());
        
        
    }
    
    public void removeMember(Entity e){
        if(e instanceof FactionTag){
            ownedEntities.remove(e.getID());
        }
    }
    
    public Entity getEntity(int index){
        return EntityList.getEntity((ownedEntities.get(index)));
    }
    
    public void adAlly(Faction f){
        addAlly(f.getID());
    }
    public void addAlly(int ID){
        for(int i = enemies.size() - 1; i >= 0; i++){
            if(enemies.get(i) == ID){
                enemies.remove(i);
            }
        }
        allies.add(ID);
    }
    
    public void addEnemy(Faction f){
        addEnemy(f.getID());
    }
    public void addEnemy(int ID){
        for(int i = allies.size() - 1; i >= 0; i++){
            if(allies.get(i) == ID){
                allies.remove(i);
            }
        }
        enemies.add(ID);
    }
    
    public int getDiplomaticStatus(Faction f){
        return getDiplomaticStatus(f.getID());
    }
    public int getDiplomaticStatus(int ID){
        for(int i : allies){
            if(i == ID){
                return 1;
            }
        } for(int i : enemies){
            if(i == ID){
                return -1;
            }
        }
        return 0;
    }
    
    public int getID(){
        return factionID;
    }
    public String getTag(){
        return factionTag;
    }
    public String getName(){
        return factionName;
    }
    public ArrayList<Long> getMembers(){
        return ownedEntities;
    }
    
}
