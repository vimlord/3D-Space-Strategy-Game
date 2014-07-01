/*
 * This class represents a railgun slug that would be launched at 6500 m/s.
 * Every Railgun slug will always have a mass of 10000 kg
 */

package engine.entities.projectiles;

import engine.entities.ships.*;

/**
 *
 * @author Christopher Hittner
 */
public class Slug extends Projectile{
    
    /**
     * Creates a railgun slug with predetermined mass and size
     * @param X
     * @param Y
     * @param Z
     */
    public Slug(double X, double Y, double Z){
        super(X,Y,Z,10000,0,0.5,6500);
    }
    
    /**
     * Fires a railgun round based on the data of a shooter
     * @param shooter The Ship object that is shooting the shot
     */
    public Slug(Ship shooter, boolean fast){
        super(shooter.getX(),shooter.getY(),shooter.getZ(),10000,0,0.5,6500);
        
        int launchSpeedFactor = 1;
        if(fast)
            launchSpeedFactor = 2;
        
        x += (shooter.getRadius() + 2) * Math.cos(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
        y += (shooter.getRadius() + 2) * Math.sin(shooter.getY_ROT());
        z += (shooter.getRadius() + 2) * Math.sin(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
        velX += launchSpeedFactor * launchSpeed * Math.cos(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
        velY += launchSpeedFactor * launchSpeed * Math.sin(shooter.getY_ROT());
        velZ += launchSpeedFactor * launchSpeed * Math.sin(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
    }
    
    public Slug(Ship shooter){
        this(shooter, false);
    }
    
}
