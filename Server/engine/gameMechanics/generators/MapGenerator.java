/*
 * The MapGenerator class creates an arrangement of Entity objects based on the needs
 * of the program. It is meant to generate map layouts, Ship positions, etc.
 * The code can generate maps, which are listed in a table below with their ID number
 *  
 * ID Number  |Map Name           |Description
 * -----------+-------------------+-----------------------------------------
 *    0       |Empty Map          |An empty map with nothing in it.
 *    1       |Earth-Moon         |A 1:1 scale model of Earth and its moon.
 *    2       |Solar System       |A 1:1 scale version of the Solar System
 *    3       |Binary Star System |A pair of stars with a planet orbiting each one.
 *    4       |Black Hole System  |A star (possibly two) orbiting a black hole. May or may not contain planets
 * 
 */

package engine.gameMechanics.generators;

import engine.entities.Entity;
import engine.entities.celestialBodies.*;
import engine.entities.ships.Ship;
import engine.entities.ships.shipTools.Formation;
import engine.gameMechanics.factions.Faction;
import engine.gameMechanics.factions.FactionList;
import engine.gameMechanics.gameModes.GameMode;
import java.util.ArrayList;
import engine.physics.*;


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
            list.add(new Planet(0,0,0,5.97219 * Math.pow(10,24), 6378100,1.225,100000));
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
            list.get(1).putIntoOrbit(new Orbit(57909050000.0, Math.toRadians(7.005), Math.toRadians(48.331), Math.toRadians(174.796), list.get(0)),list.get(0));
            //Adds Venus
            list.add(new Planet(0, 0, 0, 4.6876 * Math.pow(10,24), 6051800));
            list.get(2).putIntoOrbit(new Orbit(108208000000.0,Math.toRadians(3.86),Math.toRadians(76.678),Math.toRadians(50.115),list.get(0)),list.get(0));
            //Adds the Earth
            list.add(new Planet(0,0,0,5.97219 * Math.pow(10,24), 6378100,1.225,100000));
            list.get(3).putIntoOrbit(new Orbit(149598261000.0, Math.toRadians(7.155), Math.toRadians(348.73936), Math.toRadians(357.51716), list.get(0)),list.get(0));
            //Adds the Moon
            list.add(new Planet(0,0,0,7.34767309 * Math.pow(10,22),1737400));
            list.get(4).putIntoOrbit(new Orbit(384399000,Math.toRadians(5.145),0,0, list.get(3)), list.get(3));
            //Adds Mars
            list.add(new Planet(0,0,0,6.4185 * Math.pow(10,23), 3386200,0.02,200000));
            list.get(5).putIntoOrbit(new Orbit(227939100000.0, Math.toRadians(5.65), Math.toRadians(49.562), Math.toRadians(19.3564), list.get(0)),list.get(0));
            //Adds Jupiter
            list.add(new Planet(0,0,0,1.8983 * Math.pow(10,27), 69111000,1326,500000));
            list.get(6).putIntoOrbit(new Orbit(778547200000.0, Math.toRadians(6.09), Math.toRadians(100.492), Math.toRadians(18.818), list.get(0)),list.get(0));
            //Adds Saturn
            list.add(new Planet(0,0,0,5.6846 * Math.pow(10,26), 58232000,0.19,1000000));
            list.get(7).putIntoOrbit(new Orbit(1433449370000.0, Math.toRadians(5.51), Math.toRadians(113.642811), Math.toRadians(320.34675), list.get(0)),list.get(0));
            //Adds Uranus
            list.add(new Planet(0,0,0,8.6810 * Math.pow(10,25), 25362000,0.42,250000));
            list.get(8).putIntoOrbit(new Orbit(2876679082000.0, Math.toRadians(6.48), Math.toRadians(73.989821), Math.toRadians(142.955717), list.get(0)),list.get(0));
            //Adds Neptune
            list.add(new Planet(0,0,0,1.0243 * Math.pow(10,26), 24622000,0.45,200000));
            list.get(9).putIntoOrbit(new Orbit(2876679082000.0, Math.toRadians(6.43), Math.toRadians(131.79431), Math.toRadians(267.767281), list.get(0)),list.get(0));
            
            return list;
        } else if(ID == 3){
            //This list stores a binary pair of stars with a planet around each.
            ArrayList<CelestialBody> list = new ArrayList<>();
            
            //Adds the first star
            list.add(new Star(5000000000.0,0,0,1.49 * Math.pow(10,30), 560000000));
            list.get(0).setSpeedX(7.911304);
            
            //Adds the second star
            list.add(new Star(-5000000000.0,0,0,1.4907 * Math.pow(10,30), 560000000));
            list.get(1).setSpeedX(-7.911304);
            
            return list;
        } else if(ID == 4){
            //This list stores a black hole with stuff orbiting it.
            ArrayList<CelestialBody> list = new ArrayList<>();
            
            //Adds the promised Black Hole
            list.add(new BlackHole(0,0,0,15000));
            
            //Adds a Star
            list.add(new Star(0,0,0,Math.pow(10,30),6 * Math.pow(10,6)));
            list.get(1).putIntoOrbit(new Orbit(20000000,0,0,0,list.get(0)), list.get(0));
            
            //Adds another star
            list.add(new Star(0,0,0,Math.pow(10,30),6 * Math.pow(10,6)));
            list.get(2).putIntoOrbit(new Orbit(20000000,0,Math.toRadians(180),0,list.get(0)), list.get(0));
            
            return list;
        }
        return null;
    }

    public static ArrayList<Ship> generateShips(int ID, GameMode gm) {
        ArrayList<Ship> ships = new ArrayList<Ship>();
        int numFactions = FactionList.getFactionList().size();
        ArrayList<Faction> fact = FactionList.getFactionList();
        if(ID == 0){
            double angle = 2 * Math.PI / numFactions;
            double magnitude = 5000 * numFactions;
            for(int i = 0; i < numFactions; i++){
                ArrayList<Ship> newShips = Formation.getFormation(0, fact.get(i));
                for(Ship s : newShips){System.out.println(s);
                    s.setX(s.getX() + magnitude * Math.cos(angle * i));
                    s.setZ(s.getZ() + magnitude * Math.sin(angle * i));
                    ships.add(s);
                }
            }
        } else if(ID == 1){
            double angle = 2 * Math.PI / numFactions;
            
            Entity orbited = new Planet(0,0,0,5.97219 * Math.pow(10,24), 6378100,1.225,100000);
            
            for(int i = 0; i < numFactions; i++){
                ArrayList<Ship> newShips = Formation.getFormation(0, fact.get(i));
                for(Ship s : newShips){
                    s.putIntoOrbit(new Orbit(7000000,0,0,angle * i,orbited), orbited);
                    ships.add(s);
                }
            }
        } else if(ID == 2){
            double angle = 2 * Math.PI / numFactions;
            
            Entity orbited = new Planet(0,0,0,5.97219 * Math.pow(10,24), 6378100,1.225,100000);
            orbited.putIntoOrbit(new Orbit(149598261000.0, Math.toRadians(7.155), Math.toRadians(348.73936), Math.toRadians(357.51716), new Star(0, 0, 0, 1.98855 * Math.pow(10,30), 696342000)),new Star(0, 0, 0, 1.98855 * Math.pow(10,30), 696342000));
            
            for(int i = 0; i < numFactions; i++){
                ArrayList<Ship> newShips = Formation.getFormation(0, fact.get(i));
                for(Ship s : newShips){
                    s.putIntoOrbit(new Orbit(7000000,0,0,angle * i,orbited), orbited);
                    ships.add(s);
                }
            }
        } else if(ID == 3){
            double angle = 2 * Math.PI / numFactions;
            
            Entity orbited = new Star(5000000000.0,0,0,1.49 * Math.pow(10,30), 560000000);
            orbited.setSpeedX(7.911304);
            
            for(int i = 0; i < numFactions; i++){
                ArrayList<Ship> newShips = Formation.getFormation(0, fact.get(i));
                for(Ship s : newShips){
                    s.putIntoOrbit(new Orbit(7000000,0,0,angle * i,orbited), orbited);
                    ships.add(s);
                }
            }
        } else if(ID == 4){
            double angle = 2 * Math.PI / numFactions;
            
            Entity orbited = new Star(0,0,0,Math.pow(10,30),6 * Math.pow(10,6));
            orbited.putIntoOrbit(new Orbit(20000000,0,0,0,new BlackHole(0,0,0,15000)), new BlackHole(0,0,0,15000));
            
            for(int i = 0; i < numFactions; i++){
                ArrayList<Ship> newShips = Formation.getFormation(0, fact.get(i));
                for(Ship s : newShips){
                    s.putIntoOrbit(new Orbit(7000000,0,0,angle * i,orbited), orbited);
                    ships.add(s);
                }
            }
        }
        
        
        return ships;
    }
    
    
}
