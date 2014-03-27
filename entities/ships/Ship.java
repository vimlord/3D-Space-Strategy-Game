/*
 * NOTES ON SHIP MASSES:
 * Rotation speeds will be established so that the speed will be 5 degree/second
 * when the ship weighs 10 million kilograms
 * For a ship of that mass, the acceleration rate for firing engines will be 40 m/s^2
 */
 
package entities.ships;
 
import entities.*;
import main.CycleRunner;
 
/**
 *
 * @author Christopher Hittner
 */
public class Ship extends Entity implements ControlSystem{
    protected final double maxHealth;
    protected double health;
    
    protected double XZ_ROT = 0, Y_ROT = 0;
    protected double XZ_RotSpeed = 0, Y_RotSpeed = 0;
    protected double XZ_ROT_Target = 0, Y_ROT_Target = 0;
    
    protected boolean rotationTarget = false;
     
    protected double throttle = 0;
    
    protected double warpCharge = 0, warpCapacity = 60000, warpMinimum = 10000;
    protected boolean warpCharging = false, warping = false;
    protected int warpMode = 0;
    
    /**
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius/size of the ship's hit box
     */
    public Ship(double X, double Y, double Z, double M, double R){
        super(X, Y, Z, M, R);
        //Sets the maximum and current health
        maxHealth = Math.sqrt(mass/1000);
        health = maxHealth;
    }
     
    public void move(){
        super.move();
        autopilot();
        rotate();
        accelerate();
        chargeWarpDrive();
        warp();
    }
     
    
    //--------------------------------------------------------------------------
    //Rotation
    //--------------------------------------------------------------------------
     
    /**
     *
     * @param XZ The ratio amount of XZ to rotate by
     * @param Y The ratio amount of Y to rotate by
     * @param magnitude The percent of the ship's overall rotation ability to rotate by
     */
    public void setRotation(double XZ, double Y, double magnitude){
        double originalMagnitude = Math.sqrt(Math.pow(XZ, 2) + Math.pow(Y, 2));
        double horiz = (XZ/originalMagnitude) * (magnitude/100);
        double vert = (Y/originalMagnitude) * (magnitude/100);
        if(XZ == 0 && Y == 0){
            XZ_RotSpeed = 0;
            Y_RotSpeed = 0;
        } else {
            XZ_RotSpeed = 5.0 * Math.toRadians((10000000.0/mass) * horiz)/CycleRunner.cyclesPerSecond;
            Y_RotSpeed = 5.0 * Math.toRadians((10000000.0/mass) * vert)/CycleRunner.cyclesPerSecond;
        }
    }
     
    /**
     * Performs rotation based on specified values
     */
    public void rotate(){
        XZ_ROT += XZ_RotSpeed/CycleRunner.cyclesPerSecond;
        Y_ROT += Y_RotSpeed/CycleRunner.cyclesPerSecond;
        
        //Corrections in case the ship rotates beyond a certain degree value
        if(Y_ROT > Math.toRadians(90.0)){
            Y_ROT -= Math.toRadians(180.0);
            Y_ROT_Target -= Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180.0);
            XZ_ROT_Target += Math.toRadians(180.0);
        } else if(Y_ROT < Math.toRadians(-90.0)){
            Y_ROT += Math.toRadians(180.0);
            Y_ROT_Target += Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180);
            XZ_ROT_Target += Math.toRadians(180.0);
        }
        if(XZ_ROT >= Math.toRadians(360.0)){
            XZ_ROT -= Math.toRadians(360.0);
            XZ_ROT_Target -= Math.toRadians(360.0);
        } else if(XZ_ROT < 0.0){
            XZ_ROT += Math.toRadians(360.0);
            XZ_ROT_Target += Math.toRadians(360.0);
        }
    }
     
    public void setRotationTarget(double XZ, double Y){
        XZ_ROT_Target = XZ;
        Y_ROT_Target = Y;
        rotationTarget = true;
    }
    
    
    //--------------------------------------------------------------------------
    //Autopilot
    //--------------------------------------------------------------------------
    
    
    public void autopilot(){
        if(rotationTarget){
            int xz = 0;
            int y = 0;
            
            if(Math.abs(XZ_ROT - XZ_ROT_Target) < Math.toRadians(0.5)){
                xz = 0;
            } else if(XZ_ROT < XZ_ROT_Target){
                xz = 1;
            } else if(XZ_ROT > XZ_ROT_Target){
                xz = -1;
            }
            
            if(Math.abs(Y_ROT - Y_ROT_Target) < Math.toRadians(0.5)){
                y = 0;
            } else if(Y_ROT < Y_ROT_Target){
                y = 1;
            } else if(Y_ROT > Y_ROT_Target){
                y = -1;
            }
            
            setRotation(xz, y, 100);
            
            if(xz == 0 && y == 0){
                rotationTarget = false;
            }
            
        }
    }
 
    
    //--------------------------------------------------------------------------
    //Acceleration
    //--------------------------------------------------------------------------
    
    /**
     * Sets the value of the throttle to determine how quickly the ship will accelerate
     * @param thrustPercent The throttle level, in percent
     */
    public void setAcceleration(double thrustPercent){
        if(thrustPercent >= 0 && thrustPercent <= 100)
            throttle = thrustPercent;
    }
     
    /**
     * Accelerates the ship in the direction it is pointing
     */
    public void accelerate() {
        velX += 0.4 * throttle * Math.cos(XZ_ROT) * Math.cos(Y_ROT) * (10000000.0/mass)/CycleRunner.cyclesPerSecond;
        velZ += 0.4 * throttle * Math.sin(XZ_ROT) * Math.cos(Y_ROT) * (10000000.0/mass)/CycleRunner.cyclesPerSecond;
        velY += 0.4 * throttle * Math.sin(Y_ROT) * (10000000.0/mass)/CycleRunner.cyclesPerSecond;
    }
    
    
    //--------------------------------------------------------------------------
    //Warp Drive
    //--------------------------------------------------------------------------
    
    
    /**
     * Sets the warp mode of the Ship (in multiples of the speed of light)
     * @param level
     */
    public void setWarp(int level){
        if(level > 0){
            warpMode = level;
        }
    }
    
    /**
     * Determines whether the Ship object is prepping for a warp jump
     * @param b
     */
    public void setWarpPrep(boolean b){
        warpCharging = b;
    }
    
    /**
     * Powers the warp drive up if it can be done
     */
    public void chargeWarpDrive(){
        if(((warpMode == 0 && warpCharging) || warpCharge < warpMinimum)){
            
            warpCharge += (10000000/mass) * 200;
            
            if(warpCharge > warpCapacity){
                warpCharge = warpCapacity;
            }
            
        } else if(warpMode > 0){
            
            warpCharging = false;
            warpCharge -= Math.pow(warpMode,2) * (mass/10000000);
            
            if(warpCharge < 0){
                warpCharge = 0;
            }
            
        }
    }
    
    
    /**
     * Performs a frame of the warp jump in the physics of the game
     */
    public void warp(){
        if(!warping){
            if(warpCharge >= warpMinimum && warpMode > 0){
                warping = true;
            }
        } else {
            if(warpMode == 0){
                warping = false;
            }
        }
        
        //Error check that keeps people from using an infinitely large warp power
        if(Math.pow(warpMode,2) > warpCharge){
            warpMode = (int)(Math.sqrt(warpCharge));
        }
        
        if(warping){
            x += (c * warpMode) * Math.cos(XZ_ROT) * Math.cos(Y_ROT);
            y += (c * warpMode) * Math.sin(Y_ROT);
            z += (c * warpMode) * Math.sin(XZ_ROT) * Math.cos(Y_ROT);
        }
    }
    
    
    //---------------------------------------------------------------------------
    //Collision stuff
    //---------------------------------------------------------------------------
    
    public void collide(Entity other){
        relVelX = this.velX - other.velX;
        relVelY = this.velY - other.velY;
        relVelZ = this.velZ - other.velZ;
        relVel = Math.sqrt(Math.pow(relVelX,2) + Math.pow(relVelY,2) + Math.pow(relVelY,2));
        
        this.health -= Math.sqrt(other.mass/this.mass) * Math.pow(relVel,4) / this.mass;
        
        if(e instanceOf Ship){
            other.health -= Math.sqrt(this.mass/other.mass) * Math.pow(relVel,4) / other.mass;
        }
        
        super.collide(other);
    }
    
    
    //---------------------------
    //Data return methods
    //---------------------------
    
    
    /**
     * Returns the ship's health
     * @return The Ship's health
     */
    public double getHealth(){
        return health;
    }
    
    /**
     * Returns the rotation on the XZ plane
     * @return The XZ Rotation
     */
    public double getXZ_ROT(){
        return XZ_ROT;
    }
    
    /**
     * Returns the rotation on the Y axis
     * @return The Y Rotation
     */
    public double getY_ROT(){
        return Y_ROT;
    }
    /**
     * Returns the rotation speed on the XZ plane
     * @return The XZ Rotation speed
     */
    public double getXZ_RotSpeed(){
        return XZ_RotSpeed;
    }
    
    /**
     * Returns the rotation speed on the Y axis
     * @return The Y Rotation speed
     */
    public double getY_RotSpeed(){
        return Y_RotSpeed;
    }
    
    
}

