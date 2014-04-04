package entities.projectiles;

import entities.*;
import entities.ships.*;
import main.CycleRunner;

public class Missile extends Projectile implements ControlSystem{
    
    private double XZ_ROT = 0, Y_ROT = 0;
    private double targX, targY, targZ;
    
    public Missile(double X, double Y, double Z){
        super(X,Y,Z,500,10,0.5,0);
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
        double rel = Math.sqrt(Math.pow(relX, 2) + Math.pow(relY, 2) + Math.pow(relZ, 2));
        
        x = shooter.getX() + shooter.getRadius() * relX/rel;
        y = shooter.getY() + shooter.getRadius() * relY/rel;
        z = shooter.getZ() + shooter.getRadius() * relZ/rel;
        
        targX = target.getX();
        targY = target.getY();
        targZ = target.getZ();
        
    }
    
    public Missile(Ship shooter, double X, double Y, double Z){
        super(shooter.getX(),shooter.getY(),shooter.getX(),500,10,0.5,0);
        double relX = X - shooter.getX();
        double relY = Y - shooter.getY();
        double relZ = Z - shooter.getZ();
        double rel = Math.sqrt(Math.pow(relX, 2) + Math.pow(relY, 2) + Math.pow(relZ, 2));
        
        x = shooter.getX() + 1.1 * shooter.getRadius() * relX/rel;
        y = shooter.getY() + 1.1 * shooter.getRadius() * relY/rel;
        z = shooter.getZ() + 1.1 * shooter.getRadius() * relZ/rel;
        
        targX = X;
        targY = Y;
        targZ = Z;
        
    }
    
    public void move(){
        autopilot();
        accelerate();
        super.move();
    }
    
    
    @Override
    public void accelerate() {
        velX += 75/CycleRunner.cyclesPerSecond * Math.cos(XZ_ROT) * Math.cos(Y_ROT);
        velY += 75/CycleRunner.cyclesPerSecond * Math.sin(Y_ROT);
        velZ += 75/CycleRunner.cyclesPerSecond * Math.sin(XZ_ROT) * Math.cos(Y_ROT);
    }

    @Override
    public void autopilot() {
        double relX = targX - x;
        double relY = targY - y;
        double relZ = targZ - z;
        double relXZ = Math.sqrt(Math.pow(relX, 2) + Math.pow(relZ, 2));
        double rel = Math.sqrt(Math.pow(relX, 2) + Math.pow(relY, 2) + Math.pow(relZ, 2));
        
        XZ_ROT = Math.cosh(relX/relXZ);
        if(relY < 0){
            XZ_ROT *= -1;
        }
        
        Y_ROT = Math.sinh(relY/rel);
        
    }
    
}


