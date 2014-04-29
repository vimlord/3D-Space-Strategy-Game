/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameMechanics.factions;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class FactionList {
    private static ArrayList<Faction> factions = new ArrayList<>();
    private static int factionsGenerated = 0;
    
    public static void addFaction(String tag){
        factions.add(new Faction(tag, factionsGenerated));
    }
    
    public static void addFaction(String tag, String name){
        factions.add(new Faction(tag, name, factionsGenerated));
    }
    
    public static Faction getFaction(int ID){
        for(Faction f : factions){
            if(f.getID() == ID){
                return f;
            }
        }
        return null;
    }
    public static Faction getFaction(String tag){
        for(Faction f : factions){
            if(f.getTag() == tag){
                return f;
            }
        }
        return null;
    }
    
}
