/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.entities.structures;

import engine.entities.Entity;
import engine.entities.miscellaneous.DetectionRadius;
import engine.entities.ships.Ship;
import engine.gameMechanics.factions.Faction;
import engine.gameMechanics.factions.FactionList;
import java.util.ArrayList;
import engine.main.CycleRunner;
import engine.main.EntityList;
import engine.physics.*;

/**
 *
 * @author Christopher
 */
public class WarpGate extends Structure{
    private boolean acceptIn = true, acceptOut = true;
    private final int indexOfReceiver;
    private final double maxEnergy = 250000000 * Math.pow(c,2), energyPerSecond = 100000;
    private double energy = maxEnergy;
    
    double cycle = 0;
    
    
    public WarpGate(double X, double Y, double Z, double M, double R, int index) {
        super(X, Y, Z, M, R);
        indexOfReceiver = index;
        
    }
    
    public WarpGate(Orbit o, double MaE, double M, double R, int index){
        super(o,MaE,M,R);
        indexOfReceiver = index;
    }
    
    public boolean testCollision(Entity other){
        if(!super.testCollision(other)){
            return false;
        }
        
        double distanceX = other.getX() - this.x;
        double distanceY = other.getY() - this.y;
        double distanceZ = other.getZ() - this.z;
        double distanceXZ = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceZ,2));
        double distance = Math.sqrt(Math.pow(distanceXZ,2) + Math.pow(distanceY,2));
        
        double ringDistance = Math.sqrt(Math.pow(radius - distanceXZ,2) + Math.pow(distanceY,2));
        
        if(ringDistance < other.getRadius() || distance < other.getRadius()){
            return true;
        }
        return false;
    }
    
    public boolean warpAllowed(Ship other){
        double energyRequired = other.getMass() * Math.pow(c,2);
        if(energyRequired > energy || !super.testCollision(other)){
            return false;
        }
        double distanceX = other.getX() - this.x;
        double distanceY = other.getY() - this.y;
        double distanceZ = other.getZ() - this.z;
        double distanceXZ = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceZ,2));
        double distance = Math.sqrt(Math.pow(distanceXZ,2) + Math.pow(distanceY,2));
        
        if(distance < other.getRadius()){
            return true;
        } else {
            return false;
        }
        
    }
    
    public void collide(Ship other){
        double distanceX = other.getX() - this.x;
        double distanceY = other.getY() - this.y;
        double distanceZ = other.getZ() - this.z;
        double distanceXZ = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceZ,2));
        double distance = Math.sqrt(Math.pow(distanceXZ,2) + Math.pow(distanceY,2));
        double ringDistance = Math.sqrt(Math.pow(radius - distanceXZ,2) + Math.pow(distanceY,2));
        
        if(distance < other.getRadius()){
            sendShip(other);
        }
        if(testCollision(other) && ringDistance < other.getRadius()){
            super.collide(other);
        }
    }
    
    @Override
    public void act() {
        energy += energyPerSecond * CycleRunner.getTimeWarp() / CycleRunner.cyclesPerSecond;
        
        if(energy > maxEnergy){
            energy = maxEnergy;
        }
        
        
        if(cycle == 0.0){
            //Finds a new owner, if applicable
            DetectionRadius d = new DetectionRadius(x,y,z,1000);

            ArrayList<Integer> IDvalues = new ArrayList<>();
            for(Faction f : FactionList.getFactionList()){
                ArrayList<Long> members = f.getMembers();
                for(long l : members){
                    Entity e = EntityList.getEntity(l);
                    if(e != null && e instanceof Ship && e.testCollision(d)){
                        IDvalues.add(f.getID());
                        break;
                    }
                }
            }

            if(IDvalues.size() == 1){
                owner = IDvalues.get(0);
            }
        
        }
        
        cycle += CycleRunner.getTimeWarp();
        if(cycle >= CycleRunner.cyclesPerSecond){
            cycle = 0;
        }
    }

    public void sendShip(Ship s){
        //This should keep non-allied forces from sharing warp gates
        if(getOwner() >= 0 && (s.getFactionID() != getOwner() && (s.getFactionID() >= 0))){
            if(FactionList.getFaction(owner).getDiplomaticStatus(s.getFactionID()) > -1);
                return;
        }
        
        double energyRequired = s.getMass() * Math.pow(c,2);
        
        if(testCollision(s) || energyRequired > energy){
            return;
        } else {
            energy -= energyRequired;
        }
        
        WarpGate w = (WarpGate) EntityList.getStructure(indexOfReceiver);
        double relVel = s.getSpeedY() - velY;
        
        w.receiveShip(s, relVel);
        
    }
    
    /**
     * Receives a Ship through a warp gate, therefore teleporting it
     * @param s
     * @param relVelY
     */
    public void receiveShip(Ship s, double relVelY) {
        //This should keep non-allied forces from sharing warp gates
        if(getOwner() >= 0 && (s.getFactionID() != getOwner() && (s.getFactionID() >= 0))){
            if(FactionList.getFaction(owner).getDiplomaticStatus(s.getFactionID()) > -1);
                return;
        }
        double energyRequired = s.getMass() * Math.pow(c,2);
        if(energyRequired > energy){
            return;
        } else {
            energy -= energyRequired;
        }
        
        s.setX(x);
        if(relVelY > 0){
            s.setY(y + s.getRadius());
        } else {
            s.setY(y - s.getRadius());
        }
        s.setZ(z);
        s.setSpeedX(velX);
        s.setSpeedY(velY + relVelY);
        s.setSpeedZ(velZ);
    }
    
}
