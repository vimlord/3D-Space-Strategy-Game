/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.settings;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Options {
    
    private static final ArrayList<Option> options = new ArrayList<>();
    
    public static void addOption(Option o){
        options.add(o);
    }
    
    public static void addOption(String name, int code){
        options.add(new Option(name, code));
    }
    
    public static Option getOption(String name){
        for(Option o : options){
            if(o.name.equals(name)){
                return o;
            }
        }
        
        return null;
        
    }
    
    public static Option getOption(int code){
        for(Option o : options){
            if(o.getCode() == code){
                return o;
            }
        }
        
        return null;
        
    }
    
    public static void useOption(int code){
        
    }
    
    public static void setCode(String name, int code){
        for(Option o : options){
            if(o.name.equals(name)){
                o.setCode(code);
            }
        }
    }
    
    public static void loadPredeterminedOptions(){
        options.clear();
        
        //Movement controls
        addOption("MOVE_FORWARD", KeyEvent.VK_I);
        addOption("MOVE_BACKWARD", KeyEvent.VK_K);
        addOption("MOVE_LEFT", KeyEvent.VK_J);
        addOption("MOVE_RIGHT", KeyEvent.VK_L);
        addOption("MOVE_UP", KeyEvent.VK_H);
        addOption("MOVE_DOWN", KeyEvent.VK_N);
        
        /*
        //Rotation controls; they're going to be removed for now
        //so that mouse rotation can be trialed
        addOption("ROTATE_LEFT", KeyEvent.VK_W);
        addOption("ROTATE_RIGHT", KeyEvent.VK_S);
        addOption("ROTATE_UP", KeyEvent.VK_A);
        addOption("ROTATE_DOWN", KeyEvent.VK_D);
        
        //Zoom controls; they're going to be removed for now
        //so that mouse zooming can be trialed
        addOption("ZOOM_IN", KeyEvent.VK_E);
        addOption("ZOOM_OUT", KeyEvent.VK_Q);
        */
        
        addOption("CHANGE_VIEW", KeyEvent.VK_V);
        
    }
    
}
