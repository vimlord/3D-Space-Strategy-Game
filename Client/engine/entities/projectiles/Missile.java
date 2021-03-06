package engine.entities.projectiles;

import engine.entities.*;
import engine.entities.ships.*;
import engine.main.*;

public class Missile extends Projectile implements ControlSystem{
    
    private double fuel = 60 * CycleRunner.cyclesPerSecond;
    private double XZ_ROT = 0, Y_ROT = 0;
    private double targX, targY, targZ;
    private boolean hasEntityTarget;
    private long targetID;
    private long engineForce = 37500;
    
    public Missile(double X, double Y, double Z){
        super(X,Y,Z,500,10,0.5,0);
    }
    
    public Missile(Ship shooter, Entity target, boolean engineUpgrade, boolean HE_Payload){
        this(shooter,target);
        if(engineUpgrade){
            engineForce *= 2;
            fuel *= 2;
        } if(HE_Payload)
            damage *= 2;
    }

    /**
     * Creates a Missile object that will try to hit the coordinates of a targeted Entity
     * @param shooter
     * @param target
     */
    public Missile(Ship shooter, Entity target){
        super(shooter.getX(),shooter.getY(),shooter.getX(),500,10,0.5,0);
        
        double relX = target.getX() - shooter.getX();
        double relY = target.getY() - shooter.getY();
        double relZ = target.getZ() - shooter.getZ();
        double relXZ = Math.sqrt(Math.pow(relX,2) + Math.pow(relZ, 2));
        double rel = Math.sqrt(Math.pow(relXZ, 2) + Math.pow(relY, 2));
        
        double randomXZ = Math.toRadians((int)(121 * Math.random()) - 60);
        
        
        double angleXZ = Math.acos(relX/relXZ) + randomXZ;
        if(relZ < 0){
            angleXZ *= -1;
        }
        
        double randomY = Math.toRadians((int)(121 * Math.random()) - 60);
        
        double angleY = Math.sinh(relY/rel) + randomY;
        
        x = shooter.getX() + (shooter.getRadius() + radius + 0.1) * Math.cos(angleXZ) * Math.cos(angleY);
        y = shooter.getY() + (shooter.getRadius() + radius + 0.1) * Math.sin(angleY);
        z = shooter.getZ() + (shooter.getRadius() + radius + 0.1) * Math.sin(angleXZ) * Math.cos(angleY);
        
        targX = target.getX();
        targY = target.getY();
        targZ = target.getZ();
        
        hasEntityTarget = true;
        targetID = target.getID();
        
        velX = shooter.getSpeedX() + 20 * Math.cos(angleXZ) * Math.cos(angleY);
        velY = shooter.getSpeedY() + 20 * Math.sin(angleY);
        velZ = shooter.getSpeedZ() + 20 * Math.sin(angleXZ) * Math.cos(angleY);
        
    }
    
    
    
    public void move(){
        autopilot();
        accelerate();
        super.move();
        
    }
    
    
    @Override
    public void accelerate() {
        if(fuel <= 0 || !hasEntityTarget){
            return;
        }
        
        fuel--;
        
        double acceleration = engineForce/mass;
        
        velX += (getMass(true)/getMass(false)) * (acceleration/CycleRunner.cyclesPerSecond) * Math.cos(XZ_ROT) * Math.cos(Y_ROT);
        velY += (acceleration/CycleRunner.cyclesPerSecond) * Math.sin(Y_ROT);
        velZ += (acceleration/CycleRunner.cyclesPerSecond) * Math.sin(XZ_ROT) * Math.cos(Y_ROT);
    }

    @Override
    public void autopilot() {
        //Attempts to grab the Entity object with the ID that the Missile was given
        Entity e = EntityList.getEntity(targetID);
        
        double relX = targX - x;
        double relY = targY - y;
        double relZ = targZ - z;
        double relXZ = Math.sqrt(Math.pow(relX, 2) + Math.pow(relZ, 2));
        double rel = Math.sqrt(Math.pow(relX, 2) + Math.pow(relY, 2) + Math.pow(relZ, 2));
        
        //If the Entity object exists, it will set the target coordinates to those
        //of the targeted Entity object
        if(e != null){
            targX = e.getX() + (e.getSpeedX() - velX);
            targY = e.getY() + (e.getSpeedY() - velY);
            targZ = e.getZ() + (e.getSpeedZ() - velZ);
        } else {
            hasEntityTarget = false;
        }
        
        
        XZ_ROT = Math.acos(relX/relXZ);
        if(relZ < 0){
            XZ_ROT *= -1;
        }
        
        Y_ROT = Math.asin(relY/rel);
        
    }
    
    public double getDamage(){
        return damage;
    }
    
}


