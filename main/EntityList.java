package main;
 
import entities.*;
import entities.planetaryBodies.CelestialBody;
import entities.ships.Ship;
import java.util.ArrayList;
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
/**
 *
 * @author Christopher Hittner
 */
public class EntityList {
    private static ArrayList<CelestialBody> bodies = new ArrayList<>();
    private static ArrayList<Ship> ships = new ArrayList<>();
     
    public static void addCelestialBody(CelestialBody c){
        bodies.add(c);
    }
     
    public static void addShip(Ship s){
        ships.add(s);
    }
     
    /**
     * Simulates gravity for every Entity that exists
     */
    public static void executeGravity(){
        //Simulates gravitation between ships and planets
        for(Entity e : ships){
            for(Entity f : bodies){
                if(!e.equals(f)){
                    e.gravitate(f);
                }
            }
        }
    }
     
    /**
     * Causes all Entity objects to move based on their velocities
     */
    public static void executeMovement(){
        for(Ship s : ships){
            s.move();
        }
        for(CelestialBody c : bodies){
            c.move();
        }
    }
     
    public static CelestialBody getCelestialBody(int index){
        return bodies.get(index);
    }
     
    public static Ship getShip(int index){
        return ships.get(index);
    }
     
     
}

