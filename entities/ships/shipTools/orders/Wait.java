package entities.ships.shipTools.orders;

public class Wait extends Order{
    int cyclesLeft
    
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


