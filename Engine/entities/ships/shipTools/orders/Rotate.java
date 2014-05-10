/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.ships.shipTools.orders;

import main.CycleRunner;

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
