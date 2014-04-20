package entities.ships.shipTools.orders;

import main.CycleRunner;

public class Wait extends Order{
    int cyclesLeft;
    
    public Wait(double seconds){
        order = "(WAIT)";
        cyclesLeft = (int)(CycleRunner.cyclesPerSecond * seconds);
    }
    
    public String getOrder(){
        cyclesLeft--;
        return order;
    }
    
    public boolean getStatus(){
        if(cyclesLeft > 0){
            return true;
        } else {
            return false;
        }
    }
}


