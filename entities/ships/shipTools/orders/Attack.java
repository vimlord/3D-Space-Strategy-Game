/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.ships.shipTools.orders;

/**
 *
 * @author Christopher Hittner
 */
public class Attack extends Order{
    
    public Attack(boolean missile, boolean laser, long targID){
        order = "(ATK)[";
        if(missile) order += "T";
            else order += "F";
        if(laser) order += "T";
            else order += "F";
        
        order += "]" + targID;
    }
    
}
