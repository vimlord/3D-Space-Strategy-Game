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
        for(Entity e : bodies){
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
	
	public static void executeCollisions(){
		//Planetary collisions
		for(int i = 0; i < bodies.size() - 1; i++){
			for(int j = i + 1; j < bodies.size(); j++){
				if((bodies.get(i)).testCollision(bodies.get(j))){
					(bodies.get(i)).collide(bodies.get(j));
				}
			}
		}
		
		//Vessel collisions
		for(int i = 0; i < ships.size() - 1; i++){
			for(int j = i + 1; j < ships.size(); j++){
				if((ships.get(i)).testCollision(ships.get(j))){
					(ships.get(i)).collide(ships.get(j));
				}
			}
		}
		
		//Planet-Ship Collisions
		for(CelestialBody c : bodies){
			for(Ship s : ships){
				if(c.testCollision(s)){
					c.collide(s);
				}
			}
		}
		
	}
	
     
    public static CelestialBody getCelestialBody(int index){
        return bodies.get(index);
    }
     
    public static Ship getShip(int index){
        return ships.get(index);
    }
    
    public static ArrayList<Entity> getEntityList(){
        ArrayList<Entity> list = new ArrayList<>();
        for(Ship s : ships){
            list.add(s);
        }
        for(CelestialBody c : bodies){
            list.add(c);
        }
        
        return list;
    }
     
     
}

