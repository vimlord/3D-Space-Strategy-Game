package entities.ships;

/*
 * Frigates are the second smallest class of vessels. They each carry
 * one railgun and ten missile tubes.
 */

public class Frigate extends Ship{

	public Frigate(double X, double Y, double Z, int modifier){
		super(X,Y,Z,120,1,0,10,1,modifier);
	}

}
