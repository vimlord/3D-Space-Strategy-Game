/*
 * The Orbit class stores data for a circular orbit around a CelestialBody object.
 * All numbers are relative to the body being orbited, not the origin (0,0,0).
 */

package physics;

import entities.Entity;

/**
 *
 * @author Christopher
 */
public class Orbit implements PhysicsConstants{
    protected double a, inc, LAN, MaE, mass;
    
    protected double velX, velY, velZ;
    protected double x, y, z;
    
    /**
     * Defines an orbit around an Entity
     * @param eccentricity measured in radians
     * @param semimajorAxis
     * @param inclination
     * @param longitudeAscendingNode
     * @param argumentPeriapsis
     * @param meanAnomaly
     * @param entity The Entity being orbited
     */
    public Orbit(double semimajorAxis, double inclination, double longitudeAscendingNode, double meanAnomaly, Entity entity){
        a = semimajorAxis;
        inc = inclination;
        LAN = longitudeAscendingNode;
        MaE = meanAnomaly;
        mass = entity.getMass();
        
        calculateData();
    }

    private void calculateData() {
        double vel = Math.sqrt(G * mass/a);
        
        velY = vel * Math.cos(MaE) * Math.sin(inc);
        y = a * Math.cos(MaE) * Math.sin(inc);
        
        double velXZ = vel * Math.sin(MaE) * Math.cos(inc);
        double xz = Math.sqrt(Math.pow(a,2) - Math.pow(y, 2));
        
        double x = xz * Math.cos(MaE + LAN);
        double velX = velXZ * Math.cos(MaE + LAN);
        
        double z = xz * Math.sin(MaE + LAN);
        double velZ = velXZ * Math.cos(MaE + LAN);
        
    }
    
    
    
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public double getSpeedX(){
        return velX;
    }
    public double getSpeedY(){
        return velY;
    }
    public double getSpeedZ(){
        return velZ;
    }
    
}
