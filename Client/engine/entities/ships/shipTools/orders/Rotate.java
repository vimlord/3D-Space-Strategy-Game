/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.ships.shipTools.orders;

import engine.main.CycleRunner;

/**
 *
 * @author Christopher Hittner
 */
public class Rotate extends Order{
    
    public Rotate(double XZ, double Y){
        order = "(ROT)";
        order += (XZ + "|" + Y);
    }
    
}
