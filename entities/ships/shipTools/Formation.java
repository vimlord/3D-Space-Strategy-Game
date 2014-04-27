/*
 * This class will allow for fleets to be placed into maps. Formations will be
 * structured as follows:
 * 1 Cruiser
 * 2 Destroyers
 * 4 Frigates
 * 6 Corvettes
 */

package entities.ships.shipTools;

import entities.ships.*;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class Formation {
    
    public static ArrayList<Ship> getFormation(int ID){
        if(ID == 0){
            return Alpha(0,0);
        }
        return null;
        
    }
    
    public static ArrayList<Ship> Alpha(double ROT_XZ, double ROT_Y){
        ArrayList<Ship> list = new ArrayList<>();
        //Adds the Cruiser
        list.add(new Cruiser(0,0,0,0));
        
        //The Two Destroyers
        list.add(new Destroyer(200 * Math.sin(ROT_XZ),0,200 * Math.cos(ROT_XZ),0));
        list.add(new Destroyer(-200 * Math.sin(ROT_XZ),0,-200 * Math.cos(ROT_XZ),0));
        
        //The Four Frigates
        list.add(new Frigate( 200 * Math.cos(ROT_XZ) * Math.cos(ROT_Y),  200 * Math.sin(ROT_Y),  200 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),0));
        list.add(new Frigate(-200 * Math.cos(ROT_XZ) * Math.cos(ROT_Y), -200 * Math.sin(ROT_Y), -200 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),0));
        list.add(new Frigate(360 * Math.sin(ROT_XZ),0,360 * Math.cos(ROT_XZ),0));
        list.add(new Frigate(-360 * Math.sin(ROT_XZ),0,-360 * Math.cos(ROT_XZ),0));
        
        //The Six Corvettes
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y)                         ,   500 * Math.sin(ROT_Y)                        ,  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),0));
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y) ,   500 * Math.sin(ROT_Y) + 100 * Math.cos(ROT_Y),  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y),0));
        list.add(new Corvette( 500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y) ,   500 * Math.sin(ROT_Y) - 100 * Math.cos(ROT_Y),  500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y),0));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y)                         ,  -500 * Math.sin(ROT_Y)                        , -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y),0));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y) ,  -500 * Math.sin(ROT_Y) + 100 * Math.cos(ROT_Y), -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) + 100 * Math.sin(ROT_Y),0));
        list.add(new Corvette(-500 * Math.cos(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y ),  -500 * Math.sin(ROT_Y) - 100 * Math.cos(ROT_Y), -500 * Math.sin(ROT_XZ) * Math.cos(ROT_Y) - 100 * Math.sin(ROT_Y),0));
        
        return list;
    }
}
