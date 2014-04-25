/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.celestialBodies;

import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class CelestialBodyArrangement {
    private ArrayList<CelestialBody> bodies;
    
    /**
     * Creates an empty arrangement of CelestialBody objects
     */
    public CelestialBodyArrangement(){
        bodies = new ArrayList<>();
    }
    
    /**
     * Creates an arrangement of CelestialBody objects based on existing data
     * @param list The list of CelestialBody objects
     */
    public CelestialBodyArrangement(ArrayList<CelestialBody> list){
        bodies = list;
    }
    
    /**
     * Creates an arrangement of CelestialBody objects with a single CelestialBody
     * @param list The CelestialBody object
     */
    public CelestialBodyArrangement(CelestialBody c){
        bodies = new ArrayList<>();
        bodies.add(c);
    }
    
    /**
     * Adds a CelestialBody to the list
     * @param c
     */
    public void add(CelestialBody c){
        bodies.add(c);
    }
    
    /**
     * Adds a CelestialBody to the list at a specific index
     * @param index
     * @param c
     */
    public void add(int index, CelestialBody c){
        bodies.add(index,c);
    }
    
    /**
     * Adds a list of CelestialBody objects to the list
     * @param c
     */
    public void add(ArrayList<CelestialBody> c){
        for(CelestialBody body : c){
            bodies.add(body);
        }
    }
    
    /**
     * Adds a list of CelestialBody objects to the list at a specific index
     * @param index
     * @param c
     */
    public void add(int index, ArrayList<CelestialBody> c){
        for(CelestialBody body : c){
            bodies.add(index,body);
        }
    }
    
    /**
     * Removes a CelestialBody object
     * @param c
     */
    public void remove(CelestialBody c){
        bodies.remove(c);
    }
    
    /**
     * Removes a CelestialBody object based on a given index
     * @param index
     */
    public void remove(int index){
        bodies.remove(index);
    }
    
    public CelestialBody get(int index){
        return bodies.get(index);
    }
    
    public ArrayList<CelestialBody> get(){
        return bodies;
    }
    
}
