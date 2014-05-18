/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.miscellaneous;

import entities.Entity;

/**
 *
 * @author Christopher Hittner
 */
public class DetectionRadius extends Entity {
    public DetectionRadius(double X, double Y, double Z, double R){
        super(X,Y,Z,0,R);
    }
    
    public boolean testCollision(Entity other){
        return false;
    }
}
