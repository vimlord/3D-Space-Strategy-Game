/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import entities.ships.Ship;
import entities.ships.shipTools.orders.*;
import java.util.ArrayList;
import main.*;

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
        Ship s = EntityList.getShip(ship);
        if(s.getFactionID() != faction){
            return;
        }
        
        ArrayList<Order> add = new ArrayList<>();
        
        if(order.substring(0,5).equals("(ACC)")){
            
            //This is an acceleration order
            double acceleration = Double.parseDouble(order.substring(5,order.indexOf("|")));
            double time = (Double.parseDouble(order.substring(order.indexOf("|") + 1)))/CycleRunner.cyclesPerSecond;
            add.add(new Accelerate(acceleration,time));
            
        } else if(order.substring(0,5).equals("(ROT)")){
            
            //This is an rotation order
            int border = order.indexOf("|");
            double XZ = Double.parseDouble(order.substring(5, border));
            double Y = Double.parseDouble(order.substring(border + 1));
            
            add.add(new Rotate(XZ,Y));
            
        } else if(order.equals("(WAIT)")){
            
            add.add(new Wait(Double.parseDouble(order.substring(6))/CycleRunner.cyclesPerSecond));
            
        } else if(order.substring(0,5).equals("(WRP)")){
            double magnitude = Double.parseDouble(order.substring(5,order.indexOf("|")));
            double time = Double.parseDouble(order.substring(order.indexOf("|") + 1));
            
            add.add(new Warp(magnitude, time));
            
        } else if(order.substring(0,5).equals("(MNV)")){
            String data = order.substring(6);
            double magnitude = Double.parseDouble(data.substring(1,data.indexOf(")")));
            data = data.substring(data.indexOf(")") + 1);
            double XZ = Double.parseDouble(data.substring(1,data.indexOf(")")));
            data = data.substring(data.indexOf(")") + 1);
            double Y = Double.parseDouble(data.substring(1,data.indexOf(")")));
            
            add.add(0, new Rotate(XZ,Y));
            
            
            double accelerationTime = magnitude/(30 * 750000000.0 / s.getMass());
            
            add.add(1, new Accelerate(100,accelerationTime));
            
        } else if(order.substring(0,5).equals("(ATK)")){
            boolean fireMissile = (order.substring(6, 7).equals("T"));
            boolean fireLaser = (order.substring(7, 8).equals("T"));
            boolean fireRailgun = (order.substring(8, 9).equals("T"));
                
            long targ = Long.parseLong(order.substring(10));
            
            add.add(new Attack(fireMissile, fireLaser, fireRailgun, targ));
        }
        
        EntityList.getShip(ship).giveOrders(add);
        
        
        //Give the Ship the Order
        
    }
    
    
}
