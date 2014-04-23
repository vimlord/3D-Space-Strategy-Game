package entities;
/*
 * Holds data for an Entity object, from which subclasses will be made to allow
 * for various types of objects.
 */
 
import main.*;
import physics.*;
 
/**
 *
 * @author Christopher Hittner
 */
public abstract class Entity implements PhysicsConstants{

    protected double x, y, z;
    protected double velX = 0, velY = 0, velZ = 0;
    protected double mass;
    protected double radius;
    private long IDCode;
     
    /**
     * Creates an Entity object
     * @param X The x-coordinate
     * @param Y The y-coordinate
     * @param Z The z-coordinate
     * @param M The mass in kilograms
     * @param R The radius in meters
     */
    public Entity(double X, double Y, double Z, double M, double R){
        x = X;
        y = Y;
        z = Z;
        mass = M;
        radius = R;
    }
    
    /**
     * Establishes an ID code for an Entity
     * @param l The ID Code to be used
     */
    public void setID(long l){
        IDCode = l;
    }
    
     /**
     * Moves the Entity based on its speed
     */
    public void move(){
        testSpeedOfLight();
        x += velX/CycleRunner.cyclesPerSecond;
        y += velY/CycleRunner.cyclesPerSecond;
        z += velZ/CycleRunner.cyclesPerSecond;
        
    }
    
    private void testSpeedOfLight(){
        double speed = Math.sqrt(Math.pow(velX,2) + Math.pow(velY,2) + Math.pow(velZ,2));
        if(speed >= c){
            double factor = (c - Double.MIN_VALUE)/speed;
            velX *= factor;
            velY *= factor;
            velZ *= factor;
        }
    } 
 
    /**
     * Enacts gravity on the Entity towards the provided Entity object
     * @param other The Entity towards which the gravitation is occurring
     */
    public void gravitate(Entity other){
        //Calculates distances
        double distanceX = other.x - this.x;
        double distanceY = other.y - this.y;
        double distanceZ = other.z - this.z;
        double distanceXZ = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceZ,2));
        double distance = Math.sqrt(Math.pow(distanceXZ,2) + Math.pow(distanceY,2));
         
        //Calculates force or gravity
        double force = (double)(G*other.mass)/Math.pow(distance,2);
         
         
        //Modifies force value based on cycles per second
        force *= (1/(double)(CycleRunner.cyclesPerSecond));
         
        velX += (distanceX/distanceXZ) *(distanceXZ/distance) * (force);
        velY += (distanceY/distance) * (force);
        velZ += (distanceZ/distanceXZ) * (distanceXZ/distance) * (force);
         
    }
    
     
    /**
     * Tests whether two Entity objects are colliding
     * @param other The other Entity
     * @return Whether the two Entities are colliding
     */
    public boolean testCollision(Entity other){
        double distanceX = other.x - this.x;
        double distanceY = other.y - this.y;
        double distanceZ = other.z - this.z;
        double distance = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2) + Math.pow(distanceZ,2));
        
        if(this.radius + other.radius >= distance){
            return true;
        }
        return false;
         
    }
     
    public void collide(Entity other){
        //The coe below is a duplicate of the gravitate() method, with a few changes.
        //This is done to simulate the Normal Force.
        
        //Calculates distances
        double distanceX = other.x - this.x;
        double distanceY = other.y - this.y;
        double distanceZ = other.z - this.z;
        double distanceXZ = Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceZ,2));
        double distance = Math.sqrt(Math.pow(distanceXZ,2) + Math.pow(distanceY,2));
        double collisionRange = this.radius + other.radius;
        double fractionalChange = (collisionRange/distance);
        
        this.x -= (fractionalChange * (distanceX - distanceX))/2;
        other.x += (fractionalChange * (distanceX - distanceX))/2;
        
        this.y -= (fractionalChange * (distanceY - distanceY))/2;
        other.y += (fractionalChange * (distanceY - distanceY))/2;
        
        this.z -= (fractionalChange * (distanceZ - distanceZ))/2;
        other.z += (fractionalChange * (distanceZ - distanceZ))/2; 
        
        
        //Calculates force or gravity
        double force = (double)(G * other.mass * this.mass)/Math.pow(distance,2);
         
         
        //Modifies force value based on cycles per second
        force *= (1/(double)(CycleRunner.cyclesPerSecond));
         
        velX -= (distanceX/distanceXZ) *(distanceXZ/distance) * (force)/this.mass;
        velY -= (distanceY/distance) * (force)/this.mass;
        velZ -= (distanceZ/distanceXZ) * (distanceXZ/distance) * (force)/this.mass;
        
        other.velX += (distanceX/distanceXZ) *(distanceXZ/distance) * (force)/other.mass;
        other.velY += (distanceY/distance) * (force)/other.mass;
        other.velZ += (distanceZ/distanceXZ) * (distanceXZ/distance) * (force)/other.mass;
        
        
        //The code below creates a system for perfectly inelastic collisions

        double MomentumX = ((this.mass * this.velX) + (other.mass * other.velX));
        double newVelX = MomentumX/(this.mass + other.mass);
        this.velX = newVelX;
        other.velX = newVelX;

        double MomentumY = ((this.mass * this.velY) + (other.mass * other.velY));
        double newVelY = MomentumY/(this.mass + other.mass);
        this.velY = newVelY;
        other.velY = newVelY;

        double MomentumZ = ((this.mass * this.velZ) + (other.mass * other.velZ));
        double newVelZ = MomentumZ/(this.mass + other.mass);
        this.velZ = newVelZ;
        other.velZ = newVelZ;
        
    }
	
	
     
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public void setSpeedX(double x){
        velX = x;
    }
    public void setSpeedY(double y){
        velY = y;
    }
    public void setSpeedZ(double z){
        velZ = z;
    }
    public double getSpeedX(){
        return velX;
    }
    public double getSpeedY(){
        return velY;
    }
    public double getSpeedZ(){
        return velZ;
    }
    public double getRadius(){
        return radius;
    }
    public double getMass(){
        return mass;
    }
    public long getID(){
        return IDCode;
    }
     
}

