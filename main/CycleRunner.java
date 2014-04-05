package main;
/*
 * Runs the necessary code to execute a cycle in the game's processes.
 */
 
/**
 *
 * @author Christopher Hittner
 */
public class CycleRunner {
    //Number of times the exexuteCycle method is assumed to run each second.
    public static final int cyclesPerSecond = 1000;
     
     
    public static void executeCycle(){
        EntityList.executeGravity();
        EntityList.executeMovement();
        EntityList.executeCollisions();
        
    }
}

