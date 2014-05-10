package entities.ships;

/*
 * Destroyers are the mid sized class of vessels. They each carry
 * one railgun and twenty missile tubes.
 */

public class Destroyer extends Ship{

	public Destroyer(double X, double Y, double Z, int modifier){
		super(X,Y,Z,75,1,0,20,1,modifier);
	}

}
