/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.ships.shipTools.projectile_launchers;

import entities.*;
import entities.projectiles.*;
import entities.ships.*;
import main.*;

/**
 *
 * @author Christopher Hittner
 */
public class MissileBattery implements ProjectileLauncher {
    private final int magazineSize, startMagazines, reloadTime;
    private double wait = 0;
    private int shotsLeft, magazinesLeft;
    public static final double mass = 500;
    
    
    /**
     * Creates a MissileBattery object that hold 25 shots in a magazine, carries
     * ten spare magazines, and reloads in fifteen seconds.
     */
    public MissileBattery(){
        magazineSize = 25;
        shotsLeft = magazineSize;
        startMagazines = 10;
        magazinesLeft = startMagazines;
        reloadTime = 15 * CycleRunner.cyclesPerSecond;
    }
    
    @Override
    public boolean canFire() {
        if(wait == 0 && shotsLeft > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void fire(Ship shooter) {
        return;
    }

    @Override
    public void fire(Ship shooter, Entity target) {
        if(canFire()){
            EntityList.addProjectile(new Missile(shooter, target));
            shotsLeft--;
            if(shotsLeft > 0){
                wait = (int)(1.5 * CycleRunner.cyclesPerSecond);
            } else {
                wait = reloadTime * CycleRunner.cyclesPerSecond;
            }
        }
    }

    @Override
    public void cycle() {
        if(wait > 0){
            wait-= CycleRunner.getTimeWarp();
        }
        
        if(wait <= 0){
            if(shotsLeft == 0 && magazinesLeft > 0){
                shotsLeft = magazineSize;
                magazinesLeft--;
            }
        }
    }
    
    public static double getMass() {
        return mass;
    }
    
}
