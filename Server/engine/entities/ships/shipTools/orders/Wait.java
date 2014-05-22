package engine.entities.ships.shipTools.orders;

import engine.main.CycleRunner;

public class Wait extends Order{
    double cyclesLeft;
    
    public Wait(double seconds){
        order = "(WAIT)";
        cyclesLeft = (int)(CycleRunner.cyclesPerSecond * seconds);
    }
    
    public String getOrder(){
        cyclesLeft -= CycleRunner.getTimeWarp();
        return order + cyclesLeft;
    }
    
    public boolean getStatus(){
        if(cyclesLeft > 0){
            return true;
        } else {
            return false;
        }
    }
}


