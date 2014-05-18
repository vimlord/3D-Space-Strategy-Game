/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package entities.celestialBodies;
 
import entities.Entity;
import entities.ships.*;
import main.CycleRunner;
 
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

