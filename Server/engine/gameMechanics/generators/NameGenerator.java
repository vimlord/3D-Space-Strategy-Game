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
        shipNames.add("Alcubierre"); // Alcubierre Drive theory
        shipNames.add("Archimedes"); //Greek mathematician
        shipNames.add("Ares"); //Greek god of war
        shipNames.add("Arizona"); //In memory of the crew lost on the USS Arizona
        shipNames.add("Armstrong"); //Neil Armstrong; first human on the Moon.
        shipNames.add("Athena"); //Greek goddess
        shipNames.add("Atlantis"); //Space Shuttle
        shipNames.add("Bradbury"); //Ray Bradbury
        shipNames.add("Card"); //Orson Scott Card; Ender's Game
        shipNames.add("Challenger"); //In memory of the crew lost on the Space Shuttle Challenger
        shipNames.add("Churchill"); //Winston Churchill
        shipNames.add("Columbia"); //In memory of the crew lost on the Space Shuttle Columbia
        shipNames.add("Daedalus"); //Greek epic
        shipNames.add("Discovery"); //Space Shuttle
        shipNames.add("Einstein"); //Famous physicist
        shipNames.add("Eisenhower"); //Dwight D. Eisenhower
        shipNames.add("Enterprise"); //USS Enterprise from Star Trek
        shipNames.add("Gagarin"); //Yuri Gagarin; first human in space
        shipNames.add("Goddard"); //John Goddard; first liquid fuel rocket
        shipNames.add("Guardian");
        shipNames.add("Halley"); //Edmund Halley
        shipNames.add("Hades"); //Greek god of the underworld
        shipNames.add("Heinlein"); //Robert A. Heinlein; Starship Troopers
        shipNames.add("Hercules"); //Greek demigod
        shipNames.add("Hubble"); //Edwin Hubble
        shipNames.add("Icarus"); //Greek tale
        shipNames.add("Kennedy"); //John F. Kennedy
        shipNames.add("Leviathan");
        shipNames.add("Mayflower"); //Pilgrim ship
        shipNames.add("Monitor"); //US Submarine in the Civil War
        shipNames.add("Nautilus"); //US Ship name
        shipNames.add("Newton"); //Sir Isaac Newton
        shipNames.add("Nimitz"); //US Ship name
        shipNames.add("Odin"); //Norse God
        shipNames.add("Odysseus"); //Greek epic
        shipNames.add("Pauil"); // Pauli Exclusion Principle 
        shipNames.add("Patton"); //General George S. Patton
        shipNames.add("Poseidon"); //Greek god of the seas
        shipNames.add("Schwarzchild"); //Schwarzchild Radius
        shipNames.add("Shepard"); //Alan Shepard, first American in space
        shipNames.add("Titan"); //Fathers of the Greek gods
        shipNames.add("Thor"); //Norse god of thunder
        shipNames.add("Valiant");
        shipNames.add("Valkyrie");
        shipNames.add("Vengeance");
        shipNames.add("Von Braun"); //Wehrner von Braun
        shipNames.add("Voyager"); //Voyager 1 and 2
        shipNames.add("Washington"); //General and President George Washington
        shipNames.add("Wright"); //Orville and Wilbur Wright; built the first aircraft
        shipNames.add("Zeus"); //Greek god of gods
        
        shipNames.add(6, "Lucifer"); //Satan's number... hell, why not?
        shipNames.add(42, "Heart of Gold"); // Hitchhiker's Guide to the Galaxy; the answer is 42
        
    }
    
    
}
