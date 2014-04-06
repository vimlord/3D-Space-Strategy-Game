/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.ships.shipTools.orders;

/**
 *
 * @author Christopher Hittner
 */
public class Attack extends Order{
    public Attack(long targID){
        order = "(ATK)";
        order += targID;
    }
}
