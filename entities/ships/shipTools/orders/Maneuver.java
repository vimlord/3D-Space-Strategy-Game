/*
 * This class stores data for a maneuver that should be executed
 */

package entities.ships.shipTools.orders;

import entities.Entity;
import entities.ships.Ship;

/**
 *
 * @author Christopher Hittner
 */
public class Maneuver extends AdvancedOrder{
    
    /**
     * Creates a maneuver for a ship to execute. It is assumed that the data is
     * not relative to an orbiting body It won't work if the Ship is not moving.
     * @param ship  The ship that is executing the maneuver
     * @param PG_RG The velocity change along the prograde/retrograde vector. Positive is prograde
     * @param N_AN The velocity change along the normal/antinormal vector. Positive goes up on the y-axis
     * @param rIn_rOut The velocity change along the radial in/out vector. Positive goes to left of prograde
     */
    public Maneuver(Ship ship, double PG_RG, double N_AN, double rIn_rOut) {
        order = "(MNVR)";
        
        double velX = ship.getSpeedX();
        double velY = ship.getSpeedY();
        double velZ = ship.getSpeedZ();
        double velXZ = Math.sqrt(Math.pow(velX,2) + Math.pow(velZ,2));
        double vel = Math.sqrt(Math.pow(velXZ,2) + Math.pow(velY,2));
        
        
        //Adds the values for the prograde/retrograde vector
        double progradeXZ = Math.cosh(velX/velXZ);
        if(velZ < 0){
            progradeXZ *= -1;
        }
        double progradeY = Math.sinh(velY/vel);
        
        double nodeX = PG_RG * (Math.cos(progradeXZ) * Math.cos(progradeY));
        double nodeY = PG_RG * Math.sin(progradeY);
        double nodeZ = PG_RG * (Math.sin(progradeXZ) * Math.cos(progradeY));
        
        
        
        //Adds the values for the normal/antinormal vector
        double normalXZ = Math.cosh(velX/velXZ);
        if(velZ < 0){
            normalXZ *= -1;
        }
        double normalY = Math.sinh(velY/vel) + Math.toRadians(90);
        
        nodeX += N_AN * (Math.cos(normalXZ) * Math.cos(normalY));
        nodeY += N_AN * Math.sin(normalY);
        nodeZ += N_AN * (Math.sin(normalXZ) * Math.cos(normalY));
        
        
        //Adds the values for the radial in/out vector
        double radialXZ = Math.cosh(velX/velXZ) + Math.toRadians(90);
        if(velZ < 0){
            radialXZ *= -1;
        }
        double radialY = 0;
        
        nodeX += rIn_rOut * (Math.cos(radialXZ) * Math.cos(radialY));
        nodeY += rIn_rOut * Math.sin(radialY);
        nodeZ += rIn_rOut * (Math.sin(radialXZ) * Math.cos(radialY));
        
        double nodeXZ = Math.sqrt(Math.pow(nodeX, 2) + Math.pow(nodeZ, 2));
        double magnitude = Math.sqrt(Math.pow(nodeXZ, 2) + Math.pow(nodeY, 2));
        
        order += "(" + magnitude + ")";
        
        double nodeAngleXZ = Math.cosh(nodeX/nodeXZ);
        if(nodeZ < 0){
            nodeAngleXZ *= -1;
        }
        order += "(" + nodeAngleXZ + ")";
        double nodeAngleY = Math.sinh(nodeY/magnitude);
        order += "(" + nodeAngleY + ")";
        
    }
    
    public Maneuver(Ship ship, Entity orbiting, double PG_RG, double N_AN, double rIn_rOut) {
        order = "(MNVR)";
        
        double velX = ship.getSpeedX() - orbiting.getSpeedX();
        double velY = ship.getSpeedY() - orbiting.getSpeedY();
        double velZ = ship.getSpeedZ() - orbiting.getSpeedZ();
        double velXZ = Math.sqrt(Math.pow(velX,2) + Math.pow(velZ,2));
        double vel = Math.sqrt(Math.pow(velXZ,2) + Math.pow(velY,2));
        
        double relX = ship.getX() - orbiting.getX();
        double relY = ship.getY() - orbiting.getY();
        double relZ = ship.getZ() - orbiting.getZ();
        double relXZ = Math.sqrt(Math.pow(relX,2) + Math.pow(relZ,2));
        double rel = Math.sqrt(Math.pow(relXZ,2) + Math.pow(relY,2));
        
        
        //Adds the values for the prograde/retrograde vector
        double progradeXZ = Math.cosh(velX/velXZ);
        if(velZ < 0){
            progradeXZ *= -1;
        }
        double progradeY = Math.sinh(velY/vel);
        
        double nodeX = PG_RG * (Math.cos(progradeXZ) * Math.cos(progradeY));
        double nodeY = PG_RG * Math.sin(progradeY);
        double nodeZ = PG_RG * (Math.sin(progradeXZ) * Math.cos(progradeY));
        
        
        
        //Adds the values for the normal/antinormal vector
        double normalXZ = Math.cosh(velX/velXZ);
        if(velZ < 0){
            normalXZ *= -1;
        }
        double normalY = Math.sinh(velY/vel) + Math.toRadians(90);
        
        nodeX += N_AN * (Math.cos(normalXZ) * Math.cos(normalY));
        nodeY += N_AN * Math.sin(normalY);
        nodeZ += N_AN * (Math.sin(normalXZ) * Math.cos(normalY));
        
        
        //Adds the values for the radial in/out vector
        double radialXZ = Math.cosh(velX/velXZ) + Math.toRadians(90);
        if(velZ < 0){
            radialXZ *= -1;
        }
        double radialY = 0;
        
        nodeX += rIn_rOut * (Math.cos(radialXZ) * Math.cos(radialY));
        nodeY += rIn_rOut * Math.sin(radialY);
        nodeZ += rIn_rOut * (Math.sin(radialXZ) * Math.cos(radialY));
        
        double nodeXZ = Math.sqrt(Math.pow(nodeX, 2) + Math.pow(nodeZ, 2));
        double magnitude = Math.sqrt(Math.pow(nodeXZ, 2) + Math.pow(nodeY, 2));
        
        order += "(" + magnitude + ")";
        
        double nodeAngleXZ = Math.cosh(nodeX/nodeXZ);
        if(nodeZ < 0){
            nodeAngleXZ *= -1;
        }
        order += "(" + nodeAngleXZ + ")";
        double nodeAngleY = Math.sinh(nodeY/magnitude);
        order += "(" + nodeAngleY + ")";
        
    }
        
}



