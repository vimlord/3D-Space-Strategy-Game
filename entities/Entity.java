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
     * Moves the Entity based on its speed
     */
    public void move(){
        x += velX/CycleRunner.cyclesPerSecond;
        y += velY/CycleRunner.cyclesPerSecond;
        z += velZ/CycleRunner.cyclesPerSecond;
        
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
        
        this.x -= (fractionalChange * distanceX - distanceX);
        other.x += (fractionalChange * distanceX - distanceX);
        
        this.y -= (fractionalChange * distanceY - distanceY);
        other.y += (fractionalChange * distanceY - distanceY);
        
        this.z -= (fractionalChange * distanceZ - distanceZ);
        other.z += (fractionalChange * distanceZ - distanceZ);
        
        
        //Calculates force or gravity
        double force = (double)(G*other.mass)/Math.pow(distance,2);
         
         
        //Modifies force value based on cycles per second
        force *= (1/(double)(CycleRunner.cyclesPerSecond));
         
        velX -= (distanceX/distanceXZ) *(distanceXZ/distance) * (force);
        velY -= (distanceY/distance) * (force);
        velZ -= (distanceZ/distanceXZ) * (distanceXZ/distance) * (force);
        
        
        //The code below creates a system for perfectly inelastic collisions

        double avgMomentumX = ((this.mass * this.velX) + (other.mass * other.velX))/2.0;
        double newVelX = avgMomentumX/(this.mass + other.mass);
        this.velX = newVelX;
        other.velX = newVelX;

        double avgMomentumY = ((this.mass * this.velY) + (other.mass * other.velY))/2.0;
        double newVelY = avgMomentumY/(this.mass + other.mass);
        this.velY = newVelY;
        other.velY = newVelY;

        double avgMomentumZ = ((this.mass * this.velZ) + (other.mass * other.velZ))/2.0;
        double newVelZ = avgMomentumZ/(this.mass + other.mass);
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
     
}

