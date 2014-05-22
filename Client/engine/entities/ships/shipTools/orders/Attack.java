/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.ships.shipTools.orders;

import engine.entities.Entity;
import engine.main.CycleRunner;

/**
 *
 * @author Christopher Hittner
 */
public class Attack extends Order{
    
    private double count = 0;
    
    public Attack(boolean missile, boolean laser, boolean railgun, long targID){
        order = "(ATK)[";
        if(missile) order += "T";
            else order += "F";
        if(laser) order += "T";
            else order += "F";
        
        order += "]" + targID;
    }
    
    public Attack(boolean missile, boolean laser, boolean railgun, Entity target){
        order = "(ATK)[";
        if(missile) order += "T";
            else order += "F";
        if(laser) order += "T";
            else order += "F";
        if(railgun) order += "T";
            else order += "F";
        
        order += "]" + target.getID();
    }
    
    public String getOrder(){
        count += 0.5;
        return super.getOrder();
    }
    
    public boolean getStatus(){
        if(count > CycleRunner.cyclesPerSecond/2.0){
            status = false;
        } else {
            status = true;
        }
        return status;
    }
    
}
