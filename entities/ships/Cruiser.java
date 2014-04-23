package entities.ships;

/*
 * Cruisers are slightly larger than Destroyers. They each carry
 * one railgun and thirty missile tubes.
 */

public class Cruiser extends Ship{

	public Cruiser(double X, double Y, double Z, int modifier){
		super(X,Y,Z,90,2,0,35,1,modifier);
	}

}
