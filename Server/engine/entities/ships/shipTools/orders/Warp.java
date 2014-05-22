package engine.entities.ships.shipTools.orders;

import engine.main.CycleRunner;

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
        order += "|";
    }
    
    public String getOrder(){
        cyclesLeft-= CycleRunner.getTimeWarp();
        return order + cyclesLeft;
    }
    
    public boolean getStatus(){
        if(cyclesLeft <= 0){
            return false;
        } else {
            return true;
        }
    }
    
}
