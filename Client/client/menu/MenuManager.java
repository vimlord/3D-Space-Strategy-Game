/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import client.main.Client;

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
        if(menus.size() == 0 || index == menuIndex) //The current menu is gone!
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

    /**
     * Gets the Menu at a specific index
     * @param index
     * @return
     */
    public static Menu getMenu(int index){
        return menus.get(index);
    }

    /**
     * Retrieves the currently active menu
     * @return
     */
    public static Menu getMenu(){
        return menus.get(menuIndex);
    }
    
   /**
    * Draws the current menu
    */
    public static void drawCurrentMenu(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        getMenu().drawButtons(g);
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
        String footer = order.substring(order.indexOf("]"));
        
        if(header.equals("[SETMENU]")){ //A menu change request has been made
            setMenu(Integer.parseInt(footer.substring(1,footer.length()-1))); //The program will attempt to set the menu to the parameter provided
        } else if(header.equals("[ORDER]")){
            Client.sendObject(order);
        }
        
        
    }
    
    
}
