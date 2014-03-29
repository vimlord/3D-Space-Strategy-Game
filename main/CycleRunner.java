package main;
/*
 * Runs the necessary code to execute a cycle in the game's processes.
 */
 
/**
 *
 * @author Christopher Hittner
 */
public class CycleRunner {
    public static final int cyclesPerSecond = 60000;
     
     
    public static void executeCycle(){
        EntityList.executeGravity();
        EntityList.executeMovement();
        EntityList.executeCollisions();
        
    }
}

