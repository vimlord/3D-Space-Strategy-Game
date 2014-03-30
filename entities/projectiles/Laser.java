/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.projectiles;

import entities.Entity;
import entities.ships.*;

/**
 *
 * @author Christopher Hittner
 */
public class Laser extends Projectile{
    public Laser(double X, double Y, double Z){
        super(X,Y,Z,0,0.1,0,c);
    }
    
    /**
     * Fires a laser beam at a set of coordinates
     * @param shooter The Ship that shot the laser
     * @param X The x-coord
     * @param Y The y-coord
     * @param Z The z-coord
     */
    public Laser(Ship shooter, double X, double Y, double Z){
        super(shooter.getX(),shooter.getY(),shooter.getZ(),0,0.1,0,c);
        double relX = X-shooter.getX();
        double relY = Y-shooter.getY();
        double relZ = Z-shooter.getZ();
        
        double relXZ = Math.sqrt(Math.pow(relX,2) + Math.pow(relZ,2));
        double rel = Math.sqrt(Math.pow(relXZ,2) + Math.pow(relY,2));
        
        double angleXZ = Math.cosh(relX/relXZ);
        if(relZ < 0){
            angleXZ = (2 * Math.PI) - angleXZ;
        }
        double angleY = Math.sinh(relY/rel);
        
        x += shooter.getRadius() * (Math.cos(angleXZ) * Math.cos(angleY));
        y += shooter.getRadius() * (Math.sin(angleY));
        z += shooter.getRadius() * (Math.sin(angleXZ) * Math.cos(angleY));
        
        //Light must always go at the speed of light, as physics clearly seems
        //to state.
        velX += c * (Math.cos(angleXZ) * Math.cos(angleY));
        velY += c * (Math.sin(angleY));
        velZ += c * (Math.sin(angleXZ) * Math.cos(angleY));
    }
    
    /**
     * Creates a Laser entity that will fly towards the target
     * NOTE: This may require modification in the future to compensate for the
     * motion of the target object.
     * @param shooter The Ship object that fired the laser
     * @param target The Entity being targeted
     */
    public Laser(Ship shooter, Entity target){
        super(shooter.getX(),shooter.getY(),shooter.getZ(),0,0.1,0,c);
        
        //The target coordinates
        double X = target.getX();
        double Y = target.getY();
        double Z = target.getZ();
        
        //From here on, it's basically the same as the alternate constructor
        double relX = X-shooter.getX();
        double relY = Y-shooter.getY();
        double relZ = Z-shooter.getZ();
        
        double relXZ = Math.sqrt(Math.pow(relX,2) + Math.pow(relZ,2));
        double rel = Math.sqrt(Math.pow(relXZ,2) + Math.pow(relY,2));
        
        double angleXZ = Math.cosh(relX/relXZ);
        if(relZ < 0){
            angleXZ = (2 * Math.PI) - angleXZ;
        }
        double angleY = Math.sinh(relY/rel);
        
        x += shooter.getRadius() * (Math.cos(angleXZ) * Math.cos(angleY));
        y += shooter.getRadius() * (Math.sin(angleY));
        z += shooter.getRadius() * (Math.sin(angleXZ) * Math.cos(angleY));
        
        //Light must always go at the speed of light, as physics clearly seems
        //to state.
        velX += c * (Math.cos(angleXZ) * Math.cos(angleY));
        velY += c * (Math.sin(angleY));
        velZ += c * (Math.sin(angleXZ) * Math.cos(angleY));
    }
    
    /**
     * Creates a Laser entity that follows the direction that the Ship object is
     * pointing in
     * @param shooter The Ship object that fired the Laser
     */
    public Laser(Ship shooter){
        super(shooter.getX(),shooter.getY(),shooter.getZ(),0,0.1,0,c);
        x += shooter.getRadius() * (Math.cos(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT()));
        y += shooter.getRadius() * (Math.sin(shooter.getY_ROT()));
        z += shooter.getRadius() * (Math.sin(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT()));
        
        //Light must always go at the speed of light, as physics clearly seems
        //to state.
        velX += c * (Math.cos(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT()));
        velY += c * (Math.sin(shooter.getY_ROT()));
        velZ += c * (Math.sin(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT()));
    }
    
    
    
    /*
     * An override to the original move() method that keeps the Laser entity at
     * the speed of light at all times, as the laws of Physics state
     */
    public void move(){
        double vel = Math.sqrt(Math.pow(velX,2) + Math.pow(velY,2) + Math.pow(velZ,2));
        if(vel != c){
            double ratio = c/vel;
            velX *= ratio;
            velY *= ratio;
            velZ *= ratio;
        }
        super.move();
    }
}
