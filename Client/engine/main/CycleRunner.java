package engine.main;

import engine.gameMechanics.gameModes.*;

/*
 * Runs the necessary code to execute a cycle in the game's processes.
 */
 
/**
 *
 * @author Christopher Hittner
 */
public class CycleRunner {
    //The amount of cycles executed per secod
    public static final int cyclesPerSecond = 16000;
    //The factor by which all physics calculations are multipled; a higher number
    //represents a higher speed
    private static double timeWarp = 1;
    
    private static GameMode gamemode = null;
    
    public static GameMode getGamemode(){
        return gamemode;
    }
    
    public static void setGamemode(GameMode g){
        gamemode = g;
    }
    public static double getTimeWarp(){
        return timeWarp;
    }
    public static void setTimeWarp(double value){
        timeWarp = value;
    }
}

