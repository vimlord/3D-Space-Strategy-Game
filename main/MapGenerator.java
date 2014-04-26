/*
 * The MapGenerator class creates an arrangement of Entity objects based on the needs
 * of the program. It is meant to generate map layouts, Ship positions, etc.
 * The code can generate maps, which are listed in a table below with their ID number
 *  
 * ID Number  |Map Name        |Description
 * -----------+----------------+-----------------------------------------
 *    0       |Empty Map       |An empty map with nothing in it.
 *    1       |Earth-Moon      |A 1:1 scale model of Earth and its moon.
 *    2       |Solar System    |A 1:1 scale version of the Solar System
 * 
 */

package main;

import entities.Entity;
import entities.celestialBodies.*;
import java.util.ArrayList;
import physics.*;


/**
 *
 * @author Christopher Hittner
 */
public class MapGenerator {
    
    /**
     * Generates the map with the ID chosen with only the desired CelestialBody objects.
     * @param ID
     * @return
     */
    public static ArrayList<CelestialBody> generateMap(int ID){
        if(ID == 0){
            return new ArrayList<>();
        } else if(ID == 1){
            //This list will store the Earth-Moon System
            ArrayList<CelestialBody> list = new ArrayList<>();
            //Adds the Earth
            list.add(new Planet(0,0,0,5.97219 * Math.pow(10,24), 6378100,1,100000));
            //Adds the Moon
            list.add(new Planet(0,0,0,7.34767309 * Math.pow(10,22),1737400));
            //Puts the Moon in orbit around Earth
            list.get(1).putIntoOrbit(new Orbit(384399000,Math.toRadians(5.145),0,0, list.get(0)), list.get(0));
            return list;
        } else if(ID == 2){
            //This list stores the entire solar system.
            ArrayList<CelestialBody> list = new ArrayList<>();
            //Adds the Sun
            list.add(new Star(0, 0, 0, 1.98855 * Math.pow(10,30), 696342000));
            //Adds Mercury
            list.add(new Planet(0, 0, 0, 3.3022 * Math.pow(10,22), 2439700));
            list.get(1).putIntoOrbit(new Orbit(57909050000.0,Math.toRadians(7.005),Math.toRadians(48.331),Math.toRadians(174.796),list.get(0)),list.get(0));
            //Adds Venus
            list.add(new Planet(0, 0, 0, 4.6876 * Math.pow(10,24), 6051800));
            list.get(2).putIntoOrbit(new Orbit(108208000000.0,Math.toRadians(3.86),Math.toRadians(76.678),Math.toRadians(50.115),list.get(0)),list.get(0));
            //Adds Earth
            
            //Adds Mars
            
            //Adds Jupiter
            
            //Adds Saturn
            
            //Adds Uranus
            
            //Adds Neptune
            
            
            
        }
        return null;
    }
}
