/*
 * This class will allow for fleets to be placed into maps. Formations will be
 * structured as follows:
 * 1 Cruiser
 * 2 Destroyers
 * 4 Frigates
 * 6 Corvettes
 */

package engine.entities.ships.shipTools;

import engine.entities.ships.*;
import engine.gameMechanics.factions.*;
import java.util.*;

/**
 *
 * @author Christopher Hittner
 */
public class Formation {
    
    public static ArrayList<Ship> getFormation(){
        return getFormation(0);
    }
    
    public static ArrayList<Ship> getFormation(int modifier){
        return Alpha(0,0,modifier);
        
    }
    
    public static ArrayList<Ship> getFormation(int modifier, Faction f){
        return getFormation(modifier, f.getID());
    }
    
    public static ArrayList<Ship> getFormation(int modifier, int factionID){
        ArrayList<Ship> ships = getFormation(modifier);
        
        //Adds every ship to the Faction
        for(Ship s : ships){
            FactionList.getFaction(factionID).addMember(s);
        }
        
        return ships;
    }
    
    public static ArrayList<Ship> Alpha(double XZ_ROT, double Y_ROT){
        return Alpha(XZ_ROT,Y_ROT,0);
    }
    
    public static ArrayList<Ship> Alpha(double ROT_XZ, double ROT_Y, int modifier){
        ArrayList<Ship> list = new ArrayList<>();
        //Adds the Cruiser
        list.add(new Cruiser(0,0,0,modifier));
        
        //The Two Destroyers
        list.add(new Destroyer(200 * Math.sin(ROT_XZ),0,200 * Math.cos(ROT_XZ),modifier));
        list.add(new Destroyer(-200 * Math.sin(ROT_XZ),0,-200 * Math.cos(ROT_XZ),modifier));
        
        //The Four Frigates
        list.add(new Frigate( 200 * Math.cos(ROT_XZ) * Math.cos(ROT_Y),  200 * Math.sin(ROT_Y),  200 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),modifier));
        list.add(new Frigate(-200 * Math.cos(ROT_XZ) * Math.cos(ROT_Y), -200 * Math.sin(ROT_Y), -200 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),modifier));
        list.add(new Frigate(360 * Math.sin(ROT_XZ),0,360 * Math.cos(ROT_XZ),0));
        list.add(new Frigate(-360 * Math.sin(ROT_XZ),0,-360 * Math.cos(ROT_XZ),0));
        
        //The Six Corvettes
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y)                         ,   500 * Math.sin(ROT_Y)                        ,  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),modifier));
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y) ,   500 * Math.sin(ROT_Y) + 100 * Math.cos(ROT_Y),  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y),modifier));
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y) ,   500 * Math.sin(ROT_Y) - 100 * Math.cos(ROT_Y),  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y),modifier));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y)                         ,  -500 * Math.sin(ROT_Y)                        , -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),modifier));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y) ,  -500 * Math.sin(ROT_Y) + 100 * Math.cos(ROT_Y), -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y),modifier));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y ),  -500 * Math.sin(ROT_Y) - 100 * Math.cos(ROT_Y), -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y),modifier));
        
        return list;
    }
}
