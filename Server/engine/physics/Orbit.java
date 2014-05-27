/*
 * The Orbit class stores data for a circular orbit around a CelestialBody object.
 * All numbers are relative to the body being orbited, not the origin (0,0,0).
 */

package engine.physics;

import engine.entities.Entity;

/**
 *
 * @author Christopher
 */
public class Orbit implements PhysicsConstants{
    protected double a, inc, LAN, MaE, mass;
    
    protected double velX, velY, velZ;
    protected double x, y, z;
    
    protected long ID;
    
    /**
     * Defines an orbit around an Entity
     * @param semimajorAxis
     * @param inclination
     * @param longitudeAscendingNode
     * @param meanAnomaly
     * @param entity The Entity being orbited
     */
    public Orbit(double semimajorAxis, double inclination, double longitudeAscendingNode, double meanAnomaly, Entity entity){
        a = semimajorAxis;
        inc = inclination;
        LAN = longitudeAscendingNode;
        MaE = meanAnomaly;
        mass = entity.getMass(false);
        ID = entity.getID();
        
        calculateData();
    }

    private void calculateData() {
        double vel = Math.sqrt(G * mass/a);
        
        velY = vel * Math.cos(MaE - LAN) * Math.sin(inc);
        y = a * Math.sin(MaE - LAN) * Math.sin(inc);
        
        double velXZ = vel * Math.sin(inc) * Math.sin(MaE - LAN);
        double xz = Math.sqrt(Math.pow(a,2) - Math.pow(y, 2));
        
        x = xz * Math.cos(MaE - LAN);
        velX = velXZ * Math.cos(MaE - LAN + Math.toRadians(90));
        
        z = xz * Math.sin(MaE - LAN);
        velZ = velXZ * Math.sin(MaE - LAN + Math.toRadians(90));
        
    }
    
    
    
    public void setMeanAnomaly(double meanAnomaly){
        MaE = meanAnomaly;
        calculateData();
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
    public long getID(){
        return ID;
    }
    
}
