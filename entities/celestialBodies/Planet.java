/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.celestialBodies;
import entities.*;
import entities.ships.Ship;
import main.CycleRunner;

/**
 *
 * @author Christopher
 */
public class Planet extends CelestialBody{
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
    public Planet(double X, double Y, double Z, double M, double R){
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
    public Planet(double X, double Y, double Z, double M, double R, double pressure, double height){
        super(X, Y, Z, M, R);
        atmPressure = pressure;
        if(height <= 0){
            atmosphereHeight = 0;
            hasAtmosphere = false;
        } else {
            atmosphereHeight = height;
            hasAtmosphere = true;
        }
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
    
    public boolean testCollision(Entity other){
        double distanceX = other.getX() - this.x;
        double distanceY = other.getY() - this.y;
        double distanceZ = other.getZ() - this.z;
        double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2) + Math.pow(distanceZ,2));
        
        if((this.radius + atmosphereHeight) + other.getRadius() >= distance){
            return true;
        }
        return false;
    }
    
    /**
     * Performs collision physics with an Entity object
     * @param other The Entity being collided with
     */
    public void collide(Entity other){
        //If the objects are colliding physically, it will deal with the objects as such
        if(super.testCollision(other)){
            super.collide(other);
        }
        if(hasAtmosphere){
            double relVelX = other.getSpeedX() - this.velX;
            double relVelY = other.getSpeedY() - this.velY;
            double relVelZ = other.getSpeedZ() - this.velZ;
            double relVel = Math.sqrt(Math.pow(relVelX,2) + Math.pow(relVelY,2) + Math.pow(relVelZ,2));

            double dragForce = getPressure(other) * 0.47 * Math.pow(relVel,2);
            other.setSpeedX(other.getSpeedX() - ((relVelX/relVel) * dragForce/other.getMass()));
            other.setSpeedY(other.getSpeedY() - ((relVelY/relVel) * dragForce/other.getMass()));
            other.setSpeedZ(other.getSpeedZ() - ((relVelZ/relVel) * dragForce/other.getMass()));
        }
        
    }
    
    /**
     * Performs collision physics with a Ship object
     * @param other The ship
     */
    public void collide(Ship other){
        //If the objects are colliding physically, it will deal with the objects as such
        if(super.testCollision(other)){
            other.collide(this);
        }
        if(hasAtmosphere){
            double relVelX = other.getSpeedX() - this.velX;
            double relVelY = other.getSpeedY() - this.velY;
            double relVelZ = other.getSpeedZ() - this.velZ;
            double relVel = Math.sqrt(Math.pow(relVelX,2) + Math.pow(relVelY,2) + Math.pow(relVelZ,2));

            double dragForce = getPressure(other) * 0.47 * Math.pow(relVel,2);
            other.setSpeedX(other.getSpeedX() - ((relVelX/relVel) * dragForce/other.getMass()));
            other.setSpeedY(other.getSpeedY() - ((relVelY/relVel) * dragForce/other.getMass()));
            other.setSpeedZ(other.getSpeedZ() - ((relVelZ/relVel) * dragForce/other.getMass()));

            other.damage(getPressure(other) * (relVel - 120)/CycleRunner.cyclesPerSecond);
        }
    }


    
}
