/*
 * GameControlSettings will store data for various data required by the client
 * for the user interface, such as whether or not a player's ship should be able
 * to auto attack.
 */

package client.game;

import engine.entities.Entity;
import engine.main.EntityList;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class GameControlSettings {
    private static boolean autoAttackAlowed = false;
    
    private static ArrayList<Long> SELECTED_ID = new ArrayList<>();
    
    //To elaborate, this will store the results of the buildSphere() method in
    //the GUI class every time the GUI is redrawn.
    private static ArrayList<Integer[]> circles = new ArrayList<>();
    private static ArrayList<Long> reciprocalIDs = new ArrayList<>();
    
    
    public static boolean autoAttackAlowed(){
        return autoAttackAlowed;
    }
    
    public static void setAutoAttackPermission(boolean b){
        autoAttackAlowed = b;
    }
    
    public static ArrayList<Entity> getSelectedEntities(){
        ArrayList<Entity> list = new ArrayList<>();
        for(Long l : SELECTED_ID)
            list.add(EntityList.getEntity(l));
        
        return list;
    }
    
    public static ArrayList<Long> getSelectedIDs(){
        return SELECTED_ID;
    }
    
    public static void setSelectedID(long l){
        SELECTED_ID = new ArrayList<>();
        if(l >= 0)
            SELECTED_ID.add(l);
    }
    
    public static void setSelectedID(Entity e){
        SELECTED_ID = new ArrayList<>();
        if(e.getID() >= 0)
            SELECTED_ID.add(e.getID());
    }
    
    public static void addSelectedID(long l){
        if(l >= 0)
            SELECTED_ID.add(l);
    }
    
    public static void addSelectedID(Entity e){
        if(e.getID() >= 0)
            SELECTED_ID.add(e.getID());
    }
    
    public static void removeSelectedID(long l){
        SELECTED_ID.remove((Long) l);
    }
    
    public static void removeSelectedID(Entity e){
        SELECTED_ID.remove((Long)e.getID());
    }
    
    public static void setCircles(ArrayList<Integer[]> newCircles, ArrayList<Long> IDs){
        circles = newCircles;
        reciprocalIDs = IDs;
    }
    
    public static void resetCircles(){
        circles = new ArrayList<>();
        reciprocalIDs = new ArrayList<>();
    }
    
    public static long getClickedEntityID(int x, int y){
        for(int i = 0; i < circles.size(); i++){
            
            int X = circles.get(i)[0];
            int Y = circles.get(i)[1];
            int range = circles.get(i)[2];
            
            double distance = Math.sqrt(Math.pow(X-x,2) + Math.pow(Y-y,2));
            
            if(distance <= range){
                return reciprocalIDs.get(i);
            }
            
            
        }
        
        return -1;
    }
    
    
    
    
    
    
}
