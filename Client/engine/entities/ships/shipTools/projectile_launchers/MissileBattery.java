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
public class MissileBattery implements ProjectileLauncher, Serializable {
    private final int magazineSize, startMagazines, reloadTime;
    private double wait = 0;
    private int shotsLeft, magazinesLeft;
    public static final double mass = 500;
    private boolean fastMissiles = false, HE_Missiles = false;
    
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
            Missile payload = new Missile(shooter, target, fastMissiles, HE_Missiles);
            EntityList.addProjectile(payload);
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

    /**
     * Toggles the HE Explosives perk for this MissileBattery (Doubles the damage)
     * @param b
     */
    public void toggleHECargo(boolean b) {
        HE_Missiles = b;
    }
    
    /**
     * Toggles the Velociraptor Missile perk for this MissileBattery (doubles acceleration and fuel)
     * @param b
     */
    public void toggleFastMissiles(boolean b) {
        fastMissiles = b;
    }
    
}
