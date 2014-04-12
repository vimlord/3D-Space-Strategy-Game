/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.projectile_launchers;

import entities.*;
import entities.projectiles.*;
import entities.ships.*;
import main.*;

/**
 *
 * @author Christopher Hittner
 */
public class LaserGun implements ProjectileLauncher{
    private int temperature = 0;
    public final int maxTemp = 1000, cooldownMinimum = 200;
    private boolean coolLock = true, firing = false;
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
        temperature++;
        firing = true;
    }

    @Override
    public void cycle() {
        if(!firing){
            temperature--;
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
