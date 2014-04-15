package entities.ships.shipTools.orders;

public class Warp extends Order{
    double cyclesLeft;
    
    
    public Warp(double factor, double seconds){
        order = "(WRP)";
        
        //Sets the amount of time that the Ship should warp for
        cyclesLeft = CycleRunner.cyclesPerSecond * seconds;
        
        //Sets warp level
        if(factor <= 0){
            order += 0;
        } else {
            order += factor;
        }
    }
    
    public String getOrder(){
        cyclesLeft--;
        return order;
    }
    
    public boolean getStatus(){
        if(cyclesLeft <= 0){
            retun false;
        } else {
            return true;
        }
    }
    
}
