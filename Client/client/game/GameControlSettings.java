/*
 * GameControlSettings will store data for various data required by the client
 * for the user interface, such as whether or not a player's ship should be able
 * to auto attack.
 */

package client.game;

import client.gui.menu.MenuManager;
import client.gui.menu.buttons.Button;
import client.gui.menu.buttons.Slider;
import client.main.Client;
import engine.entities.Entity;
import engine.entities.ships.Ship;
import engine.entities.ships.shipTools.orders.Accelerate;
import engine.entities.ships.shipTools.orders.Rotate;
import engine.entities.ships.shipTools.orders.Warp;
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
    
    private static double ROT_Targ_XZ = 0, ROT_Targ_Y = 0;
    
    private static boolean autofire = false, weaponsActive = false;
    
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
        addSelectedID(l);
    }
    
    public static void setSelectedID(Entity e){
        SELECTED_ID = new ArrayList<>();
        addSelectedID(e);
    }
    
    public static void addSelectedID(long l){
        if(l >= 0)
            SELECTED_ID.add(l);
        if(EntityList.getEntity(l) instanceof Ship){
            Ship s = (Ship) (EntityList.getEntity(l));
            ArrayList<Button> buttons = MenuManager.getMenu("gameInterface").getButtons();
            for(Button b : buttons){
                if(b instanceof Slider){
                    Slider sl = (Slider) b;
                    sl.setValue(0);
                }
            }
        }
    }
    
    public static void addSelectedID(Entity e){
        addSelectedID(e.getID());
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

    public static void processButtonCommand(String cmd) {
        if(cmd == "" || cmd == null)
            return;
        
        String command = cmd;
        
        System.out.println(command);
        
        String parameter = command.substring(command.indexOf("(") + 1);
        command = command.substring(0,command.indexOf("("));
        
        Ship commanded;
        try{                commanded = EntityList.getShip(reciprocalIDs.get(0));}
        catch(Exception e){ commanded = null;}
        
        double XZ = Math.atan(commanded.getSpeedZ()/commanded.getSpeedX());
        if(commanded.getSpeedX() < 0){
            XZ += Math.PI;
        }
        double velXZ = Math.sqrt(Math.pow(commanded.getSpeedX(),2) + Math.pow(commanded.getSpeedZ(),2));
        double Y = Math.atan(commanded.getSpeedY()/velXZ);
        
        switch(command){
            case "Prograde":
                ROT_Targ_XZ = XZ;
                ROT_Targ_Y = Y;
                break;
            case "Retrograde":
                ROT_Targ_XZ = (XZ - Math.PI);
                ROT_Targ_Y = -Y;
                break;
            case "Normal":
                ROT_Targ_XZ = XZ;
                ROT_Targ_Y = Y + Math.PI/2.0;
                break;
            case "Antinormal":
                ROT_Targ_XZ = XZ;
                ROT_Targ_Y = Y - Math.PI/2.0;
                break;
            case "RadialIn":
                ROT_Targ_XZ = (XZ + Math.PI/2.0);
                ROT_Targ_Y = Y;
                break;
            case "RadialOut":
                ROT_Targ_XZ = (XZ - Math.PI/2.0);
                ROT_Targ_Y = Y;
                break;
            case "PointUp":
                ROT_Targ_Y++;
                break;
            case "PointDown":
                ROT_Targ_Y--;
                break;
            case "PointLeft":
                ROT_Targ_XZ--;
                break;
            case "PointRight":
                ROT_Targ_XZ++;
                break;
            case "ExecuteRotation":
                if(SELECTED_ID.size() == 1){
                    Entity ship = EntityList.getEntity(SELECTED_ID.get(0));
                    if(ship instanceof Ship){
                        Client.getConnection().sendObject("[ORDER][(" + Client.getID() + ")(" + ship.getID() + ")]"+ (new Rotate(ROT_Targ_XZ,ROT_Targ_Y)).getOrder());
                    }
                }
                break;
            case "SetThrottle":
                if(SELECTED_ID.size() == 1){
                    Entity ship = EntityList.getEntity(SELECTED_ID.get(0));
                    if(ship instanceof Ship){
                        Client.getConnection().sendObject("[ORDER][(" + Client.getID() + ")(" + ship.getID() + ")]"+ (new Accelerate(Double.parseDouble(parameter),Double.MAX_VALUE)).getOrder());
                    }
                }
                break;
            case "SetWarp":
                if(SELECTED_ID.size() == 1){
                    Entity ship = EntityList.getEntity(SELECTED_ID.get(0));
                    if(ship instanceof Ship){
                        Client.getConnection().sendObject("[ORDER][(" + Client.getID() + ")(" + ship.getID() + ")]"+ (new Warp(Double.parseDouble(parameter),Double.MAX_VALUE)).getOrder());
                    }
                }
                break;
            case "AttackON":  
                weaponsActive = true;
                break;
            case "AttackOFF":  
                weaponsActive = false;
                break;
            case "AutoFireON":  
                autofire = true;
                break;
            case "AutoFireOFF":  
                autofire = false;
                break;
            
        }
        
    }
    
    
    public boolean autoFireAllowed(){
        return autofire;
    }
    
    public boolean weaponsActive(){
        return weaponsActive;
    }
    
    
    
}
