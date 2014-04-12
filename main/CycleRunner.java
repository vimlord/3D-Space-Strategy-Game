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
        //Causes gravitation
        EntityList.executeGravity();
        //Moves stuff
        EntityList.executeMovement();
        //Moves stuff
        EntityList.executeCollisions();
        //Kills "dead" Ships and other stuff... if the other stuff is implemented
        EntityList.executeCasualties();
        
    }
}

