/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.projectiles;

import entities.*;
import entities.ships.*;
import main.CycleRunner;
import main.EntityList;

/**
 *
 * @author Christopher Hittner
 */
public class Railgun implements ProjectileLauncher{
    public final int cooldownTime = 60 * CycleRunner.cyclesPerSecond;
    private int cooldown = 0;
    private int ammo;
    
    /**
     * Creates a railgun with a provided number of shots
     * @param shots The number of railgun slugs available
     */
    public Railgun(int shots){
        ammo = shots;
    }
    
    /**
     * Creates a railgun with eight available slugs
     */
    public Railgun(){
        ammo = 8;
    }
    
    public void cycle(){
        if(cooldown > 0){
            cooldown--;
        }
    }
    
    public boolean canFire(){
        if(cooldown == 0 && ammo > 0){ 
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Fires a slug without checking direction that the shooter is facing
     * @param shooter The Ship that is shooting
     */
    public void fire(Ship shooter){
        if(canFire()){
            EntityList.addProjectile(new Slug(shooter));
            cooldown = cooldownTime;
            ammo--;
        }
    }
    /**
     * Fires a slug only if the shooter is aiming at a target
     * @param shooter
     * @param target
     */
    public void fire(Ship shooter, Entity target){
        double relX = shooter.getX() - target.getX();
        double relY = shooter.getY() - target.getY();
        double relZ = shooter.getZ() - target.getZ();
        double relCoord = Math.sqrt(Math.pow(relX,2) + Math.pow(relY,2) + Math.pow(relZ,2));
        
        double relAngleX = Math.cos(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
        double relAngleY = Math.sin(shooter.getY_ROT());
        double relAngleZ = Math.sin(shooter.getXZ_ROT()) * Math.cos(shooter.getY_ROT());
        
        if(canFire() && ((relX/relCoord == relAngleX) && (relY/relCoord == relAngleY) && (relZ/relCoord == relAngleZ))){
            EntityList.addProjectile(new Slug(shooter));
            cooldown = cooldownTime;
            ammo--;
        }
    }
}
