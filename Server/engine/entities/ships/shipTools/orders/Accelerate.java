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
public class Accelerate extends Order{
    int cyclesToAct;
    
    public Accelerate(double percentPower, double seconds){
        cyclesToAct = (int)(CycleRunner.cyclesPerSecond * seconds);
        order = "(ACC)";
        if(percentPower <= 0){
            order += (0);
        } else if(percentPower >= 100){
            order += (100);
        } else {
            order += (percentPower);
        }
        order += "|";
    }
    
    public String getOrder(){
        cyclesToAct-= CycleRunner.getTimeWarp();
        testStatus();
        return super.getOrder() + cyclesToAct;
        
    }

    private void testStatus() {
        if(cyclesToAct <= 0){
            status = false;
        }
    }

}
