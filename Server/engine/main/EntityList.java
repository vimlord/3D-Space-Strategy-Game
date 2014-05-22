package main;
 
import entities.*;
import entities.celestialBodies.*;
import entities.projectiles.*;
import entities.ships.Ship;
import entities.ships.shipTools.Formation;
import entities.structures.*;
import gameMechanics.generators.MapGenerator;
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
    private static ArrayList<Structure> structures = new ArrayList<>();
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
     * Adds a Structure object to the game
     * @param w The Structure object
     */
    public static void addStructure(Structure s){
        s.setID(valueToAssign);
        valueToAssign++;
        structures.add(s);
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
        //Simulates gravitation between structures and planets
        for(Entity e : bodies){
            for(Entity f : structures){
                e.gravitate(f);
            }
        }
        
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
        for(Structure s : structures){
            s.move();
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
            
            //Structure collisions
            for(Structure struct : structures){
                for(Ship s : ships){
                    if(struct.testCollision(s)){
                        struct.collide(s);
                    }
                }
            }
            
            //Planet Collisions
            for(CelestialBody c : bodies){
                for(Ship s : ships){
                    if(c.testCollision(s)){
                        c.collide(s);
                        if(c instanceof BlackHole){
                            bodies.remove(c);
                        }
                    }
                }
                for(Structure s : structures){
                    if(c.testCollision(s)){
                        c.collide(s);
                    }
                }
            }

            //Projectile collisions
            
            for(int i = projectiles.size() - 1; i >= 0; i++){
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
                
                for(Structure s : structures){
                    if(p.testCollision(s)){
                        p.collide(s);
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
    
    public static void executeStructure(){
        for(Structure s : structures){
            s.act();
        }
        executeWarpGates();
    }
    
    public static void executeWarpGates(){
        for(Structure st : structures){
            if(st instanceof WarpGate){
                WarpGate w = (WarpGate) st;
                for(Ship s : ships){
                    if(w.warpAllowed(s)){
                        w.sendShip(s);
                    }
                }
            }
        }
    }
    
     
    public static CelestialBody getCelestialBody(int index){
        return bodies.get(index);
    }
    public static CelestialBody getCelestialBody(long ID){
        for(CelestialBody e : bodies){
            if(e.getID() == ID)
                return e;
        }
        return null;
    }
     
    public static Ship getShip(int index){
        return ships.get(index);
    }
    public static Ship getShip(long ID){
        for(Ship e : ships){
            if(e.getID() == ID)
                return e;
        }
        return null;
    }
    
    public static Projectile getProjectile(int index){
        return projectiles.get(index);
    }
    public static Projectile getProjectile(long ID){
        for(Projectile e : projectiles){
            if(e.getID() == ID)
                return e;
        }
        return null;
    }
    
    public static Structure getStructure(int index){
        return structures.get(index);
    }
    public static Structure getStructure(long ID){
        for(Structure e : structures){
            if(e.getID() == ID){
                return e;
            }
        }
        return null;
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
        for(Entity e : structures){
            if(e.getID() == ID){
                return e;
            }
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
        for(Structure s : structures){
            list.add(s);
        }
        
        return list;
    }
    
    /**
     * Resets the list of CelestialBody objects
     */
    public static void resetMap(){
        bodies = new ArrayList<CelestialBody>();
    }
    
    public static void resetList(){
        resetMap();
        ships = new ArrayList<>();
        projectiles = new ArrayList<>();
        valueToAssign = 0;
    }
    
    /**
     *
     * @param ID The map ID
     * @param reset Whether or not the map should be reset if the requested map is available
     */
    public static void loadMap(int ID, boolean reset){
        //Grabs the map
        ArrayList<CelestialBody> newBodies = MapGenerator.generateMap(ID);
        //Grabs the ships
        ArrayList<Ship> newShips = MapGenerator.generateShips(ID, CycleRunner.getGamemode());
        
        //Error check
        if(newBodies == null){
            return;
        }
        
        //Resets CelestialBody list if needed
        if(reset){
            resetList();
        }
        
        //Adds the CelestialBody objects
        for(CelestialBody c : newBodies){
            bodies.add(c);
        }
        //Adds the Ship objects
        for(Ship s : newShips){
            ships.add(s);
        }
        
    }
    
    public static void loadFormation(int ID, boolean reset){
        if(reset){
            ships = new ArrayList<>();
        }
        
        ArrayList<Ship> list = Formation.getFormation();
        
        for(Ship s : list){
            ships.add(s);
        }
    }
    
}

