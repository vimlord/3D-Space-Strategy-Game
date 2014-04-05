/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.projectile_launchers;

import entities.*;
import entities.ships.*;

/**
 *
 * @author Christopher
 */
public interface ProjectileLauncher {
    public boolean canFire();
    public void fire(Ship shooter);
    public void fire(Ship shooter, Entity target);
    public void cycle();
}
