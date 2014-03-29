/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.projectiles;

import entities.ships.*;
import main.CycleRunner;
import main.EntityList;

/**
 *
 * @author Christopher Hittner
 */
public class Railgun {
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
    
    public void fire(Ship shooter){
        if(canFire()){
            EntityList.addProjectile(new Slug(shooter));
            cooldown = cooldownTime;
            ammo--;
        }
    }
}
