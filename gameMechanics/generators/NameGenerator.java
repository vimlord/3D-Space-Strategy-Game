/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameMechanics.generators;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class NameGenerator {
    static ArrayList<String> shipNames = null;
    static ArrayList<Boolean> shipTaken = null;
        
    public static String shipName(){
        if(shipNames == null){
            generateShipNames();
        }
        
        int index = (int)(Math.random() * shipNames.size());
        
        if(!shipTaken.get(index)){
            if(index != shipNames.size() - 1)
                shipTaken.set(index,true);
            return shipNames.get((int)(Math.random() * (shipNames.size() - 1)));
        } else
            return shipName();
    }
    
    public static void generateShipNames(){
        shipNames = new ArrayList<>();
        shipNames.add("Nimitz");
        shipNames.add("Heinlein");
        shipNames.add("Enterprise");
        shipNames.add("Armstrong");
        shipNames.add("Kennedy");
        shipNames.add("Daedalus");
        shipNames.add("Archimedes");
        shipNames.add("Nautilus");
        shipNames.add("Zeus");
        shipNames.add("Poseidon");
        shipNames.add("Ares");
        shipNames.add("Thor");
        shipNames.add("Odin");
        shipNames.add("Voyager");
        shipNames.add("Newton");
        shipNames.add("Halley");
        shipNames.add("Hubble");
        shipNames.add("Vengeance");
        shipNames.add("Valiant");
        shipNames.add("Monitor");
        shipNames.add("Valkyrie");
        shipNames.add("Guardian");
        
        shipNames.add("Unnamed Ship");
        
        shipTaken = new ArrayList<>();
        for(int i = 0; i < shipNames.size(); i++){
            shipTaken.add(false);
        }
    }
    
    
}
