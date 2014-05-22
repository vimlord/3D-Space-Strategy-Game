/*
 * This class stores data for a black hole.
 */

package engine.entities.celestialBodies;

import engine.entities.Entity;

/**
 *
 * @author Christopher
 */
public class BlackHole extends CelestialBody{
    
    /**
     *
     * @param X
     * @param Y
     * @param Z
     * @param S The Schwarzchild radius of the black hole
     */
    public BlackHole(double X, double Y, double Z, double S) {
        //The radius equals half of S because the closest that an object has to
        //get in order to need to be at the speed of light to maintain an orbit
        //is half of its Schwarzchild radius.
        super(X, Y, Z, (S * Math.pow(c,2))/(2 * G), S/2);
    }
    
    /**
     * Modifies the collide() method so that it absorbs the mass of the other Entity.
     * @param other The Entity that is colliding
     */
    public void collide(Entity other){
        super.collide(other);
        
        double Xratio = this.mass/other.getMass();
        x = (Xratio * x + other.getX())/(Xratio+1);
        
        double Yratio = this.mass/other.getMass();
        y = (Yratio * x + other.getY())/(Yratio+1);
        
        double Zratio = this.mass/other.getMass();
        z = (Zratio * y + other.getZ())/(Zratio+1);
        
        mass += other.getMass();
        //The other object's mass is left unchanged because it will be destroyed anyway
    }
    
}
