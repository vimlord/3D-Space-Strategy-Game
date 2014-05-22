
package engine.entities.projectiles;

import engine.entities.*;
import engine.entities.ships.*;

/*
 * This class stores data for a Projectile entity that deals damage
 */
 
public abstract class Projectile extends Entity{
    protected double splashRadius;
    protected double damage;
    public final double launchSpeed;
	
    public Projectile (double X, double Y, double Z, double M, double D, double R, double S){
        super(X,Y,Z,M,1);
        damage = D;
        splashRadius = R;
        launchSpeed = S;
    }

    public void collide(Ship s){
        s.collide(this);
        s.damage(damage);
    }
    
    public double getDamage(){
        return damage;
    }
}
