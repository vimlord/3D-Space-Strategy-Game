/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu;

import client.game.GameControlSettings;
import client.main.Client;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class MenuManager {
    private static ArrayList<Menu> menus = new ArrayList<>();
    private static int menuIndex = -1;
    
    public static void addMenu(Menu m){
        menus.add(m);
        if(menuIndex == -1)
           menuIndex = 0;
        
    }

    /**
     * Removes the menu at the current index
     * @param index
     */
    public static void removeMenu(int index){
        menus.remove(index);
        
        if(index < menuIndex){ //The index removed was below that of the current menu
            menuIndex--;
        }
        
        if((menus.isEmpty() || index == menuIndex) && menuIndex >= 0) //The current menu is gone!
            menuIndex = -1;
        
    }
    
    /**
     * Sets the current menu to a new menu
     * @param index
     */
    public static void setMenu(int index){
        if(index > -1 && index < menus.size())
            menuIndex = index;
    }
    
    public static void setMenu(String name){
        for(int i = 0; i < menus.size(); i++){
            if(menus.get(i).getName().equals(name)){
                setMenu(i);
                return;
            }
        }
    }

    /**
     * Gets the Menu at a specific index
     * @param index
     * @return
     */
    public static Menu getMenu(int index){
        return menus.get(index);
    }
    
    /**
     * Gets the Menu at a specific index
     * @param index
     * @return
     */
    public static Menu getMenu(String name){
        for(Menu m : menus){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }
    
    /**
     * Retrieves the currently active menu
     * @return
     */
    public static Menu getMenu(){
        try {
            return menus.get(menuIndex);
        } catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    
    public static int getMenuIndex(Menu m){
        for(int i = 0; i < menus.size(); i++){
            if(m.equals(menus.get(i))){
                return i;
            }
        }
        
        return -1;
    }
    
    public static int getMenuIndex(){
        return menuIndex;
    }
    
   /**
    * Draws the current menu
    */
    public static void drawCurrentMenu(Graphics g){
        
        if(menuIndex < 0){
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        getMenu().cycle();
        getMenu().drawMenu(g2);
    }
    
    /**
     * Presses applicable buttons and executes their orders
     * @param x
     * @param y
     */
    public static void executeButtons(int x, int y){
        //Gets the menu's order
        String order = getMenu().clickButtons(x,y);
        
        if(order == null) //This should save a LOT of processing time
            return;
        
        String header = order.substring(0,order.indexOf("]") + 1);
        String footer = order.substring(order.indexOf("]") + 1);
        
        if(header.equals("[SETMENU]")){ //A menu change request has been made
            try{
                setMenu(Integer.parseInt(footer.substring(1,footer.length()-1))); //The program will attempt to set the menu to the parameter provided
            } catch(NumberFormatException e){
                String name = footer.substring(0,footer.length());
                setMenu(name);
            }
        } else if(header.equals("[ORDER]")){
            Client.getConnection().sendObject(order);
        } else if(header.equals("[END]")){
            System.exit(0);
        } else if(header.equals("[SHIP_ORDER]")) {
            GameControlSettings.processButtonCommand(footer);
        }
        
    }
    
    
    
    
}
