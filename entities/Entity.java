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
    
    protected String name = "Unnamed Entity";
     
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
        x += CycleRunner.getTimeWarp() * velX/CycleRunner.cyclesPerSecond;
        y += CycleRunner.getTimeWarp() * velY/CycleRunner.cyclesPerSecond;
        z += CycleRunner.getTimeWarp() * velZ/CycleRunner.cyclesPerSecond;
        
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
        force *= (CycleRunner.getTimeWarp()/(CycleRunner.cyclesPerSecond));
        
        //If there's no distance on the XZ plane, it doesn't gravitate on that
        //axis.
        if(distanceXZ != 0){
            velX += (distanceX/distanceXZ) * (distanceXZ/distance) * (force);
            velZ += (distanceZ/distanceXZ) * (distanceXZ/distance) * (force);
        }
        velY += (distanceY/distance) * (force);
        
        
         
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
        force *= (CycleRunner.getTimeWarp()/(double)(CycleRunner.cyclesPerSecond));
        
        if(distanceXZ != 0){
            velX -= (distanceX/distanceXZ) *(distanceXZ/distance) * (force)/this.mass;
            velZ -= (distanceZ/distanceXZ) * (distanceXZ/distance) * (force)/this.mass;
        }
        velY -= (distanceY/distance) * (force)/this.mass;
        
        if(distanceXZ != 0){
            other.velX += (distanceX/distanceXZ) *(distanceXZ/distance) * (force)/other.mass;
            other.velZ += (distanceZ/distanceXZ) * (distanceXZ/distance) * (force)/other.mass;
        }
        other.velY += (distanceY/distance) * (force)/other.mass;
        
        
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
    
    //-------------------
    //Orbital Positioning
    //-------------------
    
    public void putIntoOrbit(Orbit o){
        x = o.getX() + EntityList.getEntity(o.getID()).getX();
        y = o.getY() + EntityList.getEntity(o.getID()).getY();
        z = o.getZ() + EntityList.getEntity(o.getID()).getZ();
        
        velX = o.getSpeedX() + EntityList.getEntity(o.getID()).getSpeedX();
        velY = o.getSpeedY() + EntityList.getEntity(o.getID()).getSpeedY();
        velZ = o.getSpeedZ() + EntityList.getEntity(o.getID()).getSpeedZ();
        
    }
    
    public void putIntoOrbit(Orbit o, Entity e){
        x = o.getX() + e.getX();
        y = o.getY() + e.getY();
        z = o.getZ() + e.getZ();

        velX = o.getSpeedX() + e.getSpeedX();
        velY = o.getSpeedY() + e.getSpeedY();
        velZ = o.getSpeedZ() + e.getSpeedZ();
    }
    
    public void putIntoOrbit(Orbit o, double MaE){
        o.setMeanAnomaly(MaE);
        putIntoOrbit(o);
    }
    
    public void setX(double X){
        x = X;
    }
    public void setY(double Y){
        y = Y;
    }
    public void setZ(double Z){
        z = Z;
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
    public void setName(String nm){
        name = nm;
    }
    public String getName(){
        return name;
    }
    public String[] getTag(){
        String[] tag = new String[1];
        tag[0] = name;
        return tag;
    }
    
     
}

