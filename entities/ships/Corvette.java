package entities.ships;

/*
 * Corvettes are the smallest class of vessels. They each carry
 * four missile batteries.
 */

public class Corvette extends Ship{
	
	public Corvette(double X, double Y, double Z, int modifier){
		super(X,Y,Z,80,0,0,4,1,modifier);
	}
	
}
