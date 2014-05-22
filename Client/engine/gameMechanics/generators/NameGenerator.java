/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gameMechanics.generators;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class NameGenerator {
    static ArrayList<String> shipNames = null;
        
    public static String shipName(){
        if(shipNames == null){
            generateShipNames();
        }
        if(shipNames.size() > 0){
            int index = (int)(Math.random() * (shipNames.size() - 1));
            String choice = shipNames.get(index);
            shipNames.remove(index);
            return choice;
        } else {
            return "Unnamed Ship";
        }
        
    }
    
    public static void generateShipNames(){
        shipNames = new ArrayList<>();
        shipNames.add("Archimedes");
        shipNames.add("Ares");
        shipNames.add("Arizona");
        shipNames.add("Armstrong");
        shipNames.add("Athena");
        shipNames.add("Card");
        shipNames.add("Daedalus");
        shipNames.add("Einstein");
        shipNames.add("Enterprise");
        shipNames.add("Guardian");
        shipNames.add("Halley");
        shipNames.add("Heinlein");
        shipNames.add("Hercules");
        shipNames.add("Hubble");
        shipNames.add("Icarus");
        shipNames.add("Kennedy");
        shipNames.add("Mayflower");
        shipNames.add("Monitor");
        shipNames.add("Nautilus");
        shipNames.add("Newton");
        shipNames.add("Nimitz");
        shipNames.add("Odin");
        shipNames.add("Odysseus");
        shipNames.add("Poseidon");
        shipNames.add("Thor");
        shipNames.add("Valiant");
        shipNames.add("Valkyrie");
        shipNames.add("Vengeance");
        shipNames.add("Voyager");
        shipNames.add("Washington");
        shipNames.add("Zeus");
        
        
        
    }
    
    
}
