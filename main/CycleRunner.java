package main;

import gameMechanics.gameModes.*;

/*
 * Runs the necessary code to execute a cycle in the game's processes.
 */
 
/**
 *
 * @author Christopher Hittner
 */
public class CycleRunner {
    public static final int cyclesPerSecond = 16000;
    
    private static GameMode gamemode = null;
     
    public static void executeCycle(){
        //Causes gravitation
        EntityList.executeGravity();
        //Moves stuff
        EntityList.executeMovement();
        //Moves stuff
        EntityList.executeCollisions();
        //Kills "dead" Ships and other stuff... if the other stuff is implemented
        EntityList.executeCasualties();
        //Runs necessary gamemode stuff
        if(gamemode != null)
            gamemode.executeCycle();
        
        
    }
    
    public GameMode getGamemode(){
        return gamemode;
    }
    
    public void setGamemode(GameMode g){
        gamemode = g;
    }
}

