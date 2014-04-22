package main;
 
import entities.*;
import entities.celestialBodies.BlackHole;
import entities.celestialBodies.CelestialBody;
import entities.projectiles.*;
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
    private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private static long valueToAssign = 0;
     
    /**
     * Adds a CelestialBody object to the game
     * @param c The CelestialBody object
     */
    public static void addCelestialBody(CelestialBody c){
        c.setID(valueToAssign);
        valueToAssign++;
        bodies.add(c);
    }
    
    /**
     * Adds a Ship object to the game
     * @param s The Ship object
     */
    public static void addShip(Ship s){
        s.setID(valueToAssign);
        valueToAssign++;
        ships.add(s);
    }
    
    /**
     * Adds a Projectile object to the game
     * @param p The Projectile object
     */
    public static void addProjectile(Projectile p){
        p.setID(valueToAssign);
        valueToAssign++;
        projectiles.add(p);
    }
     
    /**
     * Simulates gravity for every Entity that exists
     */
    public static void executeGravity(){
        //Simulates gravitation between ships and planets
        for(Entity e : ships){
            for(Entity f : bodies){
                e.gravitate(f);
            }
        }
        
        
        for(Entity e : bodies){
            for(Entity f : bodies){
                if(!e.equals(f)){
                    e.gravitate(f);
                }
            }
        }
        
        
        for(Entity p : projectiles){
            for(Entity e : bodies){
                p.gravitate(e);
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
        for(Projectile p : projectiles){
            p.move();
        }
    }
	
    public static void executeCollisions(){
            //Planetary collisions
            for(int i = 0; i < bodies.size() - 1; i++){
                    for(int j = i + 1; j < bodies.size(); j++){
                            if((bodies.get(i)).testCollision(bodies.get(j))){
                                    (bodies.get(i)).collide(bodies.get(j));
                                    if(bodies.get(i) instanceof BlackHole){
                                        bodies.remove(j);
                                        j--;
                                    }
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
                        if(c instanceof BlackHole){
                            bodies.remove(c);
                        }
                    }
                }
            }

            //Projectile collisions
            
            for(int i = 0; i < projectiles.size(); i++){
                Projectile p = projectiles.get(i);
                for(Ship s : ships){
                    if(p.testCollision(s)){
                        p.collide(s);
                        projectiles.remove(p);
                        break;
                    }
                }

                for(CelestialBody c : bodies){
                    if(p.testCollision(c)){
                        p.collide(c);
                        projectiles.remove(p);
                        break;
                    }
                }

            }
            
    }
    
    
    public static void executeCasualties(){
        //Kills dead ships because... they're dead... duh.
        for(int i = 0; i < ships.size(); i++){
            Ship s = ships.get(i);
            if(s.getHealth() < 0){
                ships.remove(s);
            }
        }
        for(int i = 0; i < projectiles.size(); i++){
            if(projectiles.get(i) instanceof Laser && projectiles.get(i).getDamage() < 0.02){
                projectiles.remove(i);
            }
        }
    }
    
     
    public static CelestialBody getCelestialBody(int index){
        return bodies.get(index);
    }
     
    public static Ship getShip(int index){
        return ships.get(index);
    }
    
    public static Projectile getProjectile(int index){
        return projectiles.get(index);
    }
    
    public static Entity getEntity(long ID){
        for(Entity e : bodies){
            if(e.getID() == ID)
                return e;
        }
        for(Entity e : ships){
            if(e.getID() == ID)
                return e;
        }
        for(Entity e : projectiles){
            if(e.getID() == ID)
                return e;
        }
        return null;
    }

        
    public static ArrayList<Entity> getEntityList(){
        ArrayList<Entity> list = new ArrayList<>();
        for(Ship s : ships){
            list.add(s);
        }
        for(CelestialBody c : bodies){
            list.add(c);
        }
        for(int i = 0; i < projectiles.size(); i++){
            list.add(projectiles.get(i));
        }
        
        return list;
    }
     
     
}

