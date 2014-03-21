
package entities.projectiles;

import entities.*;

/*
 * This class stores data for a Projectile entity that deals damage
 */
 
public class Projectile extends Entity{
    protected double splashRadius;
	protected double damage;
	
	public Projectile (double X, double Y, double Z, double M, double D, double R){
		super(X,Y,Z,M,1);
		damage = D;
		splashRadius = R;
	}
}
