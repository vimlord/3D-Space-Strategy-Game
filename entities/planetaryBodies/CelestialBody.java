/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package entities.planetaryBodies;
 
import entities.Entity;
 
/**
 *
 * @author Christopher Hittner
 */
public class CelestialBody extends Entity{
    private double atmPressure = 0;
    private boolean hasAtmosphere;
    private double atmosphereHeight = 0;
     
    /**
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius
     */
    public CelestialBody(double X, double Y, double Z, double M, double R){
        super(X, Y, Z, M, R);
        hasAtmosphere = false;
    }
     
    /**
     *
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius
     * @param pressure The pressure at the surface
     * @param height The atmosphere's height
     */
    public CelestialBody(double X, double Y, double Z, double M, double R, double pressure, double height){
        super(X, Y, Z, M, R);
        hasAtmosphere = true;
        atmPressure = pressure;
        atmosphereHeight = height;
    }
     
    /**
     *
     * @param other The air pressure wherever the Entity is
     * @return The air pressure at the Entity's coordinates
     */
    public double getPressure(Entity other){
        if(testCollision(other) || !hasAtmosphere){
            return 0;
        }
        double distanceX = other.getX() - this.x;
        double distanceY = other.getY() - this.y;
        double distanceZ = other.getZ() - this.z;
        double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2) + Math.pow(distanceZ,2));
        distance -= this.radius;
        if(distance <= atmosphereHeight)
            return (atmPressure * (1-(double)(distance/atmosphereHeight)));
        else return 0;
    }
     
    /**
     *
     * @param X The x-coordinate
     * @param Y The y-coordinate
     * @param Z The z-coordinate
     * @return The air pressure at the provided coordinates
     */
    public double getPressure(double X, double Y, double Z){
        if(!hasAtmosphere){
            return 0;
        }
        double distanceX = X - this.x;
        double distanceY = Y - this.y;
        double distanceZ = Y - this.z;
        double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2) + Math.pow(distanceZ,2));
        distance -= this.radius;
        if(distance < 0 || distance > atmosphereHeight){
            return 0;
        } else
            return (atmPressure * (1-(double)(distance/atmosphereHeight)));
    }
     
}

