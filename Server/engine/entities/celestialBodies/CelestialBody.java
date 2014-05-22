/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package engine.entities.celestialBodies;
 
import engine.entities.Entity;
import engine.entities.ships.*;
import engine.main.*;
 
/**
 *
 * @author Christopher Hittner
 */
public class CelestialBody extends Entity{
    
     
    /**
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius
     */
    public CelestialBody(double X, double Y, double Z, double M, double R){
        super(X, Y, Z, M, R);
    }
     
}

