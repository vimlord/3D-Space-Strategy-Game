/*
 * NOTES ON SHIP MASSES:
 * Rotation speeds will be established so that the speed will be 5 degree/second
 * when the ship weighs 10 million kilograms
 * For a ship of that mass, the acceleration rate for firing engines will be 40 m/s^2
 */
 
package entities.ships;
 
import entities.Entity;
import main.CycleRunner;
 
/**
 *
 * @author Christopher Hittner
 */
public class Ship extends Entity{
    protected double XZ_ROT = 0, Y_ROT = 0;
    protected double XZ_RotSpeed = 0, Y_RotSpeed = 0;
    protected double XZ_ROT_Target = 0, Y_ROT_Target = 0;
    protected double XZ_ROT_MidPt, Y_ROT_MidPt;
    
    protected boolean rotationTarget = false;
    protected boolean readyToRotate = true;
     
    protected double throttle = 0;
     
    /**
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius/size of the ship's hit box
     */
    public Ship(double X, double Y, double Z, double M, double R){
        super(X, Y, Z, M, R);
    }
     
    public void move(){
        super.move();
        autopilot();
        rotate();
        accelerate();
    }
     
     
    /**
     *
     * @param XZ The ratio amount of XZ to rotate by
     * @param Y The ratio amount of Y to rotate by
     * @param magnitude The percent of the ship's overall rotation ability to rotate by
     */
    public void addRotation(double XZ, double Y, double magnitude){
        if(magnitude > 100 || magnitude < 0){
            return;
        }
        double originalMagnitude = Math.sqrt(Math.pow(XZ, 2) + Math.pow(Y, 2));
        double horiz = (XZ/originalMagnitude) * (magnitude/100);
        double vert = (Y/originalMagnitude) * (magnitude/100);
         
        XZ_RotSpeed += 5.0 * Math.toRadians((10000000.0/mass) * horiz)/CycleRunner.cyclesPerSecond;
        Y_RotSpeed += 5.0 * Math.toRadians((10000000.0/mass) * vert)/CycleRunner.cyclesPerSecond;
    }
     
    /**
     * Performs rotation based on specified values
     */
    public void rotate(){
        XZ_ROT += XZ_RotSpeed/CycleRunner.cyclesPerSecond;
        Y_ROT += Y_RotSpeed/CycleRunner.cyclesPerSecond;
        if(Y_ROT > Math.toRadians(90.0)){
            Y_ROT -= Math.toRadians(180.0);
            Y_ROT_MidPt -= Math.toRadians(180.0);
            Y_RotTarget -= Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180.0);X
            XZ_ROT_MidPt += Math.toRadians(180.0);
            XZ_RotTarget += Math.toRadians(180.0);
        } else if(Y_ROT < Math.toRadians(-90.0)){
            Y_ROT += Math.toRadians(180.0);
            Y_ROT_MidPt += Math.toRadians(180.0);
            Y_RotTarget += Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180);
            XZ_ROT_MidPt += Math.toRadians(180.0);
            XZ_RotTarget += Math.toRadians(180.0);
        }
        if(XZ_ROT >= Math.toRadians(360.0)){
            XZ_ROT -= Math.toRadians(360.0);
            XZ_ROT_MidPt -= Math.toRadians(360.0);
            XZ_RotTarget -= Math.toRadians(360.0);
        } else if(XZ_ROT < 0.0){
            XZ_ROT += Math.toRadians(360.0);
            XZ_ROT_MidPt += Math.toRadians(360.0);
            XZ_RotTarget += Math.toRadians(360.0);
        }
    }
     
    public void setRotationTarget(double XZ, double Y){
        XZ_ROT_Target = XZ;
        Y_ROT_Target = Y;
        readyToRotate = false;
    }
     
    public void autopilot(){
        //This section of the autopilot will rotate the craft so that it will execute rotation commands
        double XZ_dist = Math.abs(XZ_ROT - XZ_ROT_Target);
        double Y_dist = Math.abs(Y_ROT - Y_ROT_Target);
        double dist =  Math.sqrt(Math.pow(XZ_dist, 2) + Math.pow(Y_dist, 2));
        
        //Stops the rotation if a new target is selected
        if(!readyToRotate && Math.abs(Math.sqrt(Math.pow(XY_RotSpeed,2) + Math.pow(XY_RotSpeed,2))) < 0.5){
                XZ_ROT_MidPt = (XZ_ROT_Target + XZ_ROT)/2.0;
                Y_ROT_MidPt = (Y_ROT_Target + Y_ROT)/2.0;
                readyToRotate = true;
        }
        
        //Will be true if the craft is ready to rotate
        if(readyToRotate){ 
            if(rotationTarget){
                if(!(XZ_dist < 0.5)){
                    if(XZ_ROT > XZ_ROT_MidPt){ 
                        addRotation(-1,0,50);
                    } else if(XZ_ROT < XZ_ROT_MidPt){
                        addRotation(1,0,50);
                    }
                }
     
                if(!(Y_dist < 0.5)){
                    if(Y_ROT > XZ_ROT_MidPt){ 
                        addRotation(0,-1,50);
                    } else if(XZ_ROT < Y_ROT_MidPt){
                        addRotation(0,1,50);
                    }
                }
            }
            if(dist < 0.5){
                rotationTarget = false;
            }
        } else {
            //Enacts necessary rotation acceleration to stop the rotation
            addRotation(-XZ_RotSpeed, -Y_RotSpeed, Math.sqrt(0.5));
        }
    }
 
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
}

