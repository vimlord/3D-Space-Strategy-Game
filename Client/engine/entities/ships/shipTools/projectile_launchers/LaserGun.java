/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.ships.shipTools.projectile_launchers;

import engine.entities.*;
import engine.entities.projectiles.*;
import engine.entities.ships.*;
import engine.main.*;
import java.io.Serializable;

/**
 *
 * @author Christopher Hittner
 */
public class LaserGun implements ProjectileLauncher, Serializable{
    private int temperature = 0;
    public final int maxTemp = 10 * CycleRunner.cyclesPerSecond, cooldownMinimum = 2 * CycleRunner.cyclesPerSecond;
    private boolean coolLock = false, firing = false;
    public static final double mass = 50;
    
    public LaserGun(){
        
    }
    
    @Override
    public boolean canFire() {
        if(coolLock){
            return false;
        } else if(temperature >= maxTemp){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void fire(Ship shooter) {
        if(!canFire()){
            firing = false;
            return;
        }
        EntityList.addProjectile(new Laser(shooter));
        fire();
    }

    @Override
    public void fire(Ship shooter, Entity target) {
        if(!canFire()){
            firing = false;
            return;
        }
        EntityList.addProjectile(new Laser(shooter, target));
        fire();
    }
    
    private void fire(){
        temperature+= CycleRunner.getTimeWarp();
        firing = true;
    }

    @Override
    public void cycle() {
        if(!firing){
            temperature-= CycleRunner.getTimeWarp();
        }
        
        if(temperature >= maxTemp){
            firing = false;
            coolLock = true;
        }
        if(coolLock && temperature < cooldownMinimum){
            coolLock = false;
        }
    }
    
    public static double getMass() {
        return mass;
    }

}
