/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

/**
 *
 * @author Christopher
 */
public class OrderThread extends Thread{
    private int faction;
    private long ship;
    private String order;
    
    
    /**
     * Creates a Thread object that processes a Ship order
     * @param f The faction that the ship belongs to. Used as a "password", in a way.
     * @param s The ID of the Ship to be commanded
     * @param o The Order to pass on
     */
    public OrderThread(int f, long s, String o){
        faction = f;
        ship = s;
        order = o;
    }
    
    public void run(){
        //Test to make sure the faction matches the Ship AND the Ship exists
        
        //Give the Ship the Order
        
    }
    
    
}
