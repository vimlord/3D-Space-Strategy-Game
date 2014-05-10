/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.structures;

import entities.*;
import gameMechanics.factions.Faction;
import gameMechanics.factions.FactionList;
import gameMechanics.factions.FactionTag;
import physics.Orbit;

/**
 *
 * @author Christopher
 */
public abstract class Structure extends Entity implements FactionTag{
    int owner;
    int factionID;
    
    public Structure(double X, double Y, double Z, double M, double R) {
        super(X, Y, Z, M, R);
    }
    
    public Structure(Orbit o, double MaE, double M, double R){
        super(o.getX(),o.getY(),o.getZ(), M, R);
    }
    
    public abstract void act();
    
    public void setOwner(int i){
        owner = i;
    }
    
    public int getOwner(){
        return owner;
    }

    @Override
    public long setFactionID(int ID) {
        Faction joining = FactionList.getFaction(ID);
        if(joining == null)
            return -1;
        FactionList.getFaction(ID).addMember(this);
        factionID = ID;
        return getID();
    }

    @Override
    public int getFactionID() {
        return factionID;
    }
    
}
